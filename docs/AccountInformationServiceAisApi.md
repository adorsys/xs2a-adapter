# AccountInformationServiceAisApi

All URIs are relative to *https://api.testbank.com/psd2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createConsent**](AccountInformationServiceAisApi.md#createConsent) | **POST** /v1/consents | Create consent
[**deleteConsent**](AccountInformationServiceAisApi.md#deleteConsent) | **DELETE** /v1/consents/{consentId} | Delete Consent
[**getAccountList**](AccountInformationServiceAisApi.md#getAccountList) | **GET** /v1/accounts | Read Account List
[**getBalances**](AccountInformationServiceAisApi.md#getBalances) | **GET** /v1/accounts/{account-id}/balances | Read Balance
[**getConsentInformation**](AccountInformationServiceAisApi.md#getConsentInformation) | **GET** /v1/consents/{consentId} | Get Consent Request
[**getConsentScaStatus**](AccountInformationServiceAisApi.md#getConsentScaStatus) | **GET** /v1/consents/{consentId}/authorisations/{authorisationId} | Read the SCA status of the consent authorisation.
[**getConsentStatus**](AccountInformationServiceAisApi.md#getConsentStatus) | **GET** /v1/consents/{consentId}/status | Consent status request
[**getTransactionDetails**](AccountInformationServiceAisApi.md#getTransactionDetails) | **GET** /v1/accounts/{account-id}/transactions/{resourceId} | Read Transaction Details
[**getTransactionList**](AccountInformationServiceAisApi.md#getTransactionList) | **GET** /v1/accounts/{account-id}/transactions/ | Read Transaction List
[**readAccountDetails**](AccountInformationServiceAisApi.md#readAccountDetails) | **GET** /v1/accounts/{account-id} | Read Account Details
[**startConsentAuthorisation**](AccountInformationServiceAisApi.md#startConsentAuthorisation) | **POST** /v1/consents/{consentId}/authorisations | Start the authorisation process for a consent
[**updateConsentsPsuData**](AccountInformationServiceAisApi.md#updateConsentsPsuData) | **PUT** /v1/consents/{consentId}/authorisations/{authorisationId} | Update PSU Data for consents




<a name="createConsent"></a>
# **createConsent**
> createConsent(xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, tpPRedirectPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPExplicitAuthorisationPreferred, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Create consent

This method create a consent resource, defining access rights to dedicated accounts of  a given PSU-ID. These accounts are addressed explicitly in the method as  parameters as a core function.  **Side Effects** When this Consent Request is a request where the \&quot;recurringIndicator\&quot; equals \&quot;true\&quot;,  and if it exists already a former consent for recurring access on account information  for the addressed PSU, then the former consent automatically expires as soon as the new  consent request is authorised by the PSU.  Optional Extension: As an option, an ASPSP might optionally accept a specific access right on the access on all psd2 related services for all available accounts.   As another option an ASPSP might optionally also accept a command, where only access rights are inserted without mentioning the addressed account.  The relation to accounts is then handled afterwards between PSU and ASPSP.  This option is supported only within the Decoupled, OAuth2 or Re-direct SCA Approach.  As a last option, an ASPSP might in addition accept a command with access rights   * to see the list of available payment accounts or   * to see the list of available payment accounts with balances. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

Consents body = new Consents(); // Consents | Requestbody for a consents request


String digest = Arrays.asList("digest_example"); // String | Is contained if and only if the \"Signature\" element is contained in the header of the request.

String signature = Arrays.asList("signature_example"); // String | A signature of the request by the TPP on application level. This might be mandated by ASPSP. 

byte[] tpPSignatureCertificate = Arrays.asList(B); // byte[] | The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained. 

String PSU_ID = Arrays.asList("PSU_ID_example"); // String | Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP's documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session. 

String psUIDType = Arrays.asList("psUIDType_example"); // String | Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility. 

String psUCorporateID = Arrays.asList("psUCorporateID_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

String psUCorporateIDType = Arrays.asList("psUCorporateIDType_example"); // String | Might be mandated in the ASPSP's documentation. Only used in a corporate context. 

Boolean tpPRedirectPreferred = Arrays.asList(true); // Boolean | If it equals \"true\", the TPP prefers a redirect over an embedded SCA approach. If it equals \"false\", the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU. 

String tpPRedirectURI = Arrays.asList("tpPRedirectURI_example"); // String | URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach (including OAuth2 SCA approach), specifically  when TPP-Redirect-Preferred equals \"true\". It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification. 

String tpPNokRedirectURI = Arrays.asList("tpPNokRedirectURI_example"); // String | If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP. 

Boolean tpPExplicitAuthorisationPreferred = Arrays.asList(true); // Boolean | If it equals \"true\", the TPP prefers to start the authorisation process separately,  e.g. because of the usage of a signing basket.  This preference might be ignored by the ASPSP, if a signing basket is not supported as functionality.  If it equals \"false\" or if the parameter is not used, there is no preference of the TPP.  This especially indicates that the TPP assumes a direct authorisation of the transaction in the next step,  without using a signing basket. 

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
    apiInstance.createConsent(xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, tpPRedirectPreferred, tpPRedirectURI, tpPNokRedirectURI, tpPExplicitAuthorisationPreferred, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling AccountInformationServiceAisApi#createConsent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **body** | [**Consents**](Consents.md)| Requestbody for a consents request
 | [optional]
 **digest** | **String**| Is contained if and only if the \&quot;Signature\&quot; element is contained in the header of the request. | [optional]
 **signature** | **String**| A signature of the request by the TPP on application level. This might be mandated by ASPSP.  | [optional]
 **tpPSignatureCertificate** | **byte[]**| The certificate used for signing the request, in base64 encoding.  Must be contained if a signature is contained.  | [optional]
 **PSU_ID** | **String**| Client ID of the PSU in the ASPSP client interface. Might be mandated in the ASPSP&#x27;s documentation. Is not contained if an OAuth2 based authentication was performed in a pre-step or an OAuth2 based SCA was performed in an preceeding AIS service in the same session.  | [optional]
 **psUIDType** | **String**| Type of the PSU-ID, needed in scenarios where PSUs have several PSU-IDs as access possibility.  | [optional]
 **psUCorporateID** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **psUCorporateIDType** | **String**| Might be mandated in the ASPSP&#x27;s documentation. Only used in a corporate context.  | [optional]
 **tpPRedirectPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers a redirect over an embedded SCA approach. If it equals \&quot;false\&quot;, the TPP prefers not to be redirected for SCA. The ASPSP will then choose between the Embedded or the Decoupled SCA approach, depending on the choice of the SCA procedure by the TPP/PSU. If the parameter is not used, the ASPSP will choose the SCA approach to be applied depending on the SCA method chosen by the TPP/PSU.  | [optional]
 **tpPRedirectURI** | **String**| URI of the TPP, where the transaction flow shall be redirected to after a Redirect.  Mandated for the Redirect SCA Approach (including OAuth2 SCA approach), specifically  when TPP-Redirect-Preferred equals \&quot;true\&quot;. It is recommended to always use this header field.  **Remark for Future:**  This field might be changed to mandatory in the next version of the specification.  | [optional]
 **tpPNokRedirectURI** | **String**| If this URI is contained, the TPP is asking to redirect the transaction flow to this address instead of the TPP-Redirect-URI in case of a negative result of the redirect SCA method. This might be ignored by the ASPSP.  | [optional]
 **tpPExplicitAuthorisationPreferred** | **Boolean**| If it equals \&quot;true\&quot;, the TPP prefers to start the authorisation process separately,  e.g. because of the usage of a signing basket.  This preference might be ignored by the ASPSP, if a signing basket is not supported as functionality.  If it equals \&quot;false\&quot; or if the parameter is not used, there is no preference of the TPP.  This especially indicates that the TPP assumes a direct authorisation of the transaction in the next step,  without using a signing basket.  | [optional]
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


<a name="deleteConsent"></a>
# **deleteConsent**
> deleteConsent(consentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Delete Consent

The TPP can delete an account information consent object if needed.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

String consentId = Arrays.asList("consentId_example"); // String | ID of the corresponding consent object as returned by an Account Information Consent Request. 

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
    apiInstance.deleteConsent(consentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling AccountInformationServiceAisApi#deleteConsent");
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


<a name="getAccountList"></a>
# **getAccountList**
> getAccountList(xRequestID, consentID, withBalance, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read Account List

Read the identifiers of the available payment account together with  booking balance information, depending on the consent granted.  It is assumed that a consent of the PSU to this access is already given and stored on the ASPSP system.  The addressed list of accounts depends then on the PSU ID and the stored consent addressed by consentId,  respectively the OAuth2 access token.   Returns all identifiers of the accounts, to which an account access has been granted to through  the /consents endpoint by the PSU.  In addition, relevant information about the accounts and hyperlinks to corresponding account  information resources are provided if a related consent has been already granted.  Remark: Note that the /consents endpoint optionally offers to grant an access on all available  payment accounts of a PSU.  In this case, this endpoint will deliver the information about all available payment accounts  of the PSU at this ASPSP. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String consentID = Arrays.asList("consentID_example"); // String | This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation. 

Boolean withBalance = Arrays.asList(true); // Boolean | If contained, this function reads the list of accessible payment accounts including the booking balance,  if granted by the PSU in the related consent and available by the ASPSP.  This parameter might be ignored by the ASPSP.  

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
    apiInstance.getAccountList(xRequestID, consentID, withBalance, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling AccountInformationServiceAisApi#getAccountList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **consentID** | [**String**](.md)| This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation.  |
 **withBalance** | **Boolean**| If contained, this function reads the list of accessible payment accounts including the booking balance,  if granted by the PSU in the related consent and available by the ASPSP.  This parameter might be ignored by the ASPSP.   | [optional]
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


<a name="getBalances"></a>
# **getBalances**
> getBalances(accountId, xRequestID, consentID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read Balance

Reads account data from a given account addressed by \&quot;account-id\&quot;.   **Remark:** This account-id can be a tokenised identification due to data protection reason since the path  information might be logged on intermediary servers within the ASPSP sphere.  This account-id then can be retrieved by the \&quot;GET Account List\&quot; call.  The account-id is constant at least throughout the lifecycle of a given consent. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

String accountId = Arrays.asList("accountId_example"); // String | This identification is denoting the addressed account.  The account-id is retrieved by using a \"Read Account List\" call.  The account-id is the \"id\" attribute of the account structure.  Its value is constant at least throughout the lifecycle of a given consent. 

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String consentID = Arrays.asList("consentID_example"); // String | This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation. 

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
    apiInstance.getBalances(accountId, xRequestID, consentID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling AccountInformationServiceAisApi#getBalances");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **accountId** | [**String**](.md)| This identification is denoting the addressed account.  The account-id is retrieved by using a \&quot;Read Account List\&quot; call.  The account-id is the \&quot;id\&quot; attribute of the account structure.  Its value is constant at least throughout the lifecycle of a given consent.  |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **consentID** | [**String**](.md)| This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation.  |
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


<a name="getConsentInformation"></a>
# **getConsentInformation**
> getConsentInformation(consentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Get Consent Request

Returns the content of an account information consent object.  This is returning the data for the TPP especially in cases,  where the consent was directly managed between ASPSP and PSU e.g. in a re-direct SCA Approach. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

String consentId = Arrays.asList("consentId_example"); // String | ID of the corresponding consent object as returned by an Account Information Consent Request. 

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
    apiInstance.getConsentInformation(consentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling AccountInformationServiceAisApi#getConsentInformation");
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


<a name="getConsentScaStatus"></a>
# **getConsentScaStatus**
> getConsentScaStatus(consentId, authorisationId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read the SCA status of the consent authorisation.

This method returns the SCA status of a consent initiation&#x27;s authorisation sub-resource. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

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
    System.err.println("Exception when calling AccountInformationServiceAisApi#getConsentScaStatus");
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


<a name="getConsentStatus"></a>
# **getConsentStatus**
> getConsentStatus(consentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Consent status request

Read the status of an account information consent resource.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

String consentId = Arrays.asList("consentId_example"); // String | ID of the corresponding consent object as returned by an Account Information Consent Request. 

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
    apiInstance.getConsentStatus(consentId, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling AccountInformationServiceAisApi#getConsentStatus");
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


<a name="getTransactionDetails"></a>
# **getTransactionDetails**
> getTransactionDetails(accountId, resourceId, xRequestID, consentID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read Transaction Details

Reads transaction details from a given transaction addressed by \&quot;resourceId\&quot; on a given account addressed by \&quot;account-id\&quot;.  This call is only available on transactions as reported in a JSON format.  **Remark:** Please note that the PATH might be already given in detail by the corresponding entry of the response of the  \&quot;Read Transaction List\&quot; call within the _links subfield. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

String accountId = Arrays.asList("accountId_example"); // String | This identification is denoting the addressed account.  The account-id is retrieved by using a \"Read Account List\" call.  The account-id is the \"id\" attribute of the account structure.  Its value is constant at least throughout the lifecycle of a given consent. 

String resourceId = Arrays.asList("resourceId_example"); // String | This identification is given by the attribute resourceId of the corresponding entry of a transaction list. 

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String consentID = Arrays.asList("consentID_example"); // String | This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation. 

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
    apiInstance.getTransactionDetails(accountId, resourceId, xRequestID, consentID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling AccountInformationServiceAisApi#getTransactionDetails");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **accountId** | [**String**](.md)| This identification is denoting the addressed account.  The account-id is retrieved by using a \&quot;Read Account List\&quot; call.  The account-id is the \&quot;id\&quot; attribute of the account structure.  Its value is constant at least throughout the lifecycle of a given consent.  |
 **resourceId** | [**String**](.md)| This identification is given by the attribute resourceId of the corresponding entry of a transaction list.  |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **consentID** | [**String**](.md)| This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation.  |
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


<a name="getTransactionList"></a>
# **getTransactionList**
> getTransactionList(accountId, bookingStatus, xRequestID, consentID, dateFrom, dateTo, entryReferenceFrom, deltaList, withBalance, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read Transaction List

Read transaction reports or transaction lists of a given account ddressed by \&quot;account-id\&quot;, depending on the steering parameter  \&quot;bookingStatus\&quot; together with balances.  For a given account, additional parameters are e.g. the attributes \&quot;dateFrom\&quot; and \&quot;dateTo\&quot;.  The ASPSP might add balance information, if transaction lists without balances are not supported. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

String accountId = Arrays.asList("accountId_example"); // String | This identification is denoting the addressed account.  The account-id is retrieved by using a \"Read Account List\" call.  The account-id is the \"id\" attribute of the account structure.  Its value is constant at least throughout the lifecycle of a given consent. 

String bookingStatus = Arrays.asList("bookingStatus_example"); // String | Permitted codes are    * \"booked\",   * \"pending\" and    * \"both\" \"booked\" shall be supported by the ASPSP. To support the \"pending\" and \"both\" feature is optional for the ASPSP,  Error code if not supported in the online banking frontend 

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String consentID = Arrays.asList("consentID_example"); // String | This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation. 

LocalDate dateFrom = Arrays.asList(new LocalDate()); // LocalDate | Conditional: Starting date (inclusive the date dateFrom) of the transaction list, mandated if no delta access is required. 

LocalDate dateTo = Arrays.asList(new LocalDate()); // LocalDate | End date (inclusive the data dateTo) of the transaction list, default is now if not given. 

String entryReferenceFrom = Arrays.asList("entryReferenceFrom_example"); // String | This data attribute is indicating that the AISP is in favour to get all transactions after  the transaction with identification entryReferenceFrom alternatively to the above defined period.  This is a implementation of a delta access.  If this data element is contained, the entries \"dateFrom\" and \"dateTo\" might be ignored by the ASPSP  if a delta report is supported.  Optional if supported by API provider. 

Boolean deltaList = Arrays.asList(true); // Boolean | This data attribute is indicating that the AISP is in favour to get all transactions after the last report access for this PSU on the addressed account. This is another implementation of a delta access-report. This delta indicator might be rejected by the ASPSP if this function is not supported. Optional if supported by API provider

Boolean withBalance = Arrays.asList(true); // Boolean | If contained, this function reads the list of accessible payment accounts including the booking balance,  if granted by the PSU in the related consent and available by the ASPSP.  This parameter might be ignored by the ASPSP.  

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
    apiInstance.getTransactionList(accountId, bookingStatus, xRequestID, consentID, dateFrom, dateTo, entryReferenceFrom, deltaList, withBalance, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling AccountInformationServiceAisApi#getTransactionList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **accountId** | [**String**](.md)| This identification is denoting the addressed account.  The account-id is retrieved by using a \&quot;Read Account List\&quot; call.  The account-id is the \&quot;id\&quot; attribute of the account structure.  Its value is constant at least throughout the lifecycle of a given consent.  |
 **bookingStatus** | **String**| Permitted codes are    * \&quot;booked\&quot;,   * \&quot;pending\&quot; and    * \&quot;both\&quot; \&quot;booked\&quot; shall be supported by the ASPSP. To support the \&quot;pending\&quot; and \&quot;both\&quot; feature is optional for the ASPSP,  Error code if not supported in the online banking frontend  | [enum: booked, pending, both]
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **consentID** | [**String**](.md)| This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation.  |
 **dateFrom** | **LocalDate**| Conditional: Starting date (inclusive the date dateFrom) of the transaction list, mandated if no delta access is required.  | [optional]
 **dateTo** | **LocalDate**| End date (inclusive the data dateTo) of the transaction list, default is now if not given.  | [optional]
 **entryReferenceFrom** | **String**| This data attribute is indicating that the AISP is in favour to get all transactions after  the transaction with identification entryReferenceFrom alternatively to the above defined period.  This is a implementation of a delta access.  If this data element is contained, the entries \&quot;dateFrom\&quot; and \&quot;dateTo\&quot; might be ignored by the ASPSP  if a delta report is supported.  Optional if supported by API provider.  | [optional]
 **deltaList** | **Boolean**| This data attribute is indicating that the AISP is in favour to get all transactions after the last report access for this PSU on the addressed account. This is another implementation of a delta access-report. This delta indicator might be rejected by the ASPSP if this function is not supported. Optional if supported by API provider | [optional]
 **withBalance** | **Boolean**| If contained, this function reads the list of accessible payment accounts including the booking balance,  if granted by the PSU in the related consent and available by the ASPSP.  This parameter might be ignored by the ASPSP.   | [optional]
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


<a name="readAccountDetails"></a>
# **readAccountDetails**
> readAccountDetails(accountId, xRequestID, consentID, withBalance, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Read Account Details

Reads details about an account, with balances where required.  It is assumed that a consent of the PSU to  this access is already given and stored on the ASPSP system.  The addressed details of this account depends then on the stored consent addressed by consentId,  respectively the OAuth2 access token.  **NOTE:** The account-id can represent a multicurrency account.  In this case the currency code is set to \&quot;XXX\&quot;.  Give detailed information about the addressed account.  Give detailed information about the addressed account together with balance information 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

String accountId = Arrays.asList("accountId_example"); // String | This identification is denoting the addressed account.  The account-id is retrieved by using a \"Read Account List\" call.  The account-id is the \"id\" attribute of the account structure.  Its value is constant at least throughout the lifecycle of a given consent. 

UUID xRequestID = Arrays.asList(new UUID()); // UUID | ID of the request, unique to the call, as determined by the initiating party.

String consentID = Arrays.asList("consentID_example"); // String | This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation. 

Boolean withBalance = Arrays.asList(true); // Boolean | If contained, this function reads the list of accessible payment accounts including the booking balance,  if granted by the PSU in the related consent and available by the ASPSP.  This parameter might be ignored by the ASPSP.  

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
    apiInstance.readAccountDetails(accountId, xRequestID, consentID, withBalance, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);
} catch (ApiException e) {
    System.err.println("Exception when calling AccountInformationServiceAisApi#readAccountDetails");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **accountId** | [**String**](.md)| This identification is denoting the addressed account.  The account-id is retrieved by using a \&quot;Read Account List\&quot; call.  The account-id is the \&quot;id\&quot; attribute of the account structure.  Its value is constant at least throughout the lifecycle of a given consent.  |
 **xRequestID** | [**UUID**](.md)| ID of the request, unique to the call, as determined by the initiating party. |
 **consentID** | [**String**](.md)| This then contains the consentId of the related AIS consent, which was performed prior to this payment initiation.  |
 **withBalance** | **Boolean**| If contained, this function reads the list of accessible payment accounts including the booking balance,  if granted by the PSU in the related consent and available by the ASPSP.  This parameter might be ignored by the ASPSP.   | [optional]
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
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

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
    System.err.println("Exception when calling AccountInformationServiceAisApi#startConsentAuthorisation");
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


<a name="updateConsentsPsuData"></a>
# **updateConsentsPsuData**
> updateConsentsPsuData(consentId, authorisationId, xRequestID, body, digest, signature, tpPSignatureCertificate, PSU_ID, psUIDType, psUCorporateID, psUCorporateIDType, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation)

Update PSU Data for consents

This method update PSU data on the consents  resource if needed.  It may authorise a consent within the Embedded SCA Approach where needed.  Independently from the SCA Approach it supports e.g. the selection of  the authentication method and a non-SCA PSU authentication.  This methods updates PSU data on the cancellation authorisation resource if needed.   There are several possible Update PSU Data requests in the context of a consent request if needed,  which depends on the SCA approach:  * Redirect SCA Approach:   A specific Update PSU Data Request is applicable for      * the selection of authentication methods, before choosing the actual SCA approach. * Decoupled SCA Approach:   A specific Update PSU Data Request is only applicable for   * adding the PSU Identification, if not provided yet in the Payment Initiation Request or the Account Information Consent Request, or if no OAuth2 access token is used, or   * the selection of authentication methods. * Embedded SCA Approach:    The Update PSU Data Request might be used    * to add credentials as a first factor authentication data of the PSU and   * to select the authentication method and   * transaction authorisation.  The SCA Approach might depend on the chosen SCA method.  For that reason, the following possible Update PSU Data request can apply to all SCA approaches:  * Select an SCA method in case of several SCA methods are available for the customer.  There are the following request types on this access path:   * Update PSU Identification   * Update PSU Authentication   * Select PSU Autorization Method      WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change.   * Transaction Authorisation     WARNING: This method need a reduced header,      therefore many optional elements are not present.      Maybe in a later version the access path will change. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AccountInformationServiceAisApi;



AccountInformationServiceAisApi apiInstance = new AccountInformationServiceAisApi();

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
    System.err.println("Exception when calling AccountInformationServiceAisApi#updateConsentsPsuData");
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



