# CommonAisAndPisServicesApi

All URIs are relative to *https://api.testbank.com/psd2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getConsentScaStatus**](CommonAisAndPisServicesApi.md#getConsentScaStatus) | **GET** /v1/consents/{consentId}/authorisations/{authorisationId} | Read the SCA status of the consent authorisation.
[**getPaymentCancellationScaStatus**](CommonAisAndPisServicesApi.md#getPaymentCancellationScaStatus) | **GET** /v1/{payment-service}/{paymentId}/cancellation-authorisations/{cancellationId} | Read the SCA status of the payment cancellation&#x27;s authorisation.
[**getPaymentInitiationAuthorisation**](CommonAisAndPisServicesApi.md#getPaymentInitiationAuthorisation) | **GET** /v1/{payment-service}/{paymentId}/authorisations | Get Payment Initiation Authorisation Sub-Resources Request
[**getPaymentInitiationAuthorisation_0**](CommonAisAndPisServicesApi.md#getPaymentInitiationAuthorisation_0) | **GET** /v1/signing-baskets/{basketId}/authorisations | Get Signing Basket Authorisation Sub-Resources Request
[**getPaymentInitiationScaStatus**](CommonAisAndPisServicesApi.md#getPaymentInitiationScaStatus) | **GET** /v1/{payment-service}/{paymentId}/authorisations/{authorisationId} | Read the SCA Status of the payment authorisation
[**getSigningBasketScaStatus**](CommonAisAndPisServicesApi.md#getSigningBasketScaStatus) | **GET** /v1/signing-baskets/{basketId}/authorisations/{authorisationId} | Read the SCA status of the signing basket authorisation
[**startConsentAuthorisation**](CommonAisAndPisServicesApi.md#startConsentAuthorisation) | **POST** /v1/consents/{consentId}/authorisations | Start the authorisation process for a consent
[**startPaymentAuthorisation**](CommonAisAndPisServicesApi.md#startPaymentAuthorisation) | **POST** /v1/{payment-service}/{paymentId}/authorisations | Start the authorisation process for a payment initiation
[**startPaymentInitiationCancellationAuthorisation**](CommonAisAndPisServicesApi.md#startPaymentInitiationCancellationAuthorisation) | **POST** /v1/{payment-service}/{paymentId}/cancellation-authorisations | Start the authorisation process for the cancellation of the addressed payment
[**startSigningBasketAuthorisation**](CommonAisAndPisServicesApi.md#startSigningBasketAuthorisation) | **POST** /v1/signing-baskets/{basketId}/authorisations | Start the authorisation process for a signing basket
[**updateConsentsPsuData**](CommonAisAndPisServicesApi.md#updateConsentsPsuData) | **PUT** /v1/consents/{consentId}/authorisations/{authorisationId} | Update PSU Data for consents
[**updatePaymentCancellationPsuData**](CommonAisAndPisServicesApi.md#updatePaymentCancellationPsuData) | **PUT** /v1/{payment-service}/{paymentId}/cancellation-authorisations/{cancellationId} | Update PSU Data for payment initiation cancellation
[**updatePaymentPsuData**](CommonAisAndPisServicesApi.md#updatePaymentPsuData) | **PUT** /v1/{payment-service}/{paymentId}/authorisations/{authorisationId} | Update PSU data for payment initiation
[**updateSigningBasketPsuData**](CommonAisAndPisServicesApi.md#updateSigningBasketPsuData) | **PUT** /v1/signing-baskets/{basketId}/authorisations/{authorisationId} | Update PSU Data for signing basket




<a name="getConsentScaStatus"></a>
# **getConsentScaStatus**
> getConsentScaStatus(consentId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read the SCA status of the consent authorisation.

This method returns the SCA status of a consent initiation&#x27;s authorisation sub-resource. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String consentId = Arrays.asList("consentId_example"); // String | ID of the corresponding consent object as returned by an Account Information Consent Request. 

String authorisationId = Arrays.asList("authorisationId_example"); // String | Resource identification of the related SCA.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.getConsentScaStatus(consentId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#getConsentScaStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **consentId** | [**String**](.md)| ID of the corresponding consent object as returned by an Account Information Consent Request.  |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getPaymentCancellationScaStatus"></a>
# **getPaymentCancellationScaStatus**
> getPaymentCancellationScaStatus(paymentService, paymentId, cancellationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read the SCA status of the payment cancellation&#x27;s authorisation.

This method returns the SCA status of a payment initiation&#x27;s authorisation sub-resource. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String paymentService = Arrays.asList("paymentService_example"); // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 

String paymentId = Arrays.asList("paymentId_example"); // String | Resource identification of the generated payment initiation resource.

String cancellationId = Arrays.asList("cancellationId_example"); // String | Identification for cancellation resource.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.getPaymentCancellationScaStatus(paymentService, paymentId, cancellationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#getPaymentCancellationScaStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **cancellationId** | [**String**](.md)| Identification for cancellation resource. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getPaymentInitiationAuthorisation"></a>
# **getPaymentInitiationAuthorisation**
> getPaymentInitiationAuthorisation(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Get Payment Initiation Authorisation Sub-Resources Request

Read a list of all authorisation subresources IDs which have been created.  This function returns an array of hyperlinks to all generated authorisation sub-resources. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String paymentService = Arrays.asList("paymentService_example"); // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 

String paymentId = Arrays.asList("paymentId_example"); // String | Resource identification of the generated payment initiation resource.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.getPaymentInitiationAuthorisation(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#getPaymentInitiationAuthorisation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getPaymentInitiationAuthorisation_0"></a>
# **getPaymentInitiationAuthorisation_0**
> getPaymentInitiationAuthorisation_0(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Get Signing Basket Authorisation Sub-Resources Request

Read a list of all authorisation subresources IDs which have been created.  This function returns an array of hyperlinks to all generated authorisation sub-resources. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String paymentService = Arrays.asList("paymentService_example"); // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 

String paymentId = Arrays.asList("paymentId_example"); // String | Resource identification of the generated payment initiation resource.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.getPaymentInitiationAuthorisation_0(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#getPaymentInitiationAuthorisation_0");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getPaymentInitiationScaStatus"></a>
# **getPaymentInitiationScaStatus**
> getPaymentInitiationScaStatus(paymentService, paymentId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read the SCA Status of the payment authorisation

This method returns the SCA status of a payment initiation&#x27;s authorisation sub-resource. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String paymentService = Arrays.asList("paymentService_example"); // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 

String paymentId = Arrays.asList("paymentId_example"); // String | Resource identification of the generated payment initiation resource.

String authorisationId = Arrays.asList("authorisationId_example"); // String | Resource identification of the related SCA.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.getPaymentInitiationScaStatus(paymentService, paymentId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#getPaymentInitiationScaStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="getSigningBasketScaStatus"></a>
# **getSigningBasketScaStatus**
> getSigningBasketScaStatus(basketId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read the SCA status of the signing basket authorisation

This method returns the SCA status of a signing basket&#x27;s authorisation sub-resource. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String basketId = Arrays.asList("basketId_example"); // String | This identification of the corresponding signing basket object. 

String authorisationId = Arrays.asList("authorisationId_example"); // String | Resource identification of the related SCA.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.getSigningBasketScaStatus(basketId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#getSigningBasketScaStatus");
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
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="startConsentAuthorisation"></a>
# **startConsentAuthorisation**
> startConsentAuthorisation(consentId, xRequestID, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Start the authorisation process for a consent

Create an authorisation sub-resource and start the authorisation process of a consent.  The message might in addition transmit authentication and authorisation related data.  his method is iterated n times for a n times SCA authorisation in a  corporate context, each creating an own authorisation sub-endpoint for  the corresponding PSU authorising the consent.  The ASPSP might make the usage of this access method unnecessary,  since the related authorisation resource will be automatically created by  the ASPSP after the submission of the consent data with the first POST consents call.  The start authorisation process is a process which is needed for creating a new authorisation  or cancellation sub-resource.   This applies in the following scenarios:    * The ASPSP has indicated with an &#x27;startAuthorisation&#x27; hyperlink in the preceeding Payment      Initiation Response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be      uploaded by using the extended forms.     * &#x27;startAuthorisationWithPsuIdentfication&#x27;,      * &#x27;startAuthorisationWithPsuAuthentication&#x27;     * &#x27;startAuthorisationWithAuthentciationMethodSelection&#x27;    * The related payment initiation cannot yet be executed since a multilevel SCA is mandated.   * The ASPSP has indicated with an &#x27;startAuthorisation&#x27; hyperlink in the preceeding      Payment Cancellation Response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be uploaded      by using the extended forms as indicated above.   * The related payment cancellation request cannot be applied yet since a multilevel SCA is mandate for      executing the cancellation.   * The signing basket needs to be authorised yet. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String consentId = Arrays.asList("consentId_example"); // String | ID of the corresponding consent object as returned by an Account Information Consent Request. 

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String PSU_ID = Arrays.asList("PSU_ID_example"); // String | Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP's documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session. 

String psUIDType = Arrays.asList("psUIDType_example"); // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility. 

String psUCorporateID = Arrays.asList("psUCorporateID_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUCorporateIDType = Arrays.asList("psUCorporateIDType_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.startConsentAuthorisation(consentId, xRequestID, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#startConsentAuthorisation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **consentId** | [**String**](.md)| ID of the corresponding consent object as returned by an Account Information Consent Request.  |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP&#x27;s documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="startPaymentAuthorisation"></a>
# **startPaymentAuthorisation**
> startPaymentAuthorisation(paymentService, paymentId, xRequestID, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Start the authorisation process for a payment initiation

Create an authorisation sub-resource and start the authorisation process.  The message might in addition transmit authentication and authorisation related data.   This method is iterated n times for a n times SCA authorisation in a  corporate context, each creating an own authorisation sub-endpoint for  the corresponding PSU authorising the transaction.  The ASPSP might make the usage of this access method unnecessary in case  of only one SCA process needed, since the related authorisation resource  might be automatically created by the ASPSP after the submission of the  payment data with the first POST payments/{payment-product} call.  The start authorisation process is a process which is needed for creating a new authorisation  or cancellation sub-resource.   This applies in the following scenarios:    * The ASPSP has indicated with an &#x27;startAuthorisation&#x27; hyperlink in the preceeding Payment      Initiation Response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be      uploaded by using the extended forms.     * &#x27;startAuthorisationWithPsuIdentfication&#x27;,      * &#x27;startAuthorisationWithPsuAuthentication&#x27;     * &#x27;startAuthorisationWithAuthentciationMethodSelection&#x27;    * The related payment initiation cannot yet be executed since a multilevel SCA is mandated.   * The ASPSP has indicated with an &#x27;startAuthorisation&#x27; hyperlink in the preceeding      Payment Cancellation Response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be uploaded      by using the extended forms as indicated above.   * The related payment cancellation request cannot be applied yet since a multilevel SCA is mandate for      executing the cancellation.   * The signing basket needs to be authorised yet. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String paymentService = Arrays.asList("paymentService_example"); // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 

String paymentId = Arrays.asList("paymentId_example"); // String | Resource identification of the generated payment initiation resource.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String PSU_ID = Arrays.asList("PSU_ID_example"); // String | Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP's documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session. 

String psUIDType = Arrays.asList("psUIDType_example"); // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility. 

String psUCorporateID = Arrays.asList("psUCorporateID_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUCorporateIDType = Arrays.asList("psUCorporateIDType_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.startPaymentAuthorisation(paymentService, paymentId, xRequestID, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#startPaymentAuthorisation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP&#x27;s documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="startPaymentInitiationCancellationAuthorisation"></a>
# **startPaymentInitiationCancellationAuthorisation**
> startPaymentInitiationCancellationAuthorisation(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Start the authorisation process for the cancellation of the addressed payment

Creates an authorisation sub-resource and start the authorisation process of the cancellation of the addressed payment.  The message might in addition transmit authentication and authorisation related data.  This method is iterated n times for a n times SCA authorisation in a  corporate context, each creating an own authorisation sub-endpoint for  the corresponding PSU authorising the cancellation-authorisation.  The ASPSP might make the usage of this access method unnecessary in case  of only one SCA process needed, since the related authorisation resource  might be automatically created by the ASPSP after the submission of the  payment data with the first POST payments/{payment-product} call.  The start authorisation process is a process which is needed for creating a new authorisation  or cancellation sub-resource.   This applies in the following scenarios:    * The ASPSP has indicated with an &#x27;startAuthorisation&#x27; hyperlink in the preceeding Payment      Initiation Response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be      uploaded by using the extended forms.     * &#x27;startAuthorisationWithPsuIdentfication&#x27;,      * &#x27;startAuthorisationWithPsuAuthentication&#x27;     * &#x27;startAuthorisationWithAuthentciationMethodSelection&#x27;    * The related payment initiation cannot yet be executed since a multilevel SCA is mandated.   * The ASPSP has indicated with an &#x27;startAuthorisation&#x27; hyperlink in the preceeding      Payment Cancellation Response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be uploaded      by using the extended forms as indicated above.   * The related payment cancellation request cannot be applied yet since a multilevel SCA is mandate for      executing the cancellation.   * The signing basket needs to be authorised yet. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String paymentService = Arrays.asList("paymentService_example"); // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 

String paymentId = Arrays.asList("paymentId_example"); // String | Resource identification of the generated payment initiation resource.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String PSU_ID = Arrays.asList("PSU_ID_example"); // String | Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP's documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session. 

String psUIDType = Arrays.asList("psUIDType_example"); // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility. 

String psUCorporateID = Arrays.asList("psUCorporateID_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUCorporateIDType = Arrays.asList("psUCorporateIDType_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.startPaymentInitiationCancellationAuthorisation(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#startPaymentInitiationCancellationAuthorisation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP&#x27;s documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="startSigningBasketAuthorisation"></a>
# **startSigningBasketAuthorisation**
> startSigningBasketAuthorisation(basketId, xRequestID, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Start the authorisation process for a signing basket

Create an authorisation sub-resource and start the authorisation process of a signing basket.  The message might in addition transmit authentication and authorisation related data.  This method is iterated n times for a n times SCA authorisation in a  corporate context, each creating an own authorisation sub-endpoint for  the corresponding PSU authorising the signing-baskets.  The ASPSP might make the usage of this access method unnecessary in case  of only one SCA process needed, since the related authorisation resource  might be automatically created by the ASPSP after the submission of the  payment data with the first POST signing basket call.  The start authorisation process is a process which is needed for creating a new authorisation  or cancellation sub-resource.   This applies in the following scenarios:    * The ASPSP has indicated with an &#x27;startAuthorisation&#x27; hyperlink in the preceeding Payment      Initiation Response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be      uploaded by using the extended forms.     * &#x27;startAuthorisationWithPsuIdentfication&#x27;,      * &#x27;startAuthorisationWithPsuAuthentication&#x27;     * &#x27;startAuthorisationWithAuthentciationMethodSelection&#x27;    * The related payment initiation cannot yet be executed since a multilevel SCA is mandated.   * The ASPSP has indicated with an &#x27;startAuthorisation&#x27; hyperlink in the preceeding      Payment Cancellation Response that an explicit start of the authorisation process is needed by the TPP.      The &#x27;startAuthorisation&#x27; hyperlink can transport more information about data which needs to be uploaded      by using the extended forms as indicated above.   * The related payment cancellation request cannot be applied yet since a multilevel SCA is mandate for      executing the cancellation.   * The signing basket needs to be authorised yet. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String basketId = Arrays.asList("basketId_example"); // String | This identification of the corresponding signing basket object. 

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String PSU_ID = Arrays.asList("PSU_ID_example"); // String | Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP's documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session. 

String psUIDType = Arrays.asList("psUIDType_example"); // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility. 

String psUCorporateID = Arrays.asList("psUCorporateID_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUCorporateIDType = Arrays.asList("psUCorporateIDType_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.startSigningBasketAuthorisation(basketId, xRequestID, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#startSigningBasketAuthorisation");
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
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP&#x27;s documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


<a name="updateConsentsPsuData"></a>
# **updateConsentsPsuData**
> updateConsentsPsuData(consentId, authorisationId, xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Update PSU Data for consents

This method update PSU data on the consents  resource if needed.  It may authorise a consent within the Embedded SCA Approach where needed.  Independently from the SCA Approach it supports e.g. the selection of  the authentication method and a non-SCA PSU authentication.  This methods updates PSU data on the cancellation authorisation resource if needed.   There are several possible Update PSU Data requests in the context of a consent request if needed,  which depends on the SCA approach:  * Redirect SCA Approach:   A specific Update PSU Data Request is applicable for      * the selection of authentication methods, before choosing the actual SCA approach. * Decoupled SCA Approach:   A specific Update PSU Data Request is only applicable for   * adding the PSU Identification, if not provided yet in the Payment Initiation Request or the Account Information Consent Request, or if no OAuth2 access token is used, or   * the selection of authentication methods. * Embedded SCA Approach:    The Update PSU Data Request might be used    * to add credentials as a first factor authentication data of the PSU and   * to select the authentication method and   * transaction authorisation.  The SCA Approach might depend on the chosen SCA method.  For that reason, the following possible Update PSU Data request can apply to all SCA approaches:  * Select an SCA method in case of several SCA methods are available for the customer.  There are the following request types on this access path:   * Update PSU Identification   * Update PSU Authentication   * Select PSU Autorization Method      WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change.   * Transaction Authorisation     WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String consentId = Arrays.asList("consentId_example"); // String | ID of the corresponding consent object as returned by an Account Information Consent Request. 

String authorisationId = Arrays.asList("authorisationId_example"); // String | Resource identification of the related SCA.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

Object body = null; // Object | 

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String PSU_ID = Arrays.asList("PSU_ID_example"); // String | Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP's documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session. 

String psUIDType = Arrays.asList("psUIDType_example"); // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility. 

String psUCorporateID = Arrays.asList("psUCorporateID_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUCorporateIDType = Arrays.asList("psUCorporateIDType_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.updateConsentsPsuData(consentId, authorisationId, xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#updateConsentsPsuData");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **consentId** | [**String**](.md)| ID of the corresponding consent object as returned by an Account Information Consent Request.  |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **body** | [**Object**](Object.md)|  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP&#x27;s documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


<a name="updatePaymentCancellationPsuData"></a>
# **updatePaymentCancellationPsuData**
> updatePaymentCancellationPsuData(paymentService, paymentId, cancellationId, xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Update PSU Data for payment initiation cancellation

This method updates PSU data on the cancellation authorisation resource if needed.  It may authorise a cancellation of the payment within the Embedded SCA Approach where needed.  Independently from the SCA Approach it supports e.g. the selection of  the authentication method and a non-SCA PSU authentication.  This methods updates PSU data on the cancellation authorisation resource if needed.   There are several possible Update PSU Data requests in the context of a cancellation authorisation within the payment initiation services needed,  which depends on the SCA approach:  * Redirect SCA Approach:   A specific Update PSU Data Request is applicable for      * the selection of authentication methods, before choosing the actual SCA approach. * Decoupled SCA Approach:   A specific Update PSU Data Request is only applicable for   * adding the PSU Identification, if not provided yet in the Payment Initiation Request or the Account Information Consent Request, or if no OAuth2 access token is used, or   * the selection of authentication methods. * Embedded SCA Approach:    The Update PSU Data Request might be used    * to add credentials as a first factor authentication data of the PSU and   * to select the authentication method and   * transaction authorisation.  The SCA Approach might depend on the chosen SCA method.  For that reason, the following possible Update PSU Data request can apply to all SCA approaches:  * Select an SCA method in case of several SCA methods are available for the customer.  There are the following request types on this access path:   * Update PSU Identification   * Update PSU Authentication   * Select PSU Autorization Method      WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change.   * Transaction Authorisation     WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String paymentService = Arrays.asList("paymentService_example"); // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 

String paymentId = Arrays.asList("paymentId_example"); // String | Resource identification of the generated payment initiation resource.

String cancellationId = Arrays.asList("cancellationId_example"); // String | Identification for cancellation resource.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

Object body = null; // Object | 

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String PSU_ID = Arrays.asList("PSU_ID_example"); // String | Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP's documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session. 

String psUIDType = Arrays.asList("psUIDType_example"); // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility. 

String psUCorporateID = Arrays.asList("psUCorporateID_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUCorporateIDType = Arrays.asList("psUCorporateIDType_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.updatePaymentCancellationPsuData(paymentService, paymentId, cancellationId, xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#updatePaymentCancellationPsuData");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **cancellationId** | [**String**](.md)| Identification for cancellation resource. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **body** | [**Object**](Object.md)|  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP&#x27;s documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


<a name="updatePaymentPsuData"></a>
# **updatePaymentPsuData**
> updatePaymentPsuData(paymentService, paymentId, authorisationId, xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Update PSU data for payment initiation

This methods updates PSU data on the authorisation resource if needed.  It may authorise a payment within the Embedded SCA Approach where needed.  Independently from the SCA Approach it supports e.g. the selection of  the authentication method and a non-SCA PSU authentication.  There are several possible Update PSU Data requests in the context of payment initiation services needed,  which depends on the SCA approach:  * Redirect SCA Approach:   A specific Update PSU Data Request is applicable for      * the selection of authentication methods, before choosing the actual SCA approach. * Decoupled SCA Approach:   A specific Update PSU Data Request is only applicable for   * adding the PSU Identification, if not provided yet in the Payment Initiation Request or the Account Information Consent Request, or if no OAuth2 access token is used, or   * the selection of authentication methods. * Embedded SCA Approach:    The Update PSU Data Request might be used    * to add credentials as a first factor authentication data of the PSU and   * to select the authentication method and   * transaction authorisation.  The SCA Approach might depend on the chosen SCA method.  For that reason, the following possible Update PSU Data request can apply to all SCA approaches:  * Select an SCA method in case of several SCA methods are available for the customer.  There are the following request types on this access path:   * Update PSU Identification   * Update PSU Authentication   * Select PSU Autorization Method      WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change.   * Transaction Authorisation     WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String paymentService = Arrays.asList("paymentService_example"); // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 

String paymentId = Arrays.asList("paymentId_example"); // String | Resource identification of the generated payment initiation resource.

String authorisationId = Arrays.asList("authorisationId_example"); // String | Resource identification of the related SCA.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

Object body = null; // Object | 

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String PSU_ID = Arrays.asList("PSU_ID_example"); // String | Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP's documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session. 

String psUIDType = Arrays.asList("psUIDType_example"); // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility. 

String psUCorporateID = Arrays.asList("psUCorporateID_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUCorporateIDType = Arrays.asList("psUCorporateIDType_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.updatePaymentPsuData(paymentService, paymentId, authorisationId, xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#updatePaymentPsuData");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentId** | [**String**](.md)| Resource identification of the generated payment initiation resource. |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **body** | [**Object**](Object.md)|  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP&#x27;s documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


<a name="updateSigningBasketPsuData"></a>
# **updateSigningBasketPsuData**
> updateSigningBasketPsuData(basketId, authorisationId, xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Update PSU Data for signing basket

This method update PSU data on the signing basket resource if needed.  It may authorise a igning basket within the Embedded SCA Approach where needed.  Independently from the SCA Approach it supports e.g. the selection of  the authentication method and a non-SCA PSU authentication.  This methods updates PSU data on the cancellation authorisation resource if needed.   There are several possible Update PSU Data requests in the context of a consent request if needed,  which depends on the SCA approach:  * Redirect SCA Approach:   A specific Update PSU Data Request is applicable for      * the selection of authentication methods, before choosing the actual SCA approach. * Decoupled SCA Approach:   A specific Update PSU Data Request is only applicable for   * adding the PSU Identification, if not provided yet in the Payment Initiation Request or the Account Information Consent Request, or if no OAuth2 access token is used, or   * the selection of authentication methods. * Embedded SCA Approach:    The Update PSU Data Request might be used    * to add credentials as a first factor authentication data of the PSU and   * to select the authentication method and   * transaction authorisation.  The SCA Approach might depend on the chosen SCA method.  For that reason, the following possible Update PSU Data request can apply to all SCA approaches:  * Select an SCA method in case of several SCA methods are available for the customer.  There are the following request types on this access path:   * Update PSU Identification   * Update PSU Authentication   * Select PSU Autorization Method      WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change.   * Transaction Authorisation     WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CommonAisAndPisServicesApi;



CommonAisAndPisServicesApi apiInstance = new CommonAisAndPisServicesApi();

String basketId = Arrays.asList("basketId_example"); // String | This identification of the corresponding signing basket object. 

String authorisationId = Arrays.asList("authorisationId_example"); // String | Resource identification of the related SCA.

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

Object body = null; // Object | 

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String PSU_ID = Arrays.asList("PSU_ID_example"); // String | Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP's documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session. 

String psUIDType = Arrays.asList("psUIDType_example"); // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility. 

String psUCorporateID = Arrays.asList("psUCorporateID_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUCorporateIDType = Arrays.asList("psUCorporateIDType_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

Object psUIPPort = null; // Object | The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available. 

String psUAccept = Arrays.asList("psUAccept_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptCharset = Arrays.asList("psUAcceptCharset_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptEncoding = Arrays.asList("psUAcceptEncoding_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUAcceptLanguage = Arrays.asList("psUAcceptLanguage_example"); // String | The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available. 

String psUUserAgent = Arrays.asList("psUUserAgent_example"); // String | The forwarded Agent header field of the HTTP request between PSU and TPP, if available. 

String psUHttpMethod = Arrays.asList("psUHttpMethod_example"); // String | HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE 

UUID psUDeviceID = Arrays.asList(new UUID()); // UUID | UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device. 

String psUGeoLocation = Arrays.asList("psUGeoLocation_example"); // String | The forwarded Geo Location of the corresponding http request between PSU and TPP if available. 

try {
    apiInstance.updateSigningBasketPsuData(basketId, authorisationId, xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling CommonAisAndPisServicesApi#updateSigningBasketPsuData");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **basketId** | [**String**](.md)| This identification of the corresponding signing basket object.  |
 **authorisationId** | [**String**](.md)| Resource identification of the related SCA. |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **body** | [**Object**](Object.md)|  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP&#x27;s documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  | [optional]
 **psUIPPort** | [**Object**](.md)| The forwarded IP Port header field consists of the corresponding HTTP request IP Port field between PSU and TPP, if available.  | [optional]
 **psUAccept** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptCharset** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptEncoding** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUAcceptLanguage** | **String**| The forwarded IP Accept header fields consist of the corresponding HTTP request Accept header fields between PSU and TPP, if available.  | [optional]
 **psUUserAgent** | **String**| The forwarded Agent header field of the HTTP request between PSU and TPP, if available.  | [optional]
 **psUHttpMethod** | **String**| HTTP method used at the PSU ? TPP interface, if available. Valid values are: * GET * POST * PUT * PATCH * DELETE  | [optional] [enum: GET, POST, PUT, PATCH, DELETE]
 **psUDeviceID** | [**UUID**](.md)| UUID (Universally Unique Identifier) for a device, which is used by the PSU, if available. UUID identifies either a device or a device dependant application installation. In case of an installation identification this ID need to be unaltered until removal from device.  | [optional]
 **psUGeoLocation** | **String**| The forwarded Geo Location of the corresponding http request between PSU and TPP if available.  | [optional]


### Return type

null (empty response body)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined



