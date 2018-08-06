# ConfirmationOfFundsServiceApi

All URIs are relative to *https://api.testbank.com/psd2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkAvailabilityOfFunds**](ConfirmationOfFundsServiceApi.md#checkAvailabilityOfFunds) | **POST** /v1/funds-confirmations | Confirmation of Funds Request




<a name="checkAvailabilityOfFunds"></a>
# **checkAvailabilityOfFunds**
> checkAvailabilityOfFunds(body, xRequestID, digest, signature, tpPSignatureCertificate)

Confirmation of Funds Request

Creates a confirmation of funds request at the ASPSP. Checks whether a specific amount is available at point of time of the request on an account linked to a given tuple card issuer(TPP)/card number, or addressed by IBAN and TPP respectively

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ConfirmationOfFundsServiceApi;



ConfirmationOfFundsServiceApi apiInstance = new ConfirmationOfFundsServiceApi();

ConfirmationOfFunds body = new ConfirmationOfFunds(); // ConfirmationOfFunds | Request body for a confirmation of funds request.


UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

try {
    apiInstance.checkAvailabilityOfFunds(body, xRequestID, digest, signature, tpPSignatureCertificate);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfirmationOfFundsServiceApi#checkAvailabilityOfFunds");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfirmationOfFunds**](ConfirmationOfFunds.md)| Request body for a confirmation of funds request.
 |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]


### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined



