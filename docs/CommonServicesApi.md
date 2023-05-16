# CommonServicesApi

All URIs are relative to *https://api.testbank.com/psd2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteSigningBasket**](CommonServicesApi.md#deleteSigningBasket) | **DELETE** /v1/signing-baskets/{basketId} | Delete the signing basket
[**getConsentScaStatus**](CommonServicesApi.md#getConsentScaStatus) | **GET** /v1/consents/{consentId}/authorisations/{authorisationId} | Read the SCA status of the consent authorisation
[**getPaymentCancellationScaStatus**](CommonServicesApi.md#getPaymentCancellationScaStatus) | **GET** /v1/{payment-service}/{payment-product}/{paymentId}/cancellation-authorisations/{authorisationId} | Read the SCA status of the payment cancellation&#x27;s authorisation
[**getPaymentInitiationAuthorisation**](CommonServicesApi.md#getPaymentInitiationAuthorisation) | **GET** /v1/{payment-service}/{payment-product}/{paymentId}/authorisations | Get payment initiation authorisation sub-resources request
[**getPaymentInitiationScaStatus**](CommonServicesApi.md#getPaymentInitiationScaStatus) | **GET** /v1/{payment-service}/{payment-product}/{paymentId}/authorisations/{authorisationId} | Read the SCA status of the payment authorisation
[**getSigningBasketAuthorisation**](CommonServicesApi.md#getSigningBasketAuthorisation) | **GET** /v1/signing-baskets/{basketId}/authorisations | Get signing basket authorisation sub-resources request
[**getSigningBasketScaStatus**](CommonServicesApi.md#getSigningBasketScaStatus) | **GET** /v1/signing-baskets/{basketId}/authorisations/{authorisationId} | Read the SCA status of the signing basket authorisation
[**getSigningBasketStatus**](CommonServicesApi.md#getSigningBasketStatus) | **GET** /v1/signing-baskets/{basketId}/status | Read the status of the signing basket
[**startConsentAuthorisation**](CommonServicesApi.md#startConsentAuthorisation) | **POST** /v1/consents/{consentId}/authorisations | Start the authorisation process for a consent
[**startPaymentAuthorisation**](CommonServicesApi.md#startPaymentAuthorisation) | **POST** /v1/{payment-service}/{payment-product}/{paymentId}/authorisations | Start the authorisation process for a payment initiation
[**startPaymentInitiationCancellationAuthorisation**](CommonServicesApi.md#startPaymentInitiationCancellationAuthorisation) | **POST** /v1/{payment-service}/{payment-product}/{paymentId}/cancellation-authorisations | Start the authorisation process for the cancellation of the addressed payment
[**startSigningBasketAuthorisation**](CommonServicesApi.md#startSigningBasketAuthorisation) | **POST** /v1/signing-baskets/{basketId}/authorisations | Start the authorisation process for a signing basket
[**updateConsentsPsuData**](CommonServicesApi.md#updateConsentsPsuData) | **PUT** /v1/consents/{consentId}/authorisations/{authorisationId} | Update PSU Data for consents
[**updatePaymentCancellationPsuData**](CommonServicesApi.md#updatePaymentCancellationPsuData) | **PUT** /v1/{payment-service}/{payment-product}/{paymentId}/cancellation-authorisations/{authorisationId} | Update PSU data for payment initiation cancellation
[**updatePaymentPsuData**](CommonServicesApi.md#updatePaymentPsuData) | **PUT** /v1/{payment-service}/{payment-product}/{paymentId}/authorisations/{authorisationId} | Update PSU data for payment initiation
[**updateSigningBasketPsuData**](CommonServicesApi.md#updateSigningBasketPsuData) | **PUT** /v1/signing-baskets/{basketId}/authorisations/{authorisationId} | Update PSU data for signing basket

<a name="deleteSigningBasket"></a>
# **deleteSigningBasket**
> deleteSigningBasket(basketId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Delete the signing basket

Delete the signing basket structure as long as no (partial) authorisation has yet been applied.  The underlying transactions are not affected by this deletion.  Remark: The signing basket as such is not deletable after a first (partial) authorisation has been applied.  Nevertheless, single transactions might be cancelled on an individual basis on the XS2A interface. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
String basketId = "basketId_example"; // String | This identification of the corresponding signing basket object. 
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    apiInstance.deleteSigningBasket(basketId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#deleteSigningBasket");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **basketId** | [**String**](.md)| This identification of the corresponding signing basket object.  |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a name="getConsentScaStatus"></a>
# **getConsentScaStatus**
> ScaStatusResponse getConsentScaStatus(consentId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read the SCA status of the consent authorisation

This method returns the SCA status of a consent initiation&#x27;s authorisation sub-resource. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
String consentId = "consentId_example"; // String | ID of the corresponding consent object as returned by an account information consent request. 
String authorisationId = "authorisationId_example"; // String | Resource identification of the related SCA.
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding HTTP request  IP Address field between PSU and TPP.  It shall be contained if and only if this request was actively initiated by the PSU. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    ScaStatusResponse result = apiInstance.getConsentScaStatus(consentId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#getConsentScaStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **consentId** | [**String**](.md)| ID of the corresponding consent object as returned by an account information consent request.  |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding HTTP request  IP Address field between PSU and TPP.  It shall be contained if and only if this request was actively initiated by the PSU.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

[**ScaStatusResponse**](ScaStatusResponse.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a name="getPaymentCancellationScaStatus"></a>
# **getPaymentCancellationScaStatus**
> ScaStatusResponse getPaymentCancellationScaStatus(paymentService, paymentProduct, paymentId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read the SCA status of the payment cancellation&#x27;s authorisation

This method returns the SCA status of a payment initiation&#x27;s authorisation sub-resource. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
String paymentService = "paymentService_example"; // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 
String paymentProduct = "paymentProduct_example"; // String | The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants. 
String paymentId = "paymentId_example"; // String | Resource identification of the generated payment initiation resource.
String authorisationId = "authorisationId_example"; // String | Resource identification of the related SCA.
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    ScaStatusResponse result = apiInstance.getPaymentCancellationScaStatus(paymentService, paymentProduct, paymentId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#getPaymentCancellationScaStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentProduct** | **String**| The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants.  | [enum: sepa-credit-transfers, instant-sepa-credit-transfers, target-2-payments, cross-border-credit-transfers, pain.001-sepa-credit-transfers, pain.001-instant-sepa-credit-transfers, pain.001-target-2-payments, pain.001-cross-border-credit-transfers]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

[**ScaStatusResponse**](ScaStatusResponse.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a name="getPaymentInitiationAuthorisation"></a>
# **getPaymentInitiationAuthorisation**
> Authorisations getPaymentInitiationAuthorisation(paymentService, paymentProduct, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Get payment initiation authorisation sub-resources request

Read a list of all authorisation subresources IDs which have been created.  This function returns an array of hyperlinks to all generated authorisation sub-resources. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
String paymentService = "paymentService_example"; // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 
String paymentProduct = "paymentProduct_example"; // String | The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants. 
String paymentId = "paymentId_example"; // String | Resource identification of the generated payment initiation resource.
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    Authorisations result = apiInstance.getPaymentInitiationAuthorisation(paymentService, paymentProduct, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#getPaymentInitiationAuthorisation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentProduct** | **String**| The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants.  | [enum: sepa-credit-transfers, instant-sepa-credit-transfers, target-2-payments, cross-border-credit-transfers, pain.001-sepa-credit-transfers, pain.001-instant-sepa-credit-transfers, pain.001-target-2-payments, pain.001-cross-border-credit-transfers]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

[**Authorisations**](Authorisations.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a name="getPaymentInitiationScaStatus"></a>
# **getPaymentInitiationScaStatus**
> ScaStatusResponse getPaymentInitiationScaStatus(paymentService, paymentProduct, paymentId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read the SCA status of the payment authorisation

This method returns the SCA status of a payment initiation&#x27;s authorisation sub-resource. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
String paymentService = "paymentService_example"; // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 
String paymentProduct = "paymentProduct_example"; // String | The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants. 
String paymentId = "paymentId_example"; // String | Resource identification of the generated payment initiation resource.
String authorisationId = "authorisationId_example"; // String | Resource identification of the related SCA.
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    ScaStatusResponse result = apiInstance.getPaymentInitiationScaStatus(paymentService, paymentProduct, paymentId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#getPaymentInitiationScaStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentProduct** | **String**| The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants.  | [enum: sepa-credit-transfers, instant-sepa-credit-transfers, target-2-payments, cross-border-credit-transfers, pain.001-sepa-credit-transfers, pain.001-instant-sepa-credit-transfers, pain.001-target-2-payments, pain.001-cross-border-credit-transfers]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

[**ScaStatusResponse**](ScaStatusResponse.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a name="getSigningBasketAuthorisation"></a>
# **getSigningBasketAuthorisation**
> Authorisations getSigningBasketAuthorisation(basketId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Get signing basket authorisation sub-resources request

Read a list of all authorisation subresources IDs which have been created.  This function returns an array of hyperlinks to all generated authorisation sub-resources. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
String basketId = "basketId_example"; // String | This identification of the corresponding signing basket object. 
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    Authorisations result = apiInstance.getSigningBasketAuthorisation(basketId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#getSigningBasketAuthorisation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **basketId** | [**String**](.md)| This identification of the corresponding signing basket object.  |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

[**Authorisations**](Authorisations.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a name="getSigningBasketScaStatus"></a>
# **getSigningBasketScaStatus**
> ScaStatusResponse getSigningBasketScaStatus(basketId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read the SCA status of the signing basket authorisation

This method returns the SCA status of a signing basket&#x27;s authorisation sub-resource. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
String basketId = "basketId_example"; // String | This identification of the corresponding signing basket object. 
String authorisationId = "authorisationId_example"; // String | Resource identification of the related SCA.
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    ScaStatusResponse result = apiInstance.getSigningBasketScaStatus(basketId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#getSigningBasketScaStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **basketId** | [**String**](.md)| This identification of the corresponding signing basket object.  |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

[**ScaStatusResponse**](ScaStatusResponse.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a name="getSigningBasketStatus"></a>
# **getSigningBasketStatus**
> SigningBasketStatusResponse200 getSigningBasketStatus(basketId, xRequestID, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read the status of the signing basket

Returns the status of a signing basket object.  

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
String basketId = "basketId_example"; // String | This identification of the corresponding signing basket object. 
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String PSU_ID = "PSU_ID_example"; // String | Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP's documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation. 
String psUIDType = "psUIDType_example"; // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation. 
String psUCorporateID = "psUCorporateID_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUCorporateIDType = "psUCorporateIDType_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    SigningBasketStatusResponse200 result = apiInstance.getSigningBasketStatus(basketId, xRequestID, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#getSigningBasketStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **basketId** | [**String**](.md)| This identification of the corresponding signing basket object.  |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP&#x27;s documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

[**SigningBasketStatusResponse200**](SigningBasketStatusResponse200.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

<a name="startConsentAuthorisation"></a>
# **startConsentAuthorisation**
> StartScaprocessResponse startConsentAuthorisation(xRequestID, consentId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, tpPRedirectPreferred, tpPDecoupledPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPNotificationURI, tpPNotificationContentPreferred, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Start the authorisation process for a consent

Create an authorisation sub-resource and start the authorisation process of a consent.  The message might in addition transmit authentication and authorisation related data.  his method is iterated n times for a n times SCA authorisation in a  corporate context, each creating an own authorisation sub-endpoint for  the corresponding PSU authorising the consent.  The ASPSP might make the usage of this access method unnecessary,  since the related authorisation resource will be automatically created by  the ASPSP after the submission of the consent data with the first POST consents call.  The start authorisation process is a process which is needed for creating a new authorisation  or cancellation sub-resource.   This applies in the following scenarios:    * The ASPSP has indicated with an &#x27;startAuthorisation&#x27; hyperlink in the preceding Payment      initiation response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be      uploaded by using the extended forms:     * &#x27;startAuthorisationWithPsuIdentification&#x27;,      * &#x27;startAuthorisationWithPsuAuthentication&#x27;      * &#x27;startAuthorisationWithEncryptedPsuAuthentication&#x27;     * &#x27;startAuthorisationWithAuthentciationMethodSelection&#x27;    * The related payment initiation cannot yet be executed since a multilevel SCA is mandated.   * The ASPSP has indicated with an &#x27;startAuthorisation&#x27; hyperlink in the preceding      payment cancellation response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be uploaded      by using the extended forms as indicated above.   * The related payment cancellation request cannot be applied yet since a multilevel SCA is mandate for      executing the cancellation.   * The signing basket needs to be authorised yet. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String consentId = "consentId_example"; // String | ID of the corresponding consent object as returned by an account information consent request. 
Object body = null; // Object | 
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String PSU_ID = "PSU_ID_example"; // String | Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP's documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation. 
String psUIDType = "psUIDType_example"; // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation. 
String psUCorporateID = "psUCorporateID_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUCorporateIDType = "psUCorporateIDType_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
Boolean tpPRedirectPreferred = true; // Boolean | If it equals \"true\", the TPP prefers a redirect over an embedded SCA approach. If it equals \"false\", the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the parameter TPP-Decoupled-Preferred and the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU. 
Boolean tpPDecoupledPreferred = true; // Boolean | If it equals \"true\", the TPP prefers a decoupled SCA approach.  If it equals \"false\", the TPP prefers not to use the decoupled approach for SCA. The ASPSP will then choose between the embedded or the redirect SCA approach, depending on the choice of the SCA procedure by the TPP/PSU.  If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the parameter TPP-Redirect-Preferred and the SCA method chosen by the TPP/PSU.  The parameter might be ignored by the ASPSP. If both parameters TPP-Redirect-Preferred and TPP-Decoupled-Preferred are present and true, the request is still not rejected, but it is up to the ASPSP, which approach will actually be used.  **Remark for Future:**  TPP-Redirect-Preferred and TPP-Decoupled-Preferred will be revised in future versions, maybe merged. Currently kept separate for downward compatibility. 
String tpPRedirectURI = "tpPRedirectURI_example"; // String | URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach, specifically  when TPP-Redirect-Preferred equals \"true\". It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification. 
String tpPNokRedirectURI = "tpPNokRedirectURI_example"; // String | If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP. 
String tpPNotificationURI = "tpPNotificationURI_example"; // String | URI for the Endpoint of the TPP-API to which the status of the payment initiation should be sent. This header field may by ignored by the ASPSP.  For security reasons, it shall be ensured that the TPP-Notification-URI as introduced above is secured by the TPP eIDAS QWAC used for identification of the TPP. The following applies:  URIs which are provided by TPPs in TPP-Notification-URI shall comply with the domain secured by the eIDAS QWAC certificate of the TPP in the field CN or SubjectAltName of the certificate. Please note that in case of example-TPP.com as certificate entry TPP- Notification-URI like www.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications or notifications.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications would be compliant.  Wildcard definitions shall be taken into account for compliance checks by the ASPSP.  ASPSPs may respond with ASPSP-Notification-Support set to false, if the provided URIs do not comply. 
String tpPNotificationContentPreferred = "tpPNotificationContentPreferred_example"; // String | The string has the form   status=X1, ..., Xn  where Xi is one of the constants SCA, PROCESS, LAST and where constants are not repeated. The usage of the constants supports the of following semantics:    SCA: A notification on every change of the scaStatus attribute for all related authorisation processes is preferred by the TPP.    PROCESS: A notification on all changes of consentStatus or transactionStatus attributes is preferred by the TPP.   LAST: Only a notification on the last consentStatus or transactionStatus as available in the XS2A interface is preferred by the TPP.  This header field may be ignored, if the ASPSP does not support resource notification services for the related TPP. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding HTTP request  IP Address field between PSU and TPP.  It shall be contained if and only if this request was actively initiated by the PSU. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    StartScaprocessResponse result = apiInstance.startConsentAuthorisation(xRequestID, consentId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, tpPRedirectPreferred, tpPDecoupledPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPNotificationURI, tpPNotificationContentPreferred, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#startConsentAuthorisation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **consentId** | [**String**](.md)| ID of the corresponding consent object as returned by an account information consent request.  |
 **body** | [**Object**](Object.md)|  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP&#x27;s documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **tpPRedirectPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers a redirect over an embedded SCA approach. If it equals \&quot;false\&quot;, the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the parameter TPP-Decoupled-Preferred and the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU.  | [optional]
 **tpPDecoupledPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers a decoupled SCA approach.  If it equals \&quot;false\&quot;, the TPP prefers not to use the decoupled approach for SCA. The ASPSP will then choose between the embedded or the redirect SCA approach, depending on the choice of the SCA procedure by the TPP/PSU.  If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the parameter TPP-Redirect-Preferred and the SCA method chosen by the TPP/PSU.  The parameter might be ignored by the ASPSP. If both parameters TPP-Redirect-Preferred and TPP-Decoupled-Preferred are present and true, the request is still not rejected, but it is up to the ASPSP, which approach will actually be used.  **Remark for Future:**  TPP-Redirect-Preferred and TPP-Decoupled-Preferred will be revised in future versions, maybe merged. Currently kept separate for downward compatibility.  | [optional]
 **tpPRedirectURI** | **String**| URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach, specifically  when TPP-Redirect-Preferred equals \&quot;true\&quot;. It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification.  | [optional]
 **tpPNokRedirectURI** | **String**| If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP.  | [optional]
 **tpPNotificationURI** | **String**| URI for the Endpoint of the TPP-API to which the status of the payment initiation should be sent. This header field may by ignored by the ASPSP.  For security reasons, it shall be ensured that the TPP-Notification-URI as introduced above is secured by the TPP eIDAS QWAC used for identification of the TPP. The following applies:  URIs which are provided by TPPs in TPP-Notification-URI shall comply with the domain secured by the eIDAS QWAC certificate of the TPP in the field CN or SubjectAltName of the certificate. Please note that in case of example-TPP.com as certificate entry TPP- Notification-URI like www.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications or notifications.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications would be compliant.  Wildcard definitions shall be taken into account for compliance checks by the ASPSP.  ASPSPs may respond with ASPSP-Notification-Support set to false, if the provided URIs do not comply.  | [optional]
 **tpPNotificationContentPreferred** | **String**| The string has the form   status&#x3D;X1, ..., Xn  where Xi is one of the constants SCA, PROCESS, LAST and where constants are not repeated. The usage of the constants supports the of following semantics:    SCA: A notification on every change of the scaStatus attribute for all related authorisation processes is preferred by the TPP.    PROCESS: A notification on all changes of consentStatus or transactionStatus attributes is preferred by the TPP.   LAST: Only a notification on the last consentStatus or transactionStatus as available in the XS2A interface is preferred by the TPP.  This header field may be ignored, if the ASPSP does not support resource notification services for the related TPP.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding HTTP request  IP Address field between PSU and TPP.  It shall be contained if and only if this request was actively initiated by the PSU.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

[**StartScaprocessResponse**](StartScaprocessResponse.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a name="startPaymentAuthorisation"></a>
# **startPaymentAuthorisation**
> StartScaprocessResponse startPaymentAuthorisation(xRequestID, paymentService, paymentProduct, paymentId, body, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, tpPRedirectPreferred, tpPDecoupledPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPNotificationURI, tpPNotificationContentPreferred, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Start the authorisation process for a payment initiation

Create an authorisation sub-resource and start the authorisation process.  The message might in addition transmit authentication and authorisation related data.   This method is iterated n times for a n times SCA authorisation in a  corporate context, each creating an own authorisation sub-endpoint for  the corresponding PSU authorising the transaction.  The ASPSP might make the usage of this access method unnecessary in case  of only one SCA process needed, since the related authorisation resource  might be automatically created by the ASPSP after the submission of the  payment data with the first POST payments/{payment-product} call.  The start authorisation process is a process which is needed for creating a new authorisation  or cancellation sub-resource.   This applies in the following scenarios:    * The ASPSP has indicated with a &#x27;startAuthorisation&#x27; hyperlink in the preceding Payment      initiation response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be      uploaded by using the extended forms:     * &#x27;startAuthorisationWithPsuIdentification&#x27;     * &#x27;startAuthorisationWithPsuAuthentication&#x27;     * &#x27;startAuthorisationWithEncryptedPsuAuthentication&#x27;     * &#x27;startAuthorisationWithAuthentciationMethodSelection&#x27;   * The related payment initiation cannot yet be executed since a multilevel SCA is mandated.   * The ASPSP has indicated with a &#x27;startAuthorisation&#x27; hyperlink in the preceding      Payment cancellation response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be uploaded      by using the extended forms as indicated above.   * The related payment cancellation request cannot be applied yet since a multilevel SCA is mandate for      executing the cancellation.   * The signing basket needs to be authorised yet. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String paymentService = "paymentService_example"; // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 
String paymentProduct = "paymentProduct_example"; // String | The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants. 
String paymentId = "paymentId_example"; // String | Resource identification of the generated payment initiation resource.
Object body = null; // Object | 
String PSU_ID = "PSU_ID_example"; // String | Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP's documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation. 
String psUIDType = "psUIDType_example"; // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation. 
String psUCorporateID = "psUCorporateID_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUCorporateIDType = "psUCorporateIDType_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
Boolean tpPRedirectPreferred = true; // Boolean | If it equals \"true\", the TPP prefers a redirect over an embedded SCA approach. If it equals \"false\", the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the parameter TPP-Decoupled-Preferred and the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU. 
Boolean tpPDecoupledPreferred = true; // Boolean | If it equals \"true\", the TPP prefers a decoupled SCA approach.  If it equals \"false\", the TPP prefers not to use the decoupled approach for SCA. The ASPSP will then choose between the embedded or the redirect SCA approach, depending on the choice of the SCA procedure by the TPP/PSU.  If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the parameter TPP-Redirect-Preferred and the SCA method chosen by the TPP/PSU.  The parameter might be ignored by the ASPSP. If both parameters TPP-Redirect-Preferred and TPP-Decoupled-Preferred are present and true, the request is still not rejected, but it is up to the ASPSP, which approach will actually be used.  **Remark for Future:**  TPP-Redirect-Preferred and TPP-Decoupled-Preferred will be revised in future versions, maybe merged. Currently kept separate for downward compatibility. 
String tpPRedirectURI = "tpPRedirectURI_example"; // String | URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach, specifically  when TPP-Redirect-Preferred equals \"true\". It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification. 
String tpPNokRedirectURI = "tpPNokRedirectURI_example"; // String | If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP. 
String tpPNotificationURI = "tpPNotificationURI_example"; // String | URI for the Endpoint of the TPP-API to which the status of the payment initiation should be sent. This header field may by ignored by the ASPSP.  For security reasons, it shall be ensured that the TPP-Notification-URI as introduced above is secured by the TPP eIDAS QWAC used for identification of the TPP. The following applies:  URIs which are provided by TPPs in TPP-Notification-URI shall comply with the domain secured by the eIDAS QWAC certificate of the TPP in the field CN or SubjectAltName of the certificate. Please note that in case of example-TPP.com as certificate entry TPP- Notification-URI like www.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications or notifications.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications would be compliant.  Wildcard definitions shall be taken into account for compliance checks by the ASPSP.  ASPSPs may respond with ASPSP-Notification-Support set to false, if the provided URIs do not comply. 
String tpPNotificationContentPreferred = "tpPNotificationContentPreferred_example"; // String | The string has the form   status=X1, ..., Xn  where Xi is one of the constants SCA, PROCESS, LAST and where constants are not repeated. The usage of the constants supports the of following semantics:    SCA: A notification on every change of the scaStatus attribute for all related authorisation processes is preferred by the TPP.    PROCESS: A notification on all changes of consentStatus or transactionStatus attributes is preferred by the TPP.   LAST: Only a notification on the last consentStatus or transactionStatus as available in the XS2A interface is preferred by the TPP.  This header field may be ignored, if the ASPSP does not support resource notification services for the related TPP. 
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    StartScaprocessResponse result = apiInstance.startPaymentAuthorisation(xRequestID, paymentService, paymentProduct, paymentId, body, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, tpPRedirectPreferred, tpPDecoupledPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPNotificationURI, tpPNotificationContentPreferred, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#startPaymentAuthorisation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentProduct** | **String**| The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants.  | [enum: sepa-credit-transfers, instant-sepa-credit-transfers, target-2-payments, cross-border-credit-transfers, pain.001-sepa-credit-transfers, pain.001-instant-sepa-credit-transfers, pain.001-target-2-payments, pain.001-cross-border-credit-transfers]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **body** | [**Object**](Object.md)|  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP&#x27;s documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **tpPRedirectPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers a redirect over an embedded SCA approach. If it equals \&quot;false\&quot;, the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the parameter TPP-Decoupled-Preferred and the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU.  | [optional]
 **tpPDecoupledPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers a decoupled SCA approach.  If it equals \&quot;false\&quot;, the TPP prefers not to use the decoupled approach for SCA. The ASPSP will then choose between the embedded or the redirect SCA approach, depending on the choice of the SCA procedure by the TPP/PSU.  If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the parameter TPP-Redirect-Preferred and the SCA method chosen by the TPP/PSU.  The parameter might be ignored by the ASPSP. If both parameters TPP-Redirect-Preferred and TPP-Decoupled-Preferred are present and true, the request is still not rejected, but it is up to the ASPSP, which approach will actually be used.  **Remark for Future:**  TPP-Redirect-Preferred and TPP-Decoupled-Preferred will be revised in future versions, maybe merged. Currently kept separate for downward compatibility.  | [optional]
 **tpPRedirectURI** | **String**| URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach, specifically  when TPP-Redirect-Preferred equals \&quot;true\&quot;. It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification.  | [optional]
 **tpPNokRedirectURI** | **String**| If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP.  | [optional]
 **tpPNotificationURI** | **String**| URI for the Endpoint of the TPP-API to which the status of the payment initiation should be sent. This header field may by ignored by the ASPSP.  For security reasons, it shall be ensured that the TPP-Notification-URI as introduced above is secured by the TPP eIDAS QWAC used for identification of the TPP. The following applies:  URIs which are provided by TPPs in TPP-Notification-URI shall comply with the domain secured by the eIDAS QWAC certificate of the TPP in the field CN or SubjectAltName of the certificate. Please note that in case of example-TPP.com as certificate entry TPP- Notification-URI like www.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications or notifications.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications would be compliant.  Wildcard definitions shall be taken into account for compliance checks by the ASPSP.  ASPSPs may respond with ASPSP-Notification-Support set to false, if the provided URIs do not comply.  | [optional]
 **tpPNotificationContentPreferred** | **String**| The string has the form   status&#x3D;X1, ..., Xn  where Xi is one of the constants SCA, PROCESS, LAST and where constants are not repeated. The usage of the constants supports the of following semantics:    SCA: A notification on every change of the scaStatus attribute for all related authorisation processes is preferred by the TPP.    PROCESS: A notification on all changes of consentStatus or transactionStatus attributes is preferred by the TPP.   LAST: Only a notification on the last consentStatus or transactionStatus as available in the XS2A interface is preferred by the TPP.  This header field may be ignored, if the ASPSP does not support resource notification services for the related TPP.  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

[**StartScaprocessResponse**](StartScaprocessResponse.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a name="startPaymentInitiationCancellationAuthorisation"></a>
# **startPaymentInitiationCancellationAuthorisation**
> StartScaprocessResponse startPaymentInitiationCancellationAuthorisation(xRequestID, paymentService, paymentProduct, paymentId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, tpPRedirectPreferred, tpPDecoupledPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPNotificationURI, tpPNotificationContentPreferred, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Start the authorisation process for the cancellation of the addressed payment

Creates an authorisation sub-resource and start the authorisation process of the cancellation of the addressed payment.  The message might in addition transmit authentication and authorisation related data.  This method is iterated n times for a n times SCA authorisation in a  corporate context, each creating an own authorisation sub-endpoint for  the corresponding PSU authorising the cancellation-authorisation.  The ASPSP might make the usage of this access method unnecessary in case  of only one SCA process needed, since the related authorisation resource  might be automatically created by the ASPSP after the submission of the  payment data with the first POST payments/{payment-product} call.  The start authorisation process is a process which is needed for creating a new authorisation  or cancellation sub-resource.   This applies in the following scenarios:    * The ASPSP has indicated with a &#x27;startAuthorisation&#x27; hyperlink in the preceding payment      initiation response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be      uploaded by using the extended forms:     * &#x27;startAuthorisationWithPsuIdentification&#x27;     * &#x27;startAuthorisationWithPsuAuthentication&#x27;     * &#x27;startAuthorisationWithAuthentciationMethodSelection&#x27;    * The related payment initiation cannot yet be executed since a multilevel SCA is mandated.   * The ASPSP has indicated with a &#x27;startAuthorisation&#x27; hyperlink in the preceding      payment cancellation response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be uploaded      by using the extended forms as indicated above.   * The related payment cancellation request cannot be applied yet since a multilevel SCA is mandate for      executing the cancellation.   * The signing basket needs to be authorised yet. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String paymentService = "paymentService_example"; // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 
String paymentProduct = "paymentProduct_example"; // String | The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants. 
String paymentId = "paymentId_example"; // String | Resource identification of the generated payment initiation resource.
Object body = null; // Object | 
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String PSU_ID = "PSU_ID_example"; // String | Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP's documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation. 
String psUIDType = "psUIDType_example"; // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation. 
String psUCorporateID = "psUCorporateID_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUCorporateIDType = "psUCorporateIDType_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
Boolean tpPRedirectPreferred = true; // Boolean | If it equals \"true\", the TPP prefers a redirect over an embedded SCA approach. If it equals \"false\", the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the parameter TPP-Decoupled-Preferred and the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU. 
Boolean tpPDecoupledPreferred = true; // Boolean | If it equals \"true\", the TPP prefers a decoupled SCA approach.  If it equals \"false\", the TPP prefers not to use the decoupled approach for SCA. The ASPSP will then choose between the embedded or the redirect SCA approach, depending on the choice of the SCA procedure by the TPP/PSU.  If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the parameter TPP-Redirect-Preferred and the SCA method chosen by the TPP/PSU.  The parameter might be ignored by the ASPSP. If both parameters TPP-Redirect-Preferred and TPP-Decoupled-Preferred are present and true, the request is still not rejected, but it is up to the ASPSP, which approach will actually be used.  **Remark for Future:**  TPP-Redirect-Preferred and TPP-Decoupled-Preferred will be revised in future versions, maybe merged. Currently kept separate for downward compatibility. 
String tpPRedirectURI = "tpPRedirectURI_example"; // String | URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach, specifically  when TPP-Redirect-Preferred equals \"true\". It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification. 
String tpPNokRedirectURI = "tpPNokRedirectURI_example"; // String | If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP. 
String tpPNotificationURI = "tpPNotificationURI_example"; // String | URI for the Endpoint of the TPP-API to which the status of the payment initiation should be sent. This header field may by ignored by the ASPSP.  For security reasons, it shall be ensured that the TPP-Notification-URI as introduced above is secured by the TPP eIDAS QWAC used for identification of the TPP. The following applies:  URIs which are provided by TPPs in TPP-Notification-URI shall comply with the domain secured by the eIDAS QWAC certificate of the TPP in the field CN or SubjectAltName of the certificate. Please note that in case of example-TPP.com as certificate entry TPP- Notification-URI like www.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications or notifications.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications would be compliant.  Wildcard definitions shall be taken into account for compliance checks by the ASPSP.  ASPSPs may respond with ASPSP-Notification-Support set to false, if the provided URIs do not comply. 
String tpPNotificationContentPreferred = "tpPNotificationContentPreferred_example"; // String | The string has the form   status=X1, ..., Xn  where Xi is one of the constants SCA, PROCESS, LAST and where constants are not repeated. The usage of the constants supports the of following semantics:    SCA: A notification on every change of the scaStatus attribute for all related authorisation processes is preferred by the TPP.    PROCESS: A notification on all changes of consentStatus or transactionStatus attributes is preferred by the TPP.   LAST: Only a notification on the last consentStatus or transactionStatus as available in the XS2A interface is preferred by the TPP.  This header field may be ignored, if the ASPSP does not support resource notification services for the related TPP. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    StartScaprocessResponse result = apiInstance.startPaymentInitiationCancellationAuthorisation(xRequestID, paymentService, paymentProduct, paymentId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, tpPRedirectPreferred, tpPDecoupledPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPNotificationURI, tpPNotificationContentPreferred, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#startPaymentInitiationCancellationAuthorisation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentProduct** | **String**| The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants.  | [enum: sepa-credit-transfers, instant-sepa-credit-transfers, target-2-payments, cross-border-credit-transfers, pain.001-sepa-credit-transfers, pain.001-instant-sepa-credit-transfers, pain.001-target-2-payments, pain.001-cross-border-credit-transfers]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **body** | [**Object**](Object.md)|  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP&#x27;s documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **tpPRedirectPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers a redirect over an embedded SCA approach. If it equals \&quot;false\&quot;, the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the parameter TPP-Decoupled-Preferred and the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU.  | [optional]
 **tpPDecoupledPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers a decoupled SCA approach.  If it equals \&quot;false\&quot;, the TPP prefers not to use the decoupled approach for SCA. The ASPSP will then choose between the embedded or the redirect SCA approach, depending on the choice of the SCA procedure by the TPP/PSU.  If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the parameter TPP-Redirect-Preferred and the SCA method chosen by the TPP/PSU.  The parameter might be ignored by the ASPSP. If both parameters TPP-Redirect-Preferred and TPP-Decoupled-Preferred are present and true, the request is still not rejected, but it is up to the ASPSP, which approach will actually be used.  **Remark for Future:**  TPP-Redirect-Preferred and TPP-Decoupled-Preferred will be revised in future versions, maybe merged. Currently kept separate for downward compatibility.  | [optional]
 **tpPRedirectURI** | **String**| URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach, specifically  when TPP-Redirect-Preferred equals \&quot;true\&quot;. It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification.  | [optional]
 **tpPNokRedirectURI** | **String**| If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP.  | [optional]
 **tpPNotificationURI** | **String**| URI for the Endpoint of the TPP-API to which the status of the payment initiation should be sent. This header field may by ignored by the ASPSP.  For security reasons, it shall be ensured that the TPP-Notification-URI as introduced above is secured by the TPP eIDAS QWAC used for identification of the TPP. The following applies:  URIs which are provided by TPPs in TPP-Notification-URI shall comply with the domain secured by the eIDAS QWAC certificate of the TPP in the field CN or SubjectAltName of the certificate. Please note that in case of example-TPP.com as certificate entry TPP- Notification-URI like www.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications or notifications.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications would be compliant.  Wildcard definitions shall be taken into account for compliance checks by the ASPSP.  ASPSPs may respond with ASPSP-Notification-Support set to false, if the provided URIs do not comply.  | [optional]
 **tpPNotificationContentPreferred** | **String**| The string has the form   status&#x3D;X1, ..., Xn  where Xi is one of the constants SCA, PROCESS, LAST and where constants are not repeated. The usage of the constants supports the of following semantics:    SCA: A notification on every change of the scaStatus attribute for all related authorisation processes is preferred by the TPP.    PROCESS: A notification on all changes of consentStatus or transactionStatus attributes is preferred by the TPP.   LAST: Only a notification on the last consentStatus or transactionStatus as available in the XS2A interface is preferred by the TPP.  This header field may be ignored, if the ASPSP does not support resource notification services for the related TPP.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

[**StartScaprocessResponse**](StartScaprocessResponse.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a name="startSigningBasketAuthorisation"></a>
# **startSigningBasketAuthorisation**
> StartScaprocessResponse startSigningBasketAuthorisation(xRequestID, basketId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, tpPRedirectPreferred, tpPDecoupledPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPNotificationURI, tpPNotificationContentPreferred, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Start the authorisation process for a signing basket

Create an authorisation sub-resource and start the authorisation process of a signing basket.  The message might in addition transmit authentication and authorisation related data.  This method is iterated n times for a n times SCA authorisation in a  corporate context, each creating an own authorisation sub-endpoint for  the corresponding PSU authorising the signing-baskets.  The ASPSP might make the usage of this access method unnecessary in case  of only one SCA process needed, since the related authorisation resource  might be automatically created by the ASPSP after the submission of the  payment data with the first POST signing basket call.  The start authorisation process is a process which is needed for creating a new authorisation  or cancellation sub-resource.   This applies in the following scenarios:    * The ASPSP has indicated with a &#x27;startAuthorisation&#x27; hyperlink in the preceding payment      initiation response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be      uploaded by using the extended forms:     * &#x27;startAuthorisationWithPsuIdentification&#x27;,      * &#x27;startAuthorisationWithPsuAuthentication&#x27;      * &#x27;startAuthorisationWithEncryptedPsuAuthentication&#x27;     * &#x27;startAuthorisationWithAuthentciationMethodSelection&#x27;    * The related payment initiation cannot yet be executed since a multilevel SCA is mandated.   * The ASPSP has indicated with a &#x27;startAuthorisation&#x27; hyperlink in the preceding      payment cancellation response that an explicit start of the authorisation process is needed by the TPP.     The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be uploaded     by using the extended forms as indicated above.   * The related payment cancellation request cannot be applied yet since a multilevel SCA is mandate for      executing the cancellation.   * The signing basket needs to be authorised yet. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String basketId = "basketId_example"; // String | This identification of the corresponding signing basket object. 
Object body = null; // Object | 
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String PSU_ID = "PSU_ID_example"; // String | Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP's documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation. 
String psUIDType = "psUIDType_example"; // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation. 
String psUCorporateID = "psUCorporateID_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUCorporateIDType = "psUCorporateIDType_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
Boolean tpPRedirectPreferred = true; // Boolean | If it equals \"true\", the TPP prefers a redirect over an embedded SCA approach. If it equals \"false\", the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the parameter TPP-Decoupled-Preferred and the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU. 
Boolean tpPDecoupledPreferred = true; // Boolean | If it equals \"true\", the TPP prefers a decoupled SCA approach.  If it equals \"false\", the TPP prefers not to use the decoupled approach for SCA. The ASPSP will then choose between the embedded or the redirect SCA approach, depending on the choice of the SCA procedure by the TPP/PSU.  If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the parameter TPP-Redirect-Preferred and the SCA method chosen by the TPP/PSU.  The parameter might be ignored by the ASPSP. If both parameters TPP-Redirect-Preferred and TPP-Decoupled-Preferred are present and true, the request is still not rejected, but it is up to the ASPSP, which approach will actually be used.  **Remark for Future:**  TPP-Redirect-Preferred and TPP-Decoupled-Preferred will be revised in future versions, maybe merged. Currently kept separate for downward compatibility. 
String tpPRedirectURI = "tpPRedirectURI_example"; // String | URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach, specifically  when TPP-Redirect-Preferred equals \"true\". It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification. 
String tpPNokRedirectURI = "tpPNokRedirectURI_example"; // String | If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP. 
String tpPNotificationURI = "tpPNotificationURI_example"; // String | URI for the Endpoint of the TPP-API to which the status of the payment initiation should be sent. This header field may by ignored by the ASPSP.  For security reasons, it shall be ensured that the TPP-Notification-URI as introduced above is secured by the TPP eIDAS QWAC used for identification of the TPP. The following applies:  URIs which are provided by TPPs in TPP-Notification-URI shall comply with the domain secured by the eIDAS QWAC certificate of the TPP in the field CN or SubjectAltName of the certificate. Please note that in case of example-TPP.com as certificate entry TPP- Notification-URI like www.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications or notifications.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications would be compliant.  Wildcard definitions shall be taken into account for compliance checks by the ASPSP.  ASPSPs may respond with ASPSP-Notification-Support set to false, if the provided URIs do not comply. 
String tpPNotificationContentPreferred = "tpPNotificationContentPreferred_example"; // String | The string has the form   status=X1, ..., Xn  where Xi is one of the constants SCA, PROCESS, LAST and where constants are not repeated. The usage of the constants supports the of following semantics:    SCA: A notification on every change of the scaStatus attribute for all related authorisation processes is preferred by the TPP.    PROCESS: A notification on all changes of consentStatus or transactionStatus attributes is preferred by the TPP.   LAST: Only a notification on the last consentStatus or transactionStatus as available in the XS2A interface is preferred by the TPP.  This header field may be ignored, if the ASPSP does not support resource notification services for the related TPP. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    StartScaprocessResponse result = apiInstance.startSigningBasketAuthorisation(xRequestID, basketId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, tpPRedirectPreferred, tpPDecoupledPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPNotificationURI, tpPNotificationContentPreferred, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#startSigningBasketAuthorisation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **basketId** | [**String**](.md)| This identification of the corresponding signing basket object.  |
 **body** | [**Object**](Object.md)|  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP&#x27;s documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **tpPRedirectPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers a redirect over an embedded SCA approach. If it equals \&quot;false\&quot;, the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the parameter TPP-Decoupled-Preferred and the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU.  | [optional]
 **tpPDecoupledPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers a decoupled SCA approach.  If it equals \&quot;false\&quot;, the TPP prefers not to use the decoupled approach for SCA. The ASPSP will then choose between the embedded or the redirect SCA approach, depending on the choice of the SCA procedure by the TPP/PSU.  If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the parameter TPP-Redirect-Preferred and the SCA method chosen by the TPP/PSU.  The parameter might be ignored by the ASPSP. If both parameters TPP-Redirect-Preferred and TPP-Decoupled-Preferred are present and true, the request is still not rejected, but it is up to the ASPSP, which approach will actually be used.  **Remark for Future:**  TPP-Redirect-Preferred and TPP-Decoupled-Preferred will be revised in future versions, maybe merged. Currently kept separate for downward compatibility.  | [optional]
 **tpPRedirectURI** | **String**| URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach, specifically  when TPP-Redirect-Preferred equals \&quot;true\&quot;. It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification.  | [optional]
 **tpPNokRedirectURI** | **String**| If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP.  | [optional]
 **tpPNotificationURI** | **String**| URI for the Endpoint of the TPP-API to which the status of the payment initiation should be sent. This header field may by ignored by the ASPSP.  For security reasons, it shall be ensured that the TPP-Notification-URI as introduced above is secured by the TPP eIDAS QWAC used for identification of the TPP. The following applies:  URIs which are provided by TPPs in TPP-Notification-URI shall comply with the domain secured by the eIDAS QWAC certificate of the TPP in the field CN or SubjectAltName of the certificate. Please note that in case of example-TPP.com as certificate entry TPP- Notification-URI like www.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications or notifications.example-TPP.com/xs2a-client/v1/ASPSPidentifcation/mytransaction- id/notifications would be compliant.  Wildcard definitions shall be taken into account for compliance checks by the ASPSP.  ASPSPs may respond with ASPSP-Notification-Support set to false, if the provided URIs do not comply.  | [optional]
 **tpPNotificationContentPreferred** | **String**| The string has the form   status&#x3D;X1, ..., Xn  where Xi is one of the constants SCA, PROCESS, LAST and where constants are not repeated. The usage of the constants supports the of following semantics:    SCA: A notification on every change of the scaStatus attribute for all related authorisation processes is preferred by the TPP.    PROCESS: A notification on all changes of consentStatus or transactionStatus attributes is preferred by the TPP.   LAST: Only a notification on the last consentStatus or transactionStatus as available in the XS2A interface is preferred by the TPP.  This header field may be ignored, if the ASPSP does not support resource notification services for the related TPP.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

[**StartScaprocessResponse**](StartScaprocessResponse.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a name="updateConsentsPsuData"></a>
# **updateConsentsPsuData**
> Object updateConsentsPsuData(xRequestID, consentId, authorisationId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Update PSU Data for consents

This method update PSU data on the consents  resource if needed.  It may authorise a consent within the Embedded SCA Approach where needed.  Independently from the SCA Approach it supports e.g. the selection of  the authentication method and a non-SCA PSU authentication.  This methods updates PSU data on the cancellation authorisation resource if needed.   There are several possible update PSU data requests in the context of a consent request if needed,  which depends on the SCA approach:  * Redirect SCA Approach:   A specific Update PSU data request is applicable for      * the selection of authentication methods, before choosing the actual SCA approach. * Decoupled SCA Approach:   A specific update PSU data request is only applicable for   * adding the PSU Identification, if not provided yet in the payment initiation request or the Account Information Consent Request, or if no OAuth2 access token is used, or   * the selection of authentication methods. * Embedded SCA Approach:    The Update PSU data request might be used    * to add credentials as a first factor authentication data of the PSU and   * to select the authentication method and   * transaction authorisation.  The SCA Approach might depend on the chosen SCA method.  For that reason, the following possible update PSU data request can apply to all SCA approaches:  * Select an SCA method in case of several SCA methods are available for the customer.  There are the following request types on this access path:   * Update PSU identification   * Update PSU authentication   * Select PSU authorization method      WARNING: This method needs a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change.   * Transaction Authorisation     WARNING: This method needs a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String consentId = "consentId_example"; // String | ID of the corresponding consent object as returned by an account information consent request. 
String authorisationId = "authorisationId_example"; // String | Resource identification of the related SCA.
Object body = {
  "value" : { }
}; // Object | 
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String PSU_ID = "PSU_ID_example"; // String | Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP's documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation. 
String psUIDType = "psUIDType_example"; // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation. 
String psUCorporateID = "psUCorporateID_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUCorporateIDType = "psUCorporateIDType_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding HTTP request  IP Address field between PSU and TPP.  It shall be contained if and only if this request was actively initiated by the PSU. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    Object result = apiInstance.updateConsentsPsuData(xRequestID, consentId, authorisationId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#updateConsentsPsuData");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **consentId** | [**String**](.md)| ID of the corresponding consent object as returned by an account information consent request.  |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **body** | [**Object**](Object.md)|  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP&#x27;s documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding HTTP request  IP Address field between PSU and TPP.  It shall be contained if and only if this request was actively initiated by the PSU.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

**Object**

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a name="updatePaymentCancellationPsuData"></a>
# **updatePaymentCancellationPsuData**
> Object updatePaymentCancellationPsuData(xRequestID, paymentService, paymentProduct, paymentId, authorisationId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Update PSU data for payment initiation cancellation

This method updates PSU data on the cancellation authorisation resource if needed.  It may authorise a cancellation of the payment within the Embedded SCA Approach where needed.  Independently from the SCA Approach it supports e.g. the selection of  the authentication method and a non-SCA PSU authentication.  This methods updates PSU data on the cancellation authorisation resource if needed.   There are several possible update PSU data requests in the context of a cancellation authorisation within the payment initiation services needed,  which depends on the SCA approach:  * Redirect SCA Approach:   A specific Update PSU data request is applicable for      * the selection of authentication methods, before choosing the actual SCA approach. * Decoupled SCA Approach:   A specific Update PSU data request is only applicable for   * adding the PSU Identification, if not provided yet in the payment initiation request or the Account Information Consent Request, or if no OAuth2 access token is used, or   * the selection of authentication methods. * Embedded SCA Approach:    The Update PSU data request might be used    * to add credentials as a first factor authentication data of the PSU and   * to select the authentication method and   * transaction authorisation.  The SCA approach might depend on the chosen SCA method.  For that reason, the following possible update PSU data request can apply to all SCA approaches:  * Select an SCA method in case of several SCA methods are available for the customer.  There are the following request types on this access path:   * Update PSU identification   * Update PSU authentication   * Select PSU authorization method      WARNING: This method needs a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change.   * Transaction Authorisation     WARNING: This method needs a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String paymentService = "paymentService_example"; // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 
String paymentProduct = "paymentProduct_example"; // String | The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants. 
String paymentId = "paymentId_example"; // String | Resource identification of the generated payment initiation resource.
String authorisationId = "authorisationId_example"; // String | Resource identification of the related SCA.
Object body = {
  "value" : { }
}; // Object | 
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String PSU_ID = "PSU_ID_example"; // String | Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP's documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation. 
String psUIDType = "psUIDType_example"; // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation. 
String psUCorporateID = "psUCorporateID_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUCorporateIDType = "psUCorporateIDType_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    Object result = apiInstance.updatePaymentCancellationPsuData(xRequestID, paymentService, paymentProduct, paymentId, authorisationId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#updatePaymentCancellationPsuData");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentProduct** | **String**| The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants.  | [enum: sepa-credit-transfers, instant-sepa-credit-transfers, target-2-payments, cross-border-credit-transfers, pain.001-sepa-credit-transfers, pain.001-instant-sepa-credit-transfers, pain.001-target-2-payments, pain.001-cross-border-credit-transfers]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **body** | [**Object**](Object.md)|  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP&#x27;s documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

**Object**

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a name="updatePaymentPsuData"></a>
# **updatePaymentPsuData**
> Object updatePaymentPsuData(xRequestID, paymentService, paymentProduct, paymentId, authorisationId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Update PSU data for payment initiation

This methods updates PSU data on the authorisation resource if needed.  It may authorise a payment within the Embedded SCA Approach where needed.  Independently from the SCA Approach it supports e.g. the selection of  the authentication method and a non-SCA PSU authentication.  There are several possible update PSU data requests in the context of payment initiation services needed,  which depends on the SCA approach:  * Redirect SCA Approach:   A specific update PSU data request is applicable for      * the selection of authentication methods, before choosing the actual SCA approach. * Decoupled SCA Approach:   A specific update PSU data request is only applicable for   * adding the PSU identification, if not provided yet in the payment initiation request or the account information consent request, or if no OAuth2 access token is used, or   * the selection of authentication methods. * Embedded SCA Approach:    The Update PSU Data request might be used    * to add credentials as a first factor authentication data of the PSU and   * to select the authentication method and   * transaction authorisation.  The SCA Approach might depend on the chosen SCA method.  For that reason, the following possible Update PSU data request can apply to all SCA approaches:  * Select an SCA method in case of several SCA methods are available for the customer.  There are the following request types on this access path:   * Update PSU identification   * Update PSU authentication   * Select PSU authorization method      WARNING: This method needs a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change.   * Transaction authorisation     WARNING: This method needs a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String paymentService = "paymentService_example"; // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 
String paymentProduct = "paymentProduct_example"; // String | The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants. 
String paymentId = "paymentId_example"; // String | Resource identification of the generated payment initiation resource.
String authorisationId = "authorisationId_example"; // String | Resource identification of the related SCA.
Object body = {
  "value" : { }
}; // Object | 
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String PSU_ID = "PSU_ID_example"; // String | Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP's documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation. 
String psUIDType = "psUIDType_example"; // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation. 
String psUCorporateID = "psUCorporateID_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUCorporateIDType = "psUCorporateIDType_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    Object result = apiInstance.updatePaymentPsuData(xRequestID, paymentService, paymentProduct, paymentId, authorisationId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#updatePaymentPsuData");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentProduct** | **String**| The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specific scheme variants.  | [enum: sepa-credit-transfers, instant-sepa-credit-transfers, target-2-payments, cross-border-credit-transfers, pain.001-sepa-credit-transfers, pain.001-instant-sepa-credit-transfers, pain.001-target-2-payments, pain.001-cross-border-credit-transfers]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **body** | [**Object**](Object.md)|  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP&#x27;s documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

**Object**

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

<a name="updateSigningBasketPsuData"></a>
# **updateSigningBasketPsuData**
> Object updateSigningBasketPsuData(xRequestID, basketId, authorisationId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Update PSU data for signing basket

This method update PSU data on the signing basket resource if needed.  It may authorise a igning basket within the embedded SCA approach where needed.  Independently from the SCA Approach it supports e.g. the selection of  the authentication method and a non-SCA PSU authentication.  This methods updates PSU data on the cancellation authorisation resource if needed.   There are several possible update PSU data requests in the context of a consent request if needed,  which depends on the SCA approach:  * Redirect SCA Approach:   A specific Update PSU data request is applicable for      * the selection of authentication methods, before choosing the actual SCA approach. * Decoupled SCA Approach:   A specific Update PSU data request is only applicable for   * adding the PSU Identification, if not provided yet in the payment initiation request or the account information consent request, or if no OAuth2 access token is used, or   * the selection of authentication methods. * Embedded SCA Approach:    The update PSU data request might be used    * to add credentials as a first factor authentication data of the PSU and   * to select the authentication method and   * transaction authorisation.  The SCA approach might depend on the chosen SCA method.  For that reason, the following possible update PSU data request can apply to all SCA approaches:  * Select an SCA method in case of several SCA methods are available for the customer.  There are the following request types on this access path:   * Update PSU identification   * Update PSU authentication   * Select PSU authorization Method      WARNING: This method needs a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change.   * Transaction Authorisation     WARNING: This method needs a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change. 

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.CommonServicesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


CommonServicesApi apiInstance = new CommonServicesApi();
UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String basketId = "basketId_example"; // String | This identification of the corresponding signing basket object. 
String authorisationId = "authorisationId_example"; // String | Resource identification of the related SCA.
Object body = {
  "value" : { }
}; // Object | 
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
String PSU_ID = "PSU_ID_example"; // String | Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP's documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation. 
String psUIDType = "psUIDType_example"; // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation. 
String psUCorporateID = "psUCorporateID_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUCorporateIDType = "psUCorporateIDType_example"; // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 
String psUIPAddress = "psUIPAddress_example"; // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 
String psUIPPort = "psUIPPort_example"; // String | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 
String psUAccept = "psUAccept_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptCharset = "psUAcceptCharset_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptEncoding = "psUAcceptEncoding_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUAcceptLanguage = "psUAcceptLanguage_example"; // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 
String psUUserAgent = "psUUserAgent_example"; // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 
String psUHttpMethod = "psUHttpMethod_example"; // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 
UUID psUDeviceID = new UUID(); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device. 
String psUGeoLocation = "psUGeoLocation_example"; // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 
try {
    Object result = apiInstance.updateSigningBasketPsuData(xRequestID, basketId, authorisationId, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonServicesApi#updateSigningBasketPsuData");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **basketId** | [**String**](.md)| This identification of the corresponding signing basket object.  |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **body** | [**Object**](Object.md)|  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface.   Might be mandated in the ASPSP&#x27;s documentation.  It might be contained even if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceding AIS service in the same session. In this case the ASPSP might check whether PSU-ID and token match,  according to ASPSP documentation.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  In this case, the mean and use are then defined in the ASPSP’s documentation.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | **String**| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID needs to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]

### Return type

**Object**

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

