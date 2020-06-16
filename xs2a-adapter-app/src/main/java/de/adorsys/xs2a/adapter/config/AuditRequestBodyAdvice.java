package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.api.model.AccountAccess;
import de.adorsys.xs2a.adapter.api.model.AccountReference;
import de.adorsys.xs2a.adapter.api.model.Consents;
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
        if (body instanceof Consents) {
            Consents consents = (Consents) body;
            MDC.put("iban", getIbans(consents).toString());
            MDC.put("consentModel", getModel(consents));
        } else {
            logger.warn("Unexpected body type {} for create consent", body.getClass());
        }

        return body;
    }

    private Set<String> getIbans(Consents consents) {
        Set<String> ibans = new HashSet<>();
        AccountAccess access = consents.getAccess();
        if (access != null) {
            ibans.addAll(getIbans(access.getAccounts()));
            ibans.addAll(getIbans(access.getBalances()));
            ibans.addAll(getIbans(access.getTransactions()));
        }
        return ibans;
    }

    private Set<String> getIbans(List<AccountReference> accountRefs) {
        if (accountRefs == null || accountRefs.isEmpty()) {
            return emptySet();
        }
        return accountRefs.stream()
            .map(AccountReference::getIban)
            .collect(toSet());
    }

    private String getModel(Consents consents) {
        AccountAccess access = consents.getAccess();
        if (access != null) {
            if (access.getAllPsd2() == AccountAccess.AllPsd2.ALLACCOUNTS) {
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
