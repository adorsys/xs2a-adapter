# ConfirmationOfFundsServicePiisApi

All URIs are relative to *https://api.testbank.com/psd2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkAvailabilityOfFunds**](ConfirmationOfFundsServicePiisApi.md#checkAvailabilityOfFunds) | **POST** /v1/funds-confirmations | Confirmation of funds request

<a name="checkAvailabilityOfFunds"></a>
# **checkAvailabilityOfFunds**
> InlineResponse2003 checkAvailabilityOfFunds(body, xRequestID, consentID, digest, signature, tpPSignatureCertificate)

Confirmation of funds request

Creates a confirmation of funds request at the ASPSP. Checks whether a specific amount is available at point of time of the request on an account linked to a given tuple card issuer(TPP)/card number, or addressed by IBAN and TPP respectively. If the related extended services are used a conditional Consent-ID is contained in the header. This field is contained but commented out in this specification.

### Example
```java
// Import classes:
//import de.adorsys.psd2.client.ApiClient;
//import de.adorsys.psd2.client.ApiException;
//import de.adorsys.psd2.client.Configuration;
//import de.adorsys.psd2.client.auth.*;
//import de.adorsys.psd2.client.api.ConfirmationOfFundsServicePiisApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();


ConfirmationOfFundsServicePiisApi apiInstance = new ConfirmationOfFundsServicePiisApi();
ConfirmationOfFunds body = new ConfirmationOfFunds(); // ConfirmationOfFunds | Request body for a confirmation of funds request.

UUID xRequestID = new UUID(); // UUID | ID of the request, unique to the call, as determined by the initiating party.
String consentID = "consentID_example"; // String | This data element may be contained, if the payment initiation transaction is part of a session, i.e. combined AIS/PIS service. This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation. 
String digest = "digest_example"; // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.
String signature = "signature_example"; // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 
byte[] tpPSignatureCertificate = B; // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 
try {
    InlineResponse2003 result = apiInstance.checkAvailabilityOfFunds(body, xRequestID, consentID, digest, signature, tpPSignatureCertificate);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfirmationOfFundsServicePiisApi#checkAvailabilityOfFunds");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfirmationOfFunds**](ConfirmationOfFunds.md)| Request body for a confirmation of funds request.
 |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **consentID** | [**String**](.md)| This data element may be contained, if the payment initiation transaction is part of a session, i.e. combined AIS/PIS service. This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation.  | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]

### Return type

[**InlineResponse2003**](InlineResponse2003.md)

### Authorization

[BearerAuthOAuth](../README.md#BearerAuthOAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

