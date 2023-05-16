# BulkPaymentInitiationWithStatusResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**batchBookingPreferred** | **Boolean** |  |  [optional]
**requestedExecutionDate** | [**LocalDate**](LocalDate.md) |  |  [optional]
**acceptorTransactionDateTime** | [**OffsetDateTime**](OffsetDateTime.md) |  |  [optional]
**debtorAccount** | [**AccountReference**](AccountReference.md) |  | 
**paymentInformationId** | **String** |  |  [optional]
**payments** | [**List&lt;PaymentInitiationBulkElementJson&gt;**](PaymentInitiationBulkElementJson.md) | A list of generic JSON bodies payment initiations for bulk payments via JSON.  Note: Some fields from single payments do not occur in a bulk payment element  | 
**transactionStatus** | [**TransactionStatus**](TransactionStatus.md) |  |  [optional]
**tppMessages** | [**List&lt;TppMessageGeneric&gt;**](TppMessageGeneric.md) | Messages to the TPP on operational issues. |  [optional]
