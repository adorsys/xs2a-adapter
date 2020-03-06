package de.adorsys.xs2a.adapter.adapter.link.bg.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountInformationLinksTemplate extends LinksTemplate {

    // link templates:
    private static final String START_AUTHORISATION_TEMPLATE = "{host}/{version}/consents/{consentId}/authorisations";
    private static final String START_AUTHORISATION_WITH_PSU_IDENTIFICATION_TEMPLATE = "{host}/{version}/consents/{consentId}/authorisations";
    private static final String UPDATE_PSU_IDENTIFICATION_TEMPLATE = "{host}/{version}/consents/{consentId}/authorisations/{authorisationId}";
    private static final String START_AUTHORISATION_WITH_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/consents/{consentId}/authorisations";
    private static final String UPDATE_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/consents/{consentId}/authorisations/{authorisationId}";
    private static final String START_AUTHORISATION_WITH_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/consents/{consentId}/authorisations/{authorisationId}";
    private static final String UPDATE_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/consents/{consentId}/authorisations/{authorisationId}";
    private static final String SELECT_AUTHENTICATION_METHOD_TEMPLATE = "{host}/{version}/consents/{consentId}/authorisations/{authorisationId}";
    private static final String AUTHORISE_TRANSACTION_TEMPLATE = "{host}/{version}/consents/{consentId}/authorisations/{authorisationId}";
    private static final String SELF_TEMPLATE = "{host}/{version}/consents/{consentId}";
    private static final String STATUS_TEMPLATE = "{host}/{version}/consents/{consentId}/status";
    private static final String SCA_STATUS_TEMPLATE = "{host}/{version}/consents/{consentId}/authorisations/{authorisationId}";
    private static final String ACCOUNT_TEMPLATE = "{host}/{version}/accounts";
    private static final String BALANCES_TEMPLATE = "{host}/{version}/accounts/{accountId}/balances";
    private static final String TRANSACTIONS_TEMPLATE = "{host}/{version}/accounts/{accountId}/transactions";
    private static final String TRANSACTION_DETAILS_TEMPLATE = "{host}/{version}/accounts/{accountId}/transactions/{transactionId}";
    private static final String FIRST_TEMPLATE = "{host}/{version}/accounts/{accountId}/transactions";
    private static final String NEXT_TEMPLATE = "{host}/{version}/accounts/{accountId}/transactions";
    private static final String PREVIOUS_TEMPLATE = "{host}/{version}/accounts/{accountId}/transactions";
    private static final String LAST_TEMPLATE = "{host}/{version}/accounts/{accountId}/transactions";

    private final Map<String, String> linkNameToTemplate;

    public AccountInformationLinksTemplate() {
        linkNameToTemplate = new HashMap<>();

        linkNameToTemplate.put(START_AUTHORISATION, START_AUTHORISATION_TEMPLATE);
        linkNameToTemplate.put(START_AUTHORISATION_WITH_PSU_IDENTIFICATION, START_AUTHORISATION_WITH_PSU_IDENTIFICATION_TEMPLATE);
        linkNameToTemplate.put(UPDATE_PSU_IDENTIFICATION, UPDATE_PSU_IDENTIFICATION_TEMPLATE);
        linkNameToTemplate.put(START_AUTHORISATION_WITH_PSU_AUTHENTICATION, START_AUTHORISATION_WITH_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(UPDATE_PSU_AUTHENTICATION, UPDATE_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(START_AUTHORISATION_WITH_ENCRYPTED_PSU_AUTHENTICATION, START_AUTHORISATION_WITH_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(UPDATE_ENCRYPTED_PSU_AUTHENTICATION, UPDATE_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(SELECT_AUTHENTICATION_METHOD, SELECT_AUTHENTICATION_METHOD_TEMPLATE);
        linkNameToTemplate.put(AUTHORISE_TRANSACTION, AUTHORISE_TRANSACTION_TEMPLATE);
        linkNameToTemplate.put(SELF, SELF_TEMPLATE);
        linkNameToTemplate.put(STATUS, STATUS_TEMPLATE);
        linkNameToTemplate.put(SCA_STATUS, SCA_STATUS_TEMPLATE);
        linkNameToTemplate.put(ACCOUNT, ACCOUNT_TEMPLATE);
        linkNameToTemplate.put(BALANCES, BALANCES_TEMPLATE);
        linkNameToTemplate.put(TRANSACTIONS, TRANSACTIONS_TEMPLATE);
        linkNameToTemplate.put(TRANSACTION_DETAILS, TRANSACTION_DETAILS_TEMPLATE);
        linkNameToTemplate.put(FIRST, FIRST_TEMPLATE);
        linkNameToTemplate.put(NEXT, NEXT_TEMPLATE);
        linkNameToTemplate.put(PREVIOUS, PREVIOUS_TEMPLATE);
        linkNameToTemplate.put(LAST, LAST_TEMPLATE);
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
