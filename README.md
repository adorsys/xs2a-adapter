# swagger-java-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-java-client</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:swagger-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/swagger-java-client-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.AccountInformationServiceAisApi;

import java.io.File;
import java.util.*;

public class AccountInformationServiceAisApiExample {

    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        
        // Configure HTTP basic authorization: BearerAuthOAuth
        HttpBasicAuth BearerAuthOAuth = (HttpBasicAuth) defaultClient.getAuthentication("BearerAuthOAuth");
        BearerAuthOAuth.setUsername("YOUR USERNAME");
        BearerAuthOAuth.setPassword("YOUR PASSWORD");
        
        

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
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://api.testbank.com/psd2*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AccountInformationServiceAisApi* | [**createConsent**](docs/AccountInformationServiceAisApi.md#createConsent) | **POST** /v1/consents | Create consent
*AccountInformationServiceAisApi* | [**deleteConsent**](docs/AccountInformationServiceAisApi.md#deleteConsent) | **DELETE** /v1/consents/{consentId} | Delete Consent
*AccountInformationServiceAisApi* | [**getAccountList**](docs/AccountInformationServiceAisApi.md#getAccountList) | **GET** /v1/accounts | Read Account List
*AccountInformationServiceAisApi* | [**getBalances**](docs/AccountInformationServiceAisApi.md#getBalances) | **GET** /v1/accounts/{account-id}/balances | Read Balance
*AccountInformationServiceAisApi* | [**getConsentInformation**](docs/AccountInformationServiceAisApi.md#getConsentInformation) | **GET** /v1/consents/{consentId} | Get Consent Request
*AccountInformationServiceAisApi* | [**getConsentScaStatus**](docs/AccountInformationServiceAisApi.md#getConsentScaStatus) | **GET** /v1/consents/{consentId}/authorisations/{authorisationId} | Read the SCA status of the consent authorisation.
*AccountInformationServiceAisApi* | [**getConsentStatus**](docs/AccountInformationServiceAisApi.md#getConsentStatus) | **GET** /v1/consents/{consentId}/status | Consent status request
*AccountInformationServiceAisApi* | [**getTransactionDetails**](docs/AccountInformationServiceAisApi.md#getTransactionDetails) | **GET** /v1/accounts/{account-id}/transactions/{resourceId} | Read Transaction Details
*AccountInformationServiceAisApi* | [**getTransactionList**](docs/AccountInformationServiceAisApi.md#getTransactionList) | **GET** /v1/accounts/{account-id}/transactions/ | Read Transaction List
*AccountInformationServiceAisApi* | [**readAccountDetails**](docs/AccountInformationServiceAisApi.md#readAccountDetails) | **GET** /v1/accounts/{account-id} | Read Account Details
*AccountInformationServiceAisApi* | [**startConsentAuthorisation**](docs/AccountInformationServiceAisApi.md#startConsentAuthorisation) | **POST** /v1/consents/{consentId}/authorisations | Start the authorisation process for a consent
*AccountInformationServiceAisApi* | [**updateConsentsPsuData**](docs/AccountInformationServiceAisApi.md#updateConsentsPsuData) | **PUT** /v1/consents/{consentId}/authorisations/{authorisationId} | Update PSU Data for consents
*CommonAisAndPisServicesApi* | [**getConsentScaStatus**](docs/CommonAisAndPisServicesApi.md#getConsentScaStatus) | **GET** /v1/consents/{consentId}/authorisations/{authorisationId} | Read the SCA status of the consent authorisation.
*CommonAisAndPisServicesApi* | [**getPaymentCancellationScaStatus**](docs/CommonAisAndPisServicesApi.md#getPaymentCancellationScaStatus) | **GET** /v1/{payment-service}/{paymentId}/cancellation-authorisations/{cancellationId} | Read the SCA status of the payment cancellation&#x27;s authorisation.
*CommonAisAndPisServicesApi* | [**getPaymentInitiationAuthorisation**](docs/CommonAisAndPisServicesApi.md#getPaymentInitiationAuthorisation) | **GET** /v1/{payment-service}/{paymentId}/authorisations | Get Payment Initiation Authorisation Sub-Resources Request
*CommonAisAndPisServicesApi* | [**getPaymentInitiationAuthorisation_0**](docs/CommonAisAndPisServicesApi.md#getPaymentInitiationAuthorisation_0) | **GET** /v1/signing-baskets/{basketId}/authorisations | Get Signing Basket Authorisation Sub-Resources Request
*CommonAisAndPisServicesApi* | [**getPaymentInitiationScaStatus**](docs/CommonAisAndPisServicesApi.md#getPaymentInitiationScaStatus) | **GET** /v1/{payment-service}/{paymentId}/authorisations/{authorisationId} | Read the SCA Status of the payment authorisation
*CommonAisAndPisServicesApi* | [**getSigningBasketScaStatus**](docs/CommonAisAndPisServicesApi.md#getSigningBasketScaStatus) | **GET** /v1/signing-baskets/{basketId}/authorisations/{authorisationId} | Read the SCA status of the signing basket authorisation
*CommonAisAndPisServicesApi* | [**startConsentAuthorisation**](docs/CommonAisAndPisServicesApi.md#startConsentAuthorisation) | **POST** /v1/consents/{consentId}/authorisations | Start the authorisation process for a consent
*CommonAisAndPisServicesApi* | [**startPaymentAuthorisation**](docs/CommonAisAndPisServicesApi.md#startPaymentAuthorisation) | **POST** /v1/{payment-service}/{paymentId}/authorisations | Start the authorisation process for a payment initiation
*CommonAisAndPisServicesApi* | [**startPaymentInitiationCancellationAuthorisation**](docs/CommonAisAndPisServicesApi.md#startPaymentInitiationCancellationAuthorisation) | **POST** /v1/{payment-service}/{paymentId}/cancellation-authorisations | Start the authorisation process for the cancellation of the addressed payment
*CommonAisAndPisServicesApi* | [**startSigningBasketAuthorisation**](docs/CommonAisAndPisServicesApi.md#startSigningBasketAuthorisation) | **POST** /v1/signing-baskets/{basketId}/authorisations | Start the authorisation process for a signing basket
*CommonAisAndPisServicesApi* | [**updateConsentsPsuData**](docs/CommonAisAndPisServicesApi.md#updateConsentsPsuData) | **PUT** /v1/consents/{consentId}/authorisations/{authorisationId} | Update PSU Data for consents
*CommonAisAndPisServicesApi* | [**updatePaymentCancellationPsuData**](docs/CommonAisAndPisServicesApi.md#updatePaymentCancellationPsuData) | **PUT** /v1/{payment-service}/{paymentId}/cancellation-authorisations/{cancellationId} | Update PSU Data for payment initiation cancellation
*CommonAisAndPisServicesApi* | [**updatePaymentPsuData**](docs/CommonAisAndPisServicesApi.md#updatePaymentPsuData) | **PUT** /v1/{payment-service}/{paymentId}/authorisations/{authorisationId} | Update PSU data for payment initiation
*CommonAisAndPisServicesApi* | [**updateSigningBasketPsuData**](docs/CommonAisAndPisServicesApi.md#updateSigningBasketPsuData) | **PUT** /v1/signing-baskets/{basketId}/authorisations/{authorisationId} | Update PSU Data for signing basket
*ConfirmationOfFundsServiceApi* | [**checkAvailabilityOfFunds**](docs/ConfirmationOfFundsServiceApi.md#checkAvailabilityOfFunds) | **POST** /v1/funds-confirmations | Confirmation of Funds Request
*PaymentInitiationServicePisApi* | [**cancelPayment**](docs/PaymentInitiationServicePisApi.md#cancelPayment) | **DELETE** /v1/{payment-service}/{paymentId} | Payment Cancellation Request
*PaymentInitiationServicePisApi* | [**getPaymentCancellationScaStatus**](docs/PaymentInitiationServicePisApi.md#getPaymentCancellationScaStatus) | **GET** /v1/{payment-service}/{paymentId}/cancellation-authorisations/{cancellationId} | Read the SCA status of the payment cancellation&#x27;s authorisation.
*PaymentInitiationServicePisApi* | [**getPaymentInformation**](docs/PaymentInitiationServicePisApi.md#getPaymentInformation) | **GET** /v1/{payment-service}/{paymentId} | Get Payment Information
*PaymentInitiationServicePisApi* | [**getPaymentInitiationAuthorisation**](docs/PaymentInitiationServicePisApi.md#getPaymentInitiationAuthorisation) | **GET** /v1/{payment-service}/{paymentId}/authorisations | Get Payment Initiation Authorisation Sub-Resources Request
*PaymentInitiationServicePisApi* | [**getPaymentInitiationAuthorisation_0**](docs/PaymentInitiationServicePisApi.md#getPaymentInitiationAuthorisation_0) | **GET** /v1/consents/{consentId}/authorisations | Get Consent Authorisation Sub-Resources Request
*PaymentInitiationServicePisApi* | [**getPaymentInitiationCancellationAuthorisationInformation**](docs/PaymentInitiationServicePisApi.md#getPaymentInitiationCancellationAuthorisationInformation) | **GET** /v1/{payment-service}/{paymentId}/cancellation-authorisations | Will deliver an array of resource identifications to all generated cancellation authorisation sub-resources.
*PaymentInitiationServicePisApi* | [**getPaymentInitiationScaStatus**](docs/PaymentInitiationServicePisApi.md#getPaymentInitiationScaStatus) | **GET** /v1/{payment-service}/{paymentId}/authorisations/{authorisationId} | Read the SCA Status of the payment authorisation
*PaymentInitiationServicePisApi* | [**getPaymentInitiationStatus**](docs/PaymentInitiationServicePisApi.md#getPaymentInitiationStatus) | **GET** /v1/{payment-service}/{paymentId}/status | Payment initiation status request
*PaymentInitiationServicePisApi* | [**initiatePayment**](docs/PaymentInitiationServicePisApi.md#initiatePayment) | **POST** /v1/{payment-service}/{payment-product} | Payment initiation request
*PaymentInitiationServicePisApi* | [**startPaymentAuthorisation**](docs/PaymentInitiationServicePisApi.md#startPaymentAuthorisation) | **POST** /v1/{payment-service}/{paymentId}/authorisations | Start the authorisation process for a payment initiation
*PaymentInitiationServicePisApi* | [**startPaymentInitiationCancellationAuthorisation**](docs/PaymentInitiationServicePisApi.md#startPaymentInitiationCancellationAuthorisation) | **POST** /v1/{payment-service}/{paymentId}/cancellation-authorisations | Start the authorisation process for the cancellation of the addressed payment
*PaymentInitiationServicePisApi* | [**updatePaymentCancellationPsuData**](docs/PaymentInitiationServicePisApi.md#updatePaymentCancellationPsuData) | **PUT** /v1/{payment-service}/{paymentId}/cancellation-authorisations/{cancellationId} | Update PSU Data for payment initiation cancellation
*PaymentInitiationServicePisApi* | [**updatePaymentPsuData**](docs/PaymentInitiationServicePisApi.md#updatePaymentPsuData) | **PUT** /v1/{payment-service}/{paymentId}/authorisations/{authorisationId} | Update PSU data for payment initiation
*SigningBasketsApi* | [**createSigningBasket**](docs/SigningBasketsApi.md#createSigningBasket) | **POST** /v1/signing-baskets | Create a signing basket resource
*SigningBasketsApi* | [**getPaymentInitiationAuthorisation**](docs/SigningBasketsApi.md#getPaymentInitiationAuthorisation) | **GET** /v1/signing-baskets/{basketId}/authorisations | Get Signing Basket Authorisation Sub-Resources Request
*SigningBasketsApi* | [**getSigningBasket**](docs/SigningBasketsApi.md#getSigningBasket) | **GET** /v1/signing-baskets/{basketId} | Returns the content of an signing basket object.
*SigningBasketsApi* | [**getSigningBasketScaStatus**](docs/SigningBasketsApi.md#getSigningBasketScaStatus) | **GET** /v1/signing-baskets/{basketId}/authorisations/{authorisationId} | Read the SCA status of the signing basket authorisation
*SigningBasketsApi* | [**startSigningBasketAuthorisation**](docs/SigningBasketsApi.md#startSigningBasketAuthorisation) | **POST** /v1/signing-baskets/{basketId}/authorisations | Start the authorisation process for a signing basket
*SigningBasketsApi* | [**updateSigningBasketPsuData**](docs/SigningBasketsApi.md#updateSigningBasketPsuData) | **PUT** /v1/signing-baskets/{basketId}/authorisations/{authorisationId} | Update PSU Data for signing basket


## Documentation for Models

 - [AccountAccess](docs/AccountAccess.md)
 - [AccountDetails](docs/AccountDetails.md)
 - [AccountList](docs/AccountList.md)
 - [AccountReferenceBban](docs/AccountReferenceBban.md)
 - [AccountReferenceIban](docs/AccountReferenceIban.md)
 - [AccountReferenceMaskedPan](docs/AccountReferenceMaskedPan.md)
 - [AccountReferenceMsisdn](docs/AccountReferenceMsisdn.md)
 - [AccountReferencePan](docs/AccountReferencePan.md)
 - [AccountReport](docs/AccountReport.md)
 - [AccountStatus](docs/AccountStatus.md)
 - [Address](docs/Address.md)
 - [Amount](docs/Amount.md)
 - [AuthenticationObject](docs/AuthenticationObject.md)
 - [AuthenticationType](docs/AuthenticationType.md)
 - [Authorisations](docs/Authorisations.md)
 - [AuthorisationsList](docs/AuthorisationsList.md)
 - [Balance](docs/Balance.md)
 - [BalanceList](docs/BalanceList.md)
 - [BalanceType](docs/BalanceType.md)
 - [BulkPaymentInitiationCrossBorderJson](docs/BulkPaymentInitiationCrossBorderJson.md)
 - [BulkPaymentInitiationCrossBorderWithStatusResponse](docs/BulkPaymentInitiationCrossBorderWithStatusResponse.md)
 - [BulkPaymentInitiationSctInstJson](docs/BulkPaymentInitiationSctInstJson.md)
 - [BulkPaymentInitiationSctInstWithStatusResponse](docs/BulkPaymentInitiationSctInstWithStatusResponse.md)
 - [BulkPaymentInitiationSctJson](docs/BulkPaymentInitiationSctJson.md)
 - [BulkPaymentInitiationSctWithStatusResponse](docs/BulkPaymentInitiationSctWithStatusResponse.md)
 - [BulkPaymentInitiationTarget2Json](docs/BulkPaymentInitiationTarget2Json.md)
 - [BulkPaymentInitiationTarget2WithStatusResponse](docs/BulkPaymentInitiationTarget2WithStatusResponse.md)
 - [CancellationList](docs/CancellationList.md)
 - [ChallengeData](docs/ChallengeData.md)
 - [ChosenScaMethod](docs/ChosenScaMethod.md)
 - [ConfirmationOfFunds](docs/ConfirmationOfFunds.md)
 - [ConsentIdList](docs/ConsentIdList.md)
 - [ConsentInformationResponse200Json](docs/ConsentInformationResponse200Json.md)
 - [ConsentStatus](docs/ConsentStatus.md)
 - [ConsentStatusResponse200](docs/ConsentStatusResponse200.md)
 - [Consents](docs/Consents.md)
 - [ConsentsResponse201](docs/ConsentsResponse201.md)
 - [DayOfExecution](docs/DayOfExecution.md)
 - [ExchangeRate](docs/ExchangeRate.md)
 - [ExchangeRateList](docs/ExchangeRateList.md)
 - [ExecutionRule](docs/ExecutionRule.md)
 - [FrequencyCode](docs/FrequencyCode.md)
 - [LinksSigningBasket](docs/LinksSigningBasket.md)
 - [MessageCodeTextAisSpecific](docs/MessageCodeTextAisSpecific.md)
 - [MessageCodeTextPiisSpecific](docs/MessageCodeTextPiisSpecific.md)
 - [MessageCodeTextPisSpecific](docs/MessageCodeTextPisSpecific.md)
 - [MessageCodeTextUnspecific](docs/MessageCodeTextUnspecific.md)
 - [PaymentIdList](docs/PaymentIdList.md)
 - [PaymentInitationRequestMultiLevelScaResponse201](docs/PaymentInitationRequestMultiLevelScaResponse201.md)
 - [PaymentInitationRequestResponse201](docs/PaymentInitationRequestResponse201.md)
 - [PaymentInitiationCancelResponse200202](docs/PaymentInitiationCancelResponse200202.md)
 - [PaymentInitiationCrossBorderBulkElementJson](docs/PaymentInitiationCrossBorderBulkElementJson.md)
 - [PaymentInitiationCrossBorderJson](docs/PaymentInitiationCrossBorderJson.md)
 - [PaymentInitiationCrossBorderWithStatusResponse](docs/PaymentInitiationCrossBorderWithStatusResponse.md)
 - [PaymentInitiationSctBulkElementJson](docs/PaymentInitiationSctBulkElementJson.md)
 - [PaymentInitiationSctInstBulkElementJson](docs/PaymentInitiationSctInstBulkElementJson.md)
 - [PaymentInitiationSctInstJson](docs/PaymentInitiationSctInstJson.md)
 - [PaymentInitiationSctInstWithStatusResponse](docs/PaymentInitiationSctInstWithStatusResponse.md)
 - [PaymentInitiationSctJson](docs/PaymentInitiationSctJson.md)
 - [PaymentInitiationSctWithStatusResponse](docs/PaymentInitiationSctWithStatusResponse.md)
 - [PaymentInitiationStatusResponse200Json](docs/PaymentInitiationStatusResponse200Json.md)
 - [PaymentInitiationTarget2BulkElementJson](docs/PaymentInitiationTarget2BulkElementJson.md)
 - [PaymentInitiationTarget2Json](docs/PaymentInitiationTarget2Json.md)
 - [PaymentInitiationTarget2WithStatusResponse](docs/PaymentInitiationTarget2WithStatusResponse.md)
 - [PeriodicPaymentInitiationCrossBorderJson](docs/PeriodicPaymentInitiationCrossBorderJson.md)
 - [PeriodicPaymentInitiationCrossBorderWithStatusResponse](docs/PeriodicPaymentInitiationCrossBorderWithStatusResponse.md)
 - [PeriodicPaymentInitiationMultipartBody](docs/PeriodicPaymentInitiationMultipartBody.md)
 - [PeriodicPaymentInitiationSctInstJson](docs/PeriodicPaymentInitiationSctInstJson.md)
 - [PeriodicPaymentInitiationSctInstWithStatusResponse](docs/PeriodicPaymentInitiationSctInstWithStatusResponse.md)
 - [PeriodicPaymentInitiationSctJson](docs/PeriodicPaymentInitiationSctJson.md)
 - [PeriodicPaymentInitiationSctWithStatusResponse](docs/PeriodicPaymentInitiationSctWithStatusResponse.md)
 - [PeriodicPaymentInitiationTarget2Json](docs/PeriodicPaymentInitiationTarget2Json.md)
 - [PeriodicPaymentInitiationTarget2WithStatusResponse](docs/PeriodicPaymentInitiationTarget2WithStatusResponse.md)
 - [PeriodicPaymentInitiationXmlPart2StandingorderTypeJson](docs/PeriodicPaymentInitiationXmlPart2StandingorderTypeJson.md)
 - [PsuData](docs/PsuData.md)
 - [PurposeCode](docs/PurposeCode.md)
 - [ReadBalanceResponse200](docs/ReadBalanceResponse200.md)
 - [RemittanceInformationStructured](docs/RemittanceInformationStructured.md)
 - [ScaMethods](docs/ScaMethods.md)
 - [ScaStatus](docs/ScaStatus.md)
 - [ScaStatusResponse](docs/ScaStatusResponse.md)
 - [SelectPsuAuthenticationMethod](docs/SelectPsuAuthenticationMethod.md)
 - [SelectPsuAuthenticationMethodResponse](docs/SelectPsuAuthenticationMethodResponse.md)
 - [SigningBasket](docs/SigningBasket.md)
 - [SigningBasketResponse200](docs/SigningBasketResponse200.md)
 - [SigningBasketResponse201](docs/SigningBasketResponse201.md)
 - [StartScaprocessResponse](docs/StartScaprocessResponse.md)
 - [TppMessageAISACCESSEXCEEDED429](docs/TppMessageAISACCESSEXCEEDED429.md)
 - [TppMessageAISCONSENTINVALID401](docs/TppMessageAISCONSENTINVALID401.md)
 - [TppMessageAISREQUESTEDFORMATSINVALID406](docs/TppMessageAISREQUESTEDFORMATSINVALID406.md)
 - [TppMessageAISSESSIONSNOTSUPPORTED400](docs/TppMessageAISSESSIONSNOTSUPPORTED400.md)
 - [TppMessageCategory](docs/TppMessageCategory.md)
 - [TppMessageGENERICCERTIFICATEBLOCKED401](docs/TppMessageGENERICCERTIFICATEBLOCKED401.md)
 - [TppMessageGENERICCERTIFICATEEXPIRED401](docs/TppMessageGENERICCERTIFICATEEXPIRED401.md)
 - [TppMessageGENERICCERTIFICATEINVALID401](docs/TppMessageGENERICCERTIFICATEINVALID401.md)
 - [TppMessageGENERICCERTIFICATEMISSING401](docs/TppMessageGENERICCERTIFICATEMISSING401.md)
 - [TppMessageGENERICCERTIFICATEREVOKED401](docs/TppMessageGENERICCERTIFICATEREVOKED401.md)
 - [TppMessageGENERICCONSENTEXPIRED401](docs/TppMessageGENERICCONSENTEXPIRED401.md)
 - [TppMessageGENERICCONSENTINVALID401](docs/TppMessageGENERICCONSENTINVALID401.md)
 - [TppMessageGENERICCONSENTUNKNOWN403400](docs/TppMessageGENERICCONSENTUNKNOWN403400.md)
 - [TppMessageGENERICCORPORATEIDINVALID401](docs/TppMessageGENERICCORPORATEIDINVALID401.md)
 - [TppMessageGENERICFORMATERROR400](docs/TppMessageGENERICFORMATERROR400.md)
 - [TppMessageGENERICPARAMETERNOTSUPPORTED400](docs/TppMessageGENERICPARAMETERNOTSUPPORTED400.md)
 - [TppMessageGENERICPERIODINVALID400](docs/TppMessageGENERICPERIODINVALID400.md)
 - [TppMessageGENERICPSUCREDENTIALSINVALID401](docs/TppMessageGENERICPSUCREDENTIALSINVALID401.md)
 - [TppMessageGENERICRESOURCEEXPIRED403400](docs/TppMessageGENERICRESOURCEEXPIRED403400.md)
 - [TppMessageGENERICRESOURCEUNKNOWN404403400](docs/TppMessageGENERICRESOURCEUNKNOWN404403400.md)
 - [TppMessageGENERICSCAMETHODUNKNOWN400](docs/TppMessageGENERICSCAMETHODUNKNOWN400.md)
 - [TppMessageGENERICSERVICEBLOCKED403](docs/TppMessageGENERICSERVICEBLOCKED403.md)
 - [TppMessageGENERICSERVICEINVALID400405](docs/TppMessageGENERICSERVICEINVALID400405.md)
 - [TppMessageGENERICSIGNATUREINVALID401](docs/TppMessageGENERICSIGNATUREINVALID401.md)
 - [TppMessageGENERICSIGNATUREMISSING401](docs/TppMessageGENERICSIGNATUREMISSING401.md)
 - [TppMessageGENERICTIMESTAMPINVALID400](docs/TppMessageGENERICTIMESTAMPINVALID400.md)
 - [TppMessageGENERICTOKENEXPIRED401](docs/TppMessageGENERICTOKENEXPIRED401.md)
 - [TppMessageGENERICTOKENINVALID401](docs/TppMessageGENERICTOKENINVALID401.md)
 - [TppMessageGENERICTOKENUNKNOWN401](docs/TppMessageGENERICTOKENUNKNOWN401.md)
 - [TppMessageGeneric](docs/TppMessageGeneric.md)
 - [TppMessagePIISCARDINVALID400](docs/TppMessagePIISCARDINVALID400.md)
 - [TppMessagePIISNOPIISACTIVATION400](docs/TppMessagePIISNOPIISACTIVATION400.md)
 - [TppMessagePISEXECUTIONDATEINVALID400](docs/TppMessagePISEXECUTIONDATEINVALID400.md)
 - [TppMessagePISPAYMENTFAILED400](docs/TppMessagePISPAYMENTFAILED400.md)
 - [TppMessagePISPRODUCTINVALID403](docs/TppMessagePISPRODUCTINVALID403.md)
 - [TppMessagePISPRODUCTUNKNOWN404](docs/TppMessagePISPRODUCTUNKNOWN404.md)
 - [TppMessagePISREQUIREDKIDMISSING401](docs/TppMessagePISREQUIREDKIDMISSING401.md)
 - [TppMessages](docs/TppMessages.md)
 - [TppMessages400](docs/TppMessages400.md)
 - [TppMessages401](docs/TppMessages401.md)
 - [TppMessages403](docs/TppMessages403.md)
 - [TppMessages404](docs/TppMessages404.md)
 - [TppMessages405](docs/TppMessages405.md)
 - [TppMessages406](docs/TppMessages406.md)
 - [TppMessages429](docs/TppMessages429.md)
 - [TransactionAuthorisation](docs/TransactionAuthorisation.md)
 - [TransactionDetails](docs/TransactionDetails.md)
 - [TransactionList](docs/TransactionList.md)
 - [TransactionStatus](docs/TransactionStatus.md)
 - [TransactionsResponse200Json](docs/TransactionsResponse200Json.md)
 - [UpdatePsuAuthentication](docs/UpdatePsuAuthentication.md)
 - [UpdatePsuAuthenticationResponse](docs/UpdatePsuAuthenticationResponse.md)
 - [UpdatePsuIdenticationResponse](docs/UpdatePsuIdenticationResponse.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### BearerAuthOAuth


- **Type**: HTTP basic authentication





## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

info@berlin-group.org

