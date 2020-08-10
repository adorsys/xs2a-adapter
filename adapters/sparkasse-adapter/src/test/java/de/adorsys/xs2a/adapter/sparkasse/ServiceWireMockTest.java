package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.service.provider.AdapterServiceProvider;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(ServiceWireMockTestExtension.class)
public @interface ServiceWireMockTest {
    Class<? extends AdapterServiceProvider> value();
}
