# PaymentInitiationServicePisApi

All URIs are relative to *https://api.testbank.com/psd2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**cancelPayment**](PaymentInitiationServicePisApi.md#cancelPayment) | **DELETE** /v1/{payment-service}/{paymentId} | Payment Cancellation Request
[**getPaymentCancellationScaStatus**](PaymentInitiationServicePisApi.md#getPaymentCancellationScaStatus) | **GET** /v1/{payment-service}/{paymentId}/cancellation-authorisations/{cancellationId} | Read the SCA status of the payment cancellation&#x27;s authorisation.
[**getPaymentInformation**](PaymentInitiationServicePisApi.md#getPaymentInformation) | **GET** /v1/{payment-service}/{paymentId} | Get Payment Information
[**getPaymentInitiationAuthorisation**](PaymentInitiationServicePisApi.md#getPaymentInitiationAuthorisation) | **GET** /v1/{payment-service}/{paymentId}/authorisations | Get Payment Initiation Authorisation Sub-Resources Request
[**getPaymentInitiationAuthorisation_0**](PaymentInitiationServicePisApi.md#getPaymentInitiationAuthorisation_0) | **GET** /v1/consents/{consentId}/authorisations | Get Consent Authorisation Sub-Resources Request
[**getPaymentInitiationCancellationAuthorisationInformation**](PaymentInitiationServicePisApi.md#getPaymentInitiationCancellationAuthorisationInformation) | **GET** /v1/{payment-service}/{paymentId}/cancellation-authorisations | Will deliver an array of resource identifications to all generated cancellation authorisation sub-resources.
[**getPaymentInitiationScaStatus**](PaymentInitiationServicePisApi.md#getPaymentInitiationScaStatus) | **GET** /v1/{payment-service}/{paymentId}/authorisations/{authorisationId} | Read the SCA Status of the payment authorisation
[**getPaymentInitiationStatus**](PaymentInitiationServicePisApi.md#getPaymentInitiationStatus) | **GET** /v1/{payment-service}/{paymentId}/status | Payment initiation status request
[**initiatePayment**](PaymentInitiationServicePisApi.md#initiatePayment) | **POST** /v1/{payment-service}/{payment-product} | Payment initiation request
[**startPaymentAuthorisation**](PaymentInitiationServicePisApi.md#startPaymentAuthorisation) | **POST** /v1/{payment-service}/{paymentId}/authorisations | Start the authorisation process for a payment initiation
[**startPaymentInitiationCancellationAuthorisation**](PaymentInitiationServicePisApi.md#startPaymentInitiationCancellationAuthorisation) | **POST** /v1/{payment-service}/{paymentId}/cancellation-authorisations | Start the authorisation process for the cancellation of the addressed payment
[**updatePaymentCancellationPsuData**](PaymentInitiationServicePisApi.md#updatePaymentCancellationPsuData) | **PUT** /v1/{payment-service}/{paymentId}/cancellation-authorisations/{cancellationId} | Update PSU Data for payment initiation cancellation
[**updatePaymentPsuData**](PaymentInitiationServicePisApi.md#updatePaymentPsuData) | **PUT** /v1/{payment-service}/{paymentId}/authorisations/{authorisationId} | Update PSU data for payment initiation




<a name="cancelPayment"></a>
# **cancelPayment**
> cancelPayment(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Payment Cancellation Request

This method initiates the cancellation of a payment.  Depending on the payment-service, the payment-product and the ASPSP&#x27;s implementation,  this TPP call might be sufficient to cancel a payment.  If an authorisation of the payment cancellation is mandated by the ASPSP,  a corresponding hyperlink will be contained in the response message.  Cancels the addressed payment with resource identification paymentId if applicable to the payment-service, payment-product and received in product related timelines (e.g. before end of business day for scheduled payments of the last business day before the scheduled execution day).   The response to this DELETE command will tell the TPP whether the    * access method was rejected   * access method was successful, or   * access method is generally applicable, but further authorisation processes are needed. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    apiInstance.cancelPayment(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling PaymentInitiationServicePisApi#cancelPayment");
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


<a name="getPaymentCancellationScaStatus"></a>
# **getPaymentCancellationScaStatus**
> getPaymentCancellationScaStatus(paymentService, paymentId, cancellationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read the SCA status of the payment cancellation&#x27;s authorisation.

This method returns the SCA status of a payment initiation&#x27;s authorisation sub-resource. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    System.err.println("Exception when calling PaymentInitiationServicePisApi#getPaymentCancellationScaStatus");
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


<a name="getPaymentInformation"></a>
# **getPaymentInformation**
> getPaymentInformation(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Get Payment Information

Returns the content of a payment object

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    apiInstance.getPaymentInformation(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling PaymentInitiationServicePisApi#getPaymentInformation");
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


<a name="getPaymentInitiationAuthorisation"></a>
# **getPaymentInitiationAuthorisation**
> getPaymentInitiationAuthorisation(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Get Payment Initiation Authorisation Sub-Resources Request

Read a list of all authorisation subresources IDs which have been created.  This function returns an array of hyperlinks to all generated authorisation sub-resources. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    System.err.println("Exception when calling PaymentInitiationServicePisApi#getPaymentInitiationAuthorisation");
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

Get Consent Authorisation Sub-Resources Request

Return a list of all authorisation subresources IDs which have been created.  This function returns an array of hyperlinks to all generated authorisation sub-resources. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    System.err.println("Exception when calling PaymentInitiationServicePisApi#getPaymentInitiationAuthorisation_0");
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


<a name="getPaymentInitiationCancellationAuthorisationInformation"></a>
# **getPaymentInitiationCancellationAuthorisationInformation**
> getPaymentInitiationCancellationAuthorisationInformation(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Will deliver an array of resource identifications to all generated cancellation authorisation sub-resources.

Retrieve a list of all created cancellation authorisation sub-resources. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    apiInstance.getPaymentInitiationCancellationAuthorisationInformation(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling PaymentInitiationServicePisApi#getPaymentInitiationCancellationAuthorisationInformation");
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
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    System.err.println("Exception when calling PaymentInitiationServicePisApi#getPaymentInitiationScaStatus");
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


<a name="getPaymentInitiationStatus"></a>
# **getPaymentInitiationStatus**
> getPaymentInitiationStatus(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Payment initiation status request

Check the transaction status of a payment initiation.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    apiInstance.getPaymentInitiationStatus(paymentService, paymentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling PaymentInitiationServicePisApi#getPaymentInitiationStatus");
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


<a name="initiatePayment"></a>
# **initiatePayment**
> initiatePayment(body, paymentService, paymentProduct, xRequestID, psUIPAddress, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, consentID, tpPRedirectPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPExplicitAuthorisationPreferred, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Payment initiation request

This method is used to initiate a payment at the ASPSP.  ## Variants of Payment Initiation Requests  This method to initiate a payment initiation at the ASPSP can be sent with either a JSON body or an pain.001 body depending on the payment product in the path.  There are the following **payment products**:    - Payment products with payment information in *JSON* format:     - ***sepa-credit-transfers***     - ***instant-sepa-credit-transfers***     - ***target-2-payments***     - ***cross-border-credit-transfers***   - Payment products with payment information in *pain.001* XML format:     - ***pain.001-sepa-credit-transfers***     - ***pain.001-instant-sepa-credit-transfers***     - ***pain.001-target-2-payments***     - ***pain.001-cross-border-credit-transfers***  Furthermore the request body depends on the **payment-service**   * ***payments***: A single payment initiation request.   * ***bulk-payments***: A collection of several payment iniatiation requests.        In case of a *pain.001* message there are more than one payments contained in the *pain.001 message.          In case of a *JSON* there are several JSON payment blocks contained in a joining list.   * ***periodic-payments***:      Create a standing order initiation resource for recurrent i.e. periodic payments addressable under {paymentId}       with all data relevant for the corresponding payment product and the execution of the standing order contained in a JSON body.   This is the first step in the API to initiate the related recurring/periodic payment.    ## Single and mulitilevel SCA Processes  The Payment Initiation Requests are independent from the need of one ore multilevel  SCA processing, i.e. independent from the number of authorisations needed for the execution of payments.   But the response messages are specific to either one SCA processing or multilevel SCA processing.   For payment initiation with multilevel SCA, this specification requires an explicit start of the authorisation,  i.e. links directly associated with SCA processing like &#x27;scaRedirect&#x27; or &#x27;scaOAuth&#x27; cannot be contained in the  response message of a Payment Initation Request for a payment, where multiple authorisations are needed.  Also if any data is needed for the next action, like selecting an SCA method is not supported in the response,  since all starts of the multiple authorisations are fully equal.  In these cases, first an authorisation sub-resource has to be generated following the &#x27;startAuthorisation&#x27; link. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

Object body = null; // Object | JSON request body for a payment inition request message 

There are the following payment-products supported:
  * "sepa-credit-transfers" with JSON-Body
  * "instant-sepa-credit-transfers" with JSON-Body
  * "target-2-payments" with JSON-Body
  * "cross-border-credit-transfers" with JSON-Body
  * "pain.001-sepa-credit-transfers" with XML pain.001.001.03 body for SCT scheme
  * "pain.001-instant-sepa-credit-transfers" with XML pain.001.001.03 body for SCT INST scheme
  * "pain.001-target-2-payments" with pain.001 body. 
    Only country specific schemes are currently available
  * "pain.001-cross-border-credit-transfers" with pain.001 body. 
    Only country specific schemes are currently available
  
There are the following payment-services supported:
  * "payments"
  * "periodic-payments"
  * "bulk-paments"

All optional, conditional and predefined but not yet used fields are defined.


String paymentService = Arrays.asList("paymentService_example"); // String | Payment service:  Possible values are: * payments * bulk-payments * periodic-payments 

String paymentProduct = Arrays.asList("paymentProduct_example"); // String | The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specificic scheme variants. 

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String psUIPAddress = Arrays.asList("psUIPAddress_example"); // String | The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP. 

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String PSU_ID = Arrays.asList("PSU_ID_example"); // String | Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP's documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session. 

String psUIDType = Arrays.asList("psUIDType_example"); // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility. 

String psUCorporateID = Arrays.asList("psUCorporateID_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUCorporateIDType = Arrays.asList("psUCorporateIDType_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String consentID = Arrays.asList("consentID_example"); // String | This data element may be contained, if the payment initiation transaction is part of a session, i.e. combined AIS/PIS service. This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation. 

Boolean tpPRedirectPreferred = Arrays.asList(true); // Boolean | If it equals \"true\", the TPP prefers a redirect over an embedded SCA approach. If it equals \"false\", the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU. 

String tpPRedirectURI = Arrays.asList("tpPRedirectURI_example"); // String | URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach (including OAuth2 SCA approach), specifically  when TPP-Redirect-Preferred equals \"true\". It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification. 

String tpPNokRedirectURI = Arrays.asList("tpPNokRedirectURI_example"); // String | If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP. 

Boolean tpPExplicitAuthorisationPreferred = Arrays.asList(true); // Boolean | If it equals \"true\", the TPP prefers to start the authorisation process separately,  e.g. because of the usage of a signing basket.  This preference might be ignored by the ASPSP, if a signing basket is not supported as functionality.  If it equals \"false\" or if the parameter is not used, there is no preference of the TPP.  This especially indicates that the TPP assumes a direct authorisation of the transaction in the next step,  without using a signing basket. 

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
    apiInstance.initiatePayment(body, paymentService, paymentProduct, xRequestID, psUIPAddress, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, consentID, tpPRedirectPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPExplicitAuthorisationPreferred, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling PaymentInitiationServicePisApi#initiatePayment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)| JSON request body for a payment inition request message 

There are the following payment-products supported:
  * &quot;sepa-credit-transfers&quot; with JSON-Body
  * &quot;instant-sepa-credit-transfers&quot; with JSON-Body
  * &quot;target-2-payments&quot; with JSON-Body
  * &quot;cross-border-credit-transfers&quot; with JSON-Body
  * &quot;pain.001-sepa-credit-transfers&quot; with XML pain.001.001.03 body for SCT scheme
  * &quot;pain.001-instant-sepa-credit-transfers&quot; with XML pain.001.001.03 body for SCT INST scheme
  * &quot;pain.001-target-2-payments&quot; with pain.001 body. 
    Only country specific schemes are currently available
  * &quot;pain.001-cross-border-credit-transfers&quot; with pain.001 body. 
    Only country specific schemes are currently available
  
There are the following payment-services supported:
  * &quot;payments&quot;
  * &quot;periodic-payments&quot;
  * &quot;bulk-paments&quot;

All optional, conditional and predefined but not yet used fields are defined.
 |
 **paymentService** | **String**| Payment service:  Possible values are: * payments * bulk-payments * periodic-payments  | [enum: payments, bulk-payments, periodic-payments]
 **paymentProduct** | **String**| The addressed payment product endpoint, e.g. for SEPA Credit Transfers (SCT). The ASPSP will publish which of the payment products/endpoints will be supported.  The following payment products are supported:   - sepa-credit-transfers   - instant-sepa-credit-transfers   - target-2-payments   - cross-border-credit-transfers   - pain.001-sepa-credit-transfers   - pain.001-instant-sepa-credit-transfers   - pain.001-target-2-payments   - pain.001-cross-border-credit-transfers  **Remark:** For all SEPA Credit Transfer based endpoints which accept XML encoding,  the XML pain.001 schemes provided by EPC are supported by the ASPSP as a minimum for the body content.  Further XML schemes might be supported by some communities.  **Remark:** For cross-border and TARGET-2 payments only community wide pain.001 schemes do exist.  There are plenty of country specificic scheme variants.  | [enum: sepa-credit-transfers, instant-sepa-credit-transfers, target-2-payments, cross-border-credit-transfers, pain.001-sepa-credit-transfers, pain.001-instant-sepa-credit-transfers, pain.001-target-2-payments, pain.001-cross-border-credit-transfers]
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **psUIPAddress** | **String**| The forwarded IP Address header field consists of the corresponding http request IP Address field between PSU and TPP.  |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP&#x27;s documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **consentID** | [**String**](.md)| This data element may be contained, if the payment initiation transaction is part of a session, i.e. combined AIS/PIS service. This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation.  | [optional]
 **tpPRedirectPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers a redirect over an embedded SCA approach. If it equals \&quot;false\&quot;, the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU.  | [optional]
 **tpPRedirectURI** | **String**| URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach (including OAuth2 SCA approach), specifically  when TPP-Redirect-Preferred equals \&quot;true\&quot;. It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification.  | [optional]
 **tpPNokRedirectURI** | **String**| If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP.  | [optional]
 **tpPExplicitAuthorisationPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers to start the authorisation process separately,  e.g. because of the usage of a signing basket.  This preference might be ignored by the ASPSP, if a signing basket is not supported as functionality.  If it equals \&quot;false\&quot; or if the parameter is not used, there is no preference of the TPP.  This especially indicates that the TPP assumes a direct authorisation of the transaction in the next step,  without using a signing basket.  | [optional]
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
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    System.err.println("Exception when calling PaymentInitiationServicePisApi#startPaymentAuthorisation");
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
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    System.err.println("Exception when calling PaymentInitiationServicePisApi#startPaymentInitiationCancellationAuthorisation");
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


<a name="updatePaymentCancellationPsuData"></a>
# **updatePaymentCancellationPsuData**
> updatePaymentCancellationPsuData(paymentService, paymentId, cancellationId, xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Update PSU Data for payment initiation cancellation

This method updates PSU data on the cancellation authorisation resource if needed.  It may authorise a cancellation of the payment within the Embedded SCA Approach where needed.  Independently from the SCA Approach it supports e.g. the selection of  the authentication method and a non-SCA PSU authentication.  This methods updates PSU data on the cancellation authorisation resource if needed.   There are several possible Update PSU Data requests in the context of a cancellation authorisation within the payment initiation services needed,  which depends on the SCA approach:  * Redirect SCA Approach:   A specific Update PSU Data Request is applicable for      * the selection of authentication methods, before choosing the actual SCA approach. * Decoupled SCA Approach:   A specific Update PSU Data Request is only applicable for   * adding the PSU Identification, if not provided yet in the Payment Initiation Request or the Account Information Consent Request, or if no OAuth2 access token is used, or   * the selection of authentication methods. * Embedded SCA Approach:    The Update PSU Data Request might be used    * to add credentials as a first factor authentication data of the PSU and   * to select the authentication method and   * transaction authorisation.  The SCA Approach might depend on the chosen SCA method.  For that reason, the following possible Update PSU Data request can apply to all SCA approaches:  * Select an SCA method in case of several SCA methods are available for the customer.  There are the following request types on this access path:   * Update PSU Identification   * Update PSU Authentication   * Select PSU Autorization Method      WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change.   * Transaction Authorisation     WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    System.err.println("Exception when calling PaymentInitiationServicePisApi#updatePaymentCancellationPsuData");
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
//import io.swagger.client.api.PaymentInitiationServicePisApi;



PaymentInitiationServicePisApi apiInstance = new PaymentInitiationServicePisApi();

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
    System.err.println("Exception when calling PaymentInitiationServicePisApi#updatePaymentPsuData");
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



