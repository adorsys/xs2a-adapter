package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.model.AccountAccessTO;
import de.adorsys.xs2a.adapter.model.AccountReferenceTO;
import de.adorsys.xs2a.adapter.model.ConsentsTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

@ControllerAdvice
public class AuditRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuditRequestBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = methodParameter.getMethod();
        if (method == null) {
            return false;
        }
        return "createConsent" .equals(method.getName());
    }

    @Override
    public Object afterBodyRead(Object body,
                                HttpInputMessage inputMessage,
                                MethodParameter parameter,
                                Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        if (body instanceof ConsentsTO) {
            ConsentsTO consents = (ConsentsTO) body;
            MDC.put("iban", getIbans(consents).toString());
            MDC.put("consentModel", getModel(consents));
        } else if (body instanceof de.adorsys.xs2a.adapter.rest.psd2.model.ConsentsTO) {
            de.adorsys.xs2a.adapter.rest.psd2.model.ConsentsTO consents =
                (de.adorsys.xs2a.adapter.rest.psd2.model.ConsentsTO) body;
            MDC.put("iban", getIbans(consents).toString());
            MDC.put("consentModel", getModel(consents));
        } else {
            logger.warn("Unexpected body type {} for create consent", body.getClass());
        }

        return body;
    }

    private Set<String> getIbans(ConsentsTO consents) {
        Set<String> ibans = new HashSet<>();
        AccountAccessTO access = consents.getAccess();
        if (access != null) {
            ibans.addAll(getIbans(access.getAccounts()));
            ibans.addAll(getIbans(access.getBalances()));
            ibans.addAll(getIbans(access.getTransactions()));
        }
        return ibans;
    }

    private Set<String> getIbans(List<AccountReferenceTO> accountRefs) {
        if (accountRefs == null || accountRefs.isEmpty()) {
            return emptySet();
        }
        return accountRefs.stream()
            .map(AccountReferenceTO::getIban)
            .collect(toSet());
    }

    private String getModel(ConsentsTO consents) {
        AccountAccessTO access = consents.getAccess();
        if (access != null) {
            if (access.getAllPsd2() == AccountAccessTO.AllPsd2TO.ALLACCOUNTS) {
                return "global";
            }
            if ((access.getAccounts() == null || access.getAccounts().isEmpty()) &&
                (access.getBalances() == null || access.getBalances().isEmpty()) &&
                (access.getTransactions() == null || access.getTransactions().isEmpty())) {

                return "bank-offered";
            }
            return "detailed";
        }
        return "unknown";
    }

    private Set<String> getIbans(de.adorsys.xs2a.adapter.rest.psd2.model.ConsentsTO consents) {
        Set<String> ibans = new HashSet<>();
        de.adorsys.xs2a.adapter.rest.psd2.model.AccountAccessTO access = consents.getAccess();
        if (access != null) {
            ibans.addAll(getIbansPsd2(access.getAccounts()));
            ibans.addAll(getIbansPsd2(access.getBalances()));
            ibans.addAll(getIbansPsd2(access.getTransactions()));
        }
        return ibans;
    }

    private Set<String> getIbansPsd2(List<de.adorsys.xs2a.adapter.rest.psd2.model.AccountReferenceTO> accountRefs) {
        if (accountRefs == null || accountRefs.isEmpty()) {
            return emptySet();
        }
        return accountRefs.stream()
            .map(de.adorsys.xs2a.adapter.rest.psd2.model.AccountReferenceTO::getIban)
            .collect(toSet());
    }

    private String getModel(de.adorsys.xs2a.adapter.rest.psd2.model.ConsentsTO consents) {
        de.adorsys.xs2a.adapter.rest.psd2.model.AccountAccessTO access = consents.getAccess();
        if (access != null) {
            if ("allAccounts".equals(access.getAllPsd2())) {
                return "global";
            }
            if ((access.getAccounts() == null || access.getAccounts().isEmpty()) &&
                (access.getBalances() == null || access.getBalances().isEmpty()) &&
                (access.getTransactions() == null || access.getTransactions().isEmpty())) {

                return "bank-offered";
            }
            return "detailed";
        }
        return "unknown";
    }
}
