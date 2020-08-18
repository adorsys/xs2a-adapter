package de.adorsys.xs2a.adapter.test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.impl.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.AdapterServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.PaymentInitiationServiceProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.platform.commons.support.AnnotationSupport;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

class ServiceWireMockTestExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Class<?> testClass = context.getRequiredTestClass();
        Class<? extends AdapterServiceProvider> serviceProviderClass =
            AnnotationSupport.findAnnotation(testClass, ServiceWireMockTest.class)
                .map(ServiceWireMockTest::value)
                .orElseThrow(IllegalStateException::new);
        AdapterServiceProvider serviceProvider =
            serviceProviderClass.getDeclaredConstructor().newInstance();

        WireMockServer wireMockServer = new WireMockServer(wireMockConfig()
            .dynamicPort()
            .extensions(new ResponseTemplateTransformer(true))
            .usingFilesUnderClasspath(serviceProvider.getAdapterId()));
        wireMockServer.start();
        getStore(context).put(WireMockServer.class, wireMockServer);

        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("http://localhost:" + wireMockServer.port());
        HttpClient httpClient = new ApacheHttpClient(HttpClientBuilder.create().build());
        HttpClientFactory httpClientFactory = (adapterId, qwacAlias, supportedCipherSuites) -> httpClient;
        Pkcs12KeyStore keyStore = new Pkcs12KeyStore(getClass().getClassLoader()
            .getResourceAsStream("de/adorsys/xs2a/adapter/test/test_keystore.p12"));
        LinksRewriter linksRewriter = new IdentityLinksRewriter();
        if (serviceProvider instanceof AccountInformationServiceProvider) {
            AccountInformationService service =
                ((AccountInformationServiceProvider) serviceProvider).getAccountInformationService(aspsp,
                    httpClientFactory,
                    keyStore,
                    linksRewriter);
            getStore(context).put(AccountInformationService.class, service);
        }
        if (serviceProvider instanceof PaymentInitiationServiceProvider) {
            PaymentInitiationService service =
                ((PaymentInitiationServiceProvider) serviceProvider).getPaymentInitiationService(aspsp,
                    httpClientFactory,
                    keyStore,
                    linksRewriter);
            getStore(context).put(PaymentInitiationService.class, service);
        }
        if (serviceProvider instanceof Oauth2ServiceProvider) {
            Oauth2Service service =
                ((Oauth2ServiceProvider) serviceProvider).getOauth2Service(aspsp,
                    httpClientFactory,
                    keyStore);
            getStore(context).put(Oauth2Service.class, service);
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(Namespace.create(getClass(), context.getRequiredTestClass()));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        getStore(context).remove(WireMockServer.class, WireMockServer.class).stop();
        getStore(context).remove(AccountInformationService.class);
        getStore(context).remove(PaymentInitiationService.class);
        getStore(context).remove(Oauth2Service.class);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) {
        return getStore(extensionContext).get(parameterContext.getParameter().getType()) != null;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) {
        return getStore(extensionContext).get(parameterContext.getParameter().getType());
    }
}
