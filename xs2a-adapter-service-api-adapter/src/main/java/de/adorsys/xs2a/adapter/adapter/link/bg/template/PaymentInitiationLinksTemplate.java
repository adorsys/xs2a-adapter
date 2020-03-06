package de.adorsys.xs2a.adapter.adapter.link.bg.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PaymentInitiationLinksTemplate extends LinksTemplate {

    // link templates:
    private static final String START_AUTHORISATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations";
    private static final String START_AUTHORISATION_WITH_PSU_IDENTIFICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations";
    private static final String UPDATE_PSU_IDENTIFICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String START_AUTHORISATION_WITH_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations";
    private static final String UPDATE_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String START_AUTHORISATION_WITH_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String UPDATE_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String START_AUTHORISATION_WITH_TRANSACTION_AUTHORISATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String SELECT_AUTHENTICATION_METHOD_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String AUTHORISE_TRANSACTION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String SELF_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}";
    private static final String STATUS_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/status";
    private static final String SCA_STATUS_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";

    private final Map<String, String> linkNameToTemplate;

    public PaymentInitiationLinksTemplate() {
        linkNameToTemplate = new HashMap<>();

        linkNameToTemplate.put(START_AUTHORISATION, START_AUTHORISATION_TEMPLATE);
        linkNameToTemplate.put(START_AUTHORISATION_WITH_PSU_IDENTIFICATION,
            START_AUTHORISATION_WITH_PSU_IDENTIFICATION_TEMPLATE);
        linkNameToTemplate.put(UPDATE_PSU_IDENTIFICATION, UPDATE_PSU_IDENTIFICATION_TEMPLATE);
        linkNameToTemplate.put(START_AUTHORISATION_WITH_PSU_AUTHENTICATION,
            START_AUTHORISATION_WITH_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(UPDATE_PSU_AUTHENTICATION, UPDATE_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(START_AUTHORISATION_WITH_ENCRYPTED_PSU_AUTHENTICATION,
            START_AUTHORISATION_WITH_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(UPDATE_ENCRYPTED_PSU_AUTHENTICATION,
            UPDATE_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(START_AUTHORISATION_WITH_TRANSACTION_AUTHORISATION,
            START_AUTHORISATION_WITH_TRANSACTION_AUTHORISATION_TEMPLATE);
        linkNameToTemplate.put(SELECT_AUTHENTICATION_METHOD, SELECT_AUTHENTICATION_METHOD_TEMPLATE);
        linkNameToTemplate.put(AUTHORISE_TRANSACTION, AUTHORISE_TRANSACTION_TEMPLATE);
        linkNameToTemplate.put(SELF, SELF_TEMPLATE);
        linkNameToTemplate.put(STATUS, STATUS_TEMPLATE);
        linkNameToTemplate.put(SCA_STATUS, SCA_STATUS_TEMPLATE);
    }

    @Override
    public Optional<String> get(String linkName) {
        return Optional.ofNullable(linkNameToTemplate.get(linkName));
    }

    @Override
    public void set(String linkName, String linkTemplate) {
        linkNameToTemplate.put(linkName, linkTemplate);
    }
}
