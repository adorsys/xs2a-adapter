package de.adorsys.xs2a.adapter.config;

import de.adorsys.bg.monitoring.client.MonitoringContext;
import de.adorsys.xs2a.adapter.api.model.AccountAccess;
import de.adorsys.xs2a.adapter.api.model.AccountReference;
import de.adorsys.xs2a.adapter.api.model.Consents;
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
@Deprecated
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
        return "createConsent".equals(method.getName()) && targetType.equals(Consents.class);
    }

    @Override
    public Object afterBodyRead(Object body,
                                HttpInputMessage inputMessage,
                                MethodParameter parameter,
                                Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        if (body instanceof Consents) {
            Consents consents = (Consents) body;
            MonitoringContext.setIban(getIban(consents));
        } else {
            logger.warn("Unexpected body type {} for create consent", body.getClass());
        }

        return body;
    }

    private String getIban(Consents consents) {
        Set<String> ibans = getIbans(consents);
        Iterator<String> iterator = ibans.iterator();
        if (iterator.hasNext()) {
            return iterator.next().replaceFirst("(?<=\\d{8})\\d+", "******");
        }
        return null;
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
}
