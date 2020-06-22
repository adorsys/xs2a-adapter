package de.adorsys.xs2a.adapter.adapter.link.bg;

import de.adorsys.xs2a.adapter.adapter.link.bg.template.LinksTemplate;
import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.adorsys.xs2a.adapter.adapter.link.bg.template.LinksTemplate.*;

public class BerlinGroupLinksRewriter implements LinksRewriter {
    private static final Logger logger = LoggerFactory.getLogger(BerlinGroupLinksRewriter.class);

    private static final Set<String> UNCHANGEABLE_LINKS
        = new HashSet<>(Arrays.asList(SCA_REDIRECT, SCA_OAUTH));

    private static final String START_OF_PLACEHOLDER = "{";
    private static final String END_OF_PLACEHOLDER = "}";

    private static final Pattern CONSENT_ID_PATTERN = Pattern.compile("/consents/([^/]+)");
    private static final Pattern AUTHORISATION_ID_PATTERN = Pattern.compile("/authorisations/([^/]+)");
    private static final Pattern ACCOUNT_ID_PATTERN = Pattern.compile("/accounts/([^/]+)");
    private static final Pattern TRANSACTION_ID_PATTERN = Pattern.compile("/transactions/([^/]+)");

    private final LinksTemplate linksTemplate;
    private final String host;
    private final String version;
    private final Map<String, Function<String, Optional<String>>> placeholdersToParamRetrievers;

    public BerlinGroupLinksRewriter(LinksTemplate linksTemplate,
                                    String host,
                                    String version) {
        this.linksTemplate = linksTemplate;
        this.host = host;
        this.version = version;
        this.placeholdersToParamRetrievers = new HashMap<>();

        registerPlaceholder(HOST_PLACEHOLDER, this::retrieveHost);
        registerPlaceholder(VERSION_PLACEHOLDER, this::retrieveVersion);
        registerPlaceholder(CONSENT_ID_PLACEHOLDER, this::retrieveConsentId);
        registerPlaceholder(AUTHORISATION_ID_PLACEHOLDER, this::retrieveAuthorisationId);
        registerPlaceholder(ACCOUNT_ID_PLACEHOLDER, this::retrieveAccountId);
        registerPlaceholder(TRANSACTION_ID_PLACEHOLDER, this::retrieveTransactionId);
        registerPlaceholder(PAYMENT_SERVICE_PLACEHOLDER, this::retrievePaymentService);
        registerPlaceholder(PAYMENT_PRODUCT_PLACEHOLDER, this::retrievePaymentProduct);
        registerPlaceholder(PAYMENT_ID_PLACEHOLDER, this::retrievePaymentId);
    }

    public void registerPlaceholder(String placeholder,
                                    Function<String, Optional<String>> paramRetriever) {
        placeholdersToParamRetrievers.put(placeholder, paramRetriever);
    }

    @Override
    public Map<String, HrefType> rewrite(Map<String, HrefType> links) {
        if (links == null || links.isEmpty()) {
            return links;
        }

        Map<String, HrefType> rewrittenLinks = new HashMap<>();

        for (Map.Entry<String, HrefType> linkEntry : links.entrySet()) {
            String linkName = linkEntry.getKey();
            HrefType linkFromAspsp = linkEntry.getValue();

            if (linkUnchangeable(linkName)) {
                rewrittenLinks.put(linkName, linkFromAspsp);
                continue;
            }

            Optional<String> linkTemplateOptional = linksTemplate.get(linkName);

            if (!linkTemplateOptional.isPresent()) {
                // business decision to leave unknown links untouched
                logger.warn("Links rewriting: unknown link [{}] - will be leaved unmapped", linkName);
                rewrittenLinks.put(linkName, linkFromAspsp);
                continue;
            }

            Optional<String> rewrittenLinksOptional
                = replacePlaceholdersWithValues(linkTemplateOptional.get(), linkFromAspsp.getHref());

            if (rewrittenLinksOptional.isPresent()) {
                String rewrittenLink = rewrittenLinksOptional.get();
                rewrittenLink = StringUri.copyQueryParams(linkFromAspsp.getHref(), rewrittenLink);
                HrefType link = new HrefType();
                link.setHref(rewrittenLink);
                rewrittenLinks.put(linkName, link);
            } else {
                // business decision to leave unknown links untouched
                logger.warn("Links rewriting: unknown format of the link [{}] - will be leaved unmapped. " +
                                "Custom rewriting should be provided to handle it", linkName);
                rewrittenLinks.put(linkName, linkFromAspsp);
            }
        }

        return rewrittenLinks;
    }

