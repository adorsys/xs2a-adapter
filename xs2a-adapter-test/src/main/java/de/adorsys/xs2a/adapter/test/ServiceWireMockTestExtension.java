package de.adorsys.xs2a.adapter.test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.impl.http.BaseHttpClientConfig;
import de.adorsys.xs2a.adapter.impl.http.Xs2aHttpLogSanitizer;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
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
        HttpClient httpClient = new ApacheHttpClient(new Xs2aHttpLogSanitizer(), HttpClientBuilder.create().build());
        Pkcs12KeyStore keyStore = new Pkcs12KeyStore(getClass().getClassLoader()
            .getResourceAsStream("de/adorsys/xs2a/adapter/test/test_keystore.p12"));
        HttpClientConfig httpClientConfig = new BaseHttpClientConfig(null, keyStore, null);
        HttpClientFactory httpClientFactory = getHttpClientFactory(httpClient, httpClientConfig);
        LinksRewriter linksRewriter = new IdentityLinksRewriter();
        if (serviceProvider instanceof AccountInformationServiceProvider) {
            AccountInformationService service =
                ((AccountInformationServiceProvider) serviceProvider).getAccountInformationService(aspsp,
                    httpClientFactory,
                    linksRewriter);
            getStore(context).put(AccountInformationService.class, service);
        }
        if (serviceProvider instanceof PaymentInitiationServiceProvider) {
            PaymentInitiationService service =
                ((PaymentInitiationServiceProvider) serviceProvider).getPaymentInitiationService(aspsp,
                    httpClientFactory,
                    linksRewriter);
            getStore(context).put(PaymentInitiationService.class, service);
        }
        if (serviceProvider instanceof Oauth2ServiceProvider) {
            Oauth2Service service =
                ((Oauth2ServiceProvider) serviceProvider).getOauth2Service(aspsp, httpClientFactory);
            getStore(context).put(Oauth2Service.class, service);
        }
    }

    private HttpClientFactory getHttpClientFactory(HttpClient httpClient, HttpClientConfig httpClientConfig) {
        return new HttpClientFactory() {
            @Override
            public HttpClient getHttpClient(String adapterId, String qwacAlias, String[] supportedCipherSuites) {
                return httpClient;
            }

            @Override
            public HttpClientConfig getHttpClientConfig() {
                return httpClientConfig;
            }
        };
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
