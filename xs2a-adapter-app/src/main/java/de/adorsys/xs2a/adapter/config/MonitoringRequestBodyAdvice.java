package de.adorsys.xs2a.adapter.config;

import de.adorsys.bg.monitoring.client.MonitoringContext;
import de.adorsys.xs2a.adapter.model.AccountAccessTO;
import de.adorsys.xs2a.adapter.model.AccountReferenceTO;
import de.adorsys.xs2a.adapter.model.ConsentsTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

@ControllerAdvice
public class MonitoringRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringRequestBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = methodParameter.getMethod();
        if (method == null) {
            return false;
        }
        return "createConsent".equals(method.getName()) && targetType.equals(ConsentsTO.class);
    }

    @Override
    public Object afterBodyRead(Object body,
                                HttpInputMessage inputMessage,
                                MethodParameter parameter,
                                Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        if (body instanceof ConsentsTO) {
            ConsentsTO consents = (ConsentsTO) body;
            MonitoringContext.setIban(getIban(consents));
        } else {
            logger.warn("Unexpected body type {} for create consent", body.getClass());
        }

        return body;
    }

    private String getIban(ConsentsTO consents) {
        Set<String> ibans = getIbans(consents);
        Iterator<String> iterator = ibans.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
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
}