    protected boolean linkUnchangeable(String linkName) {
        return UNCHANGEABLE_LINKS.contains(linkName);
    }

    private Optional<String> replacePlaceholdersWithValues(String linkTemplate, String linkFromAspsp) {
        Set<String> placeholders = getTemplatePlaceholders(linkTemplate);

        String rewrittenLink = linkTemplate;

        for (String placeholder : placeholders) {
            Function<String, Optional<String>> paramRetriever
                = placeholdersToParamRetrievers.get(placeholder);

            if (paramRetriever == null) {
                return Optional.empty();
            }

            Optional<String> paramOptional = paramRetriever.apply(linkFromAspsp);

            if (!paramOptional.isPresent()) {
                return Optional.empty();
            }

            String param = paramOptional.get();
            rewrittenLink = rewrittenLink.replace(placeholder, param);
        }

        return Optional.of(rewrittenLink);
    }

    private Set<String> getTemplatePlaceholders(String linkTemplate) {
        Set<String> placeholders = new LinkedHashSet<>();

        int from = 0;

        while (true) {
            int start = linkTemplate.indexOf(START_OF_PLACEHOLDER, from);
            int end = linkTemplate.indexOf(END_OF_PLACEHOLDER, from);

            if (start == -1 || end == -1) {
                break;
            }

            String placeholder = linkTemplate.substring(start, end + 1);
            placeholders.add(placeholder);

            from = end + 1;
        }

        return placeholders;
    }

    protected Optional<String> retrieveHost(String link) {
        return Optional.ofNullable(host);
    }

    protected Optional<String> retrieveVersion(String link) {
        return Optional.ofNullable(version);
    }

    protected Optional<String> retrieveConsentId(String link) {
        return findUsingPattern(link, CONSENT_ID_PATTERN);
    }

    private Optional<String> findUsingPattern(String link, Pattern pattern) {
        Matcher matcher = pattern.matcher(link);

        if (matcher.find()) {
            return Optional.of(matcher.group(1));
        }

        return Optional.empty();
    }

    protected Optional<String> retrieveAuthorisationId(String link) {
        return findUsingPattern(link, AUTHORISATION_ID_PATTERN);
    }

    protected Optional<String> retrieveAccountId(String link) {
        return findUsingPattern(link, ACCOUNT_ID_PATTERN);
    }

    protected Optional<String> retrieveTransactionId(String link) {
        return findUsingPattern(link, TRANSACTION_ID_PATTERN);
    }

    protected Optional<String> retrievePaymentService(String link) {
        String[] linkPaths = getLinkPathsAsArray(link);

        // < 1 as payment service is the first path param in the URI
        if (linkPaths.length < 1) {
            return Optional.empty();
        }

        // as payment links are compliant with
        // the following pattern: `{paymentService}/{paymentProduct}`
        // it means that the linkPaths[0] returns payment service value
        return Optional.of(linkPaths[0]);
    }

    private String[] getLinkPathsAsArray(String link) {
        Optional<String> aspspApiVersionOptional = StringUri.getVersion(link);

        if (!aspspApiVersionOptional.isPresent()) {
            return new String[]{};
        }

        String aspspApiVersion = aspspApiVersionOptional.get();
        // `aspspApiVersion.length() + 1` - (+ 1) to include a slash after the version (e.g. `v1/`)
        String linkWithoutHostAndVersion
            = link.substring(link.indexOf(aspspApiVersion) + aspspApiVersion.length() + 1);

        if (linkWithoutHostAndVersion.isEmpty()) {
            return new String[]{};
        }

        return linkWithoutHostAndVersion.split("/");
    }

    protected Optional<String> retrievePaymentProduct(String link) {
        String[] linkPaths = getLinkPathsAsArray(link);

        // < 2 as payment product is the second path param in the URI
        if (linkPaths.length < 2) {
            return Optional.empty();
        }

        // as payment links are compliant
        // with the following pattern: `{paymentService}/{paymentProduct}`
        // it means that the linkPaths[1] returns payment product value
        return Optional.of(linkPaths[1]);
    }

    protected Optional<String> retrievePaymentId(String link) {
        String[] linkPaths = getLinkPathsAsArray(link);

        // < 3 as payment id is the third path param in the URI
        if (linkPaths.length < 3) {
            return Optional.empty();
        }

        // as payment links (that contains payment id) are compliant
        // with the following pattern: `{paymentService}/{paymentProduct}/{paymentId}`
        // it means that the linkPaths[2] returns payment id value
        return Optional.of(linkPaths[2]);
    }
}
