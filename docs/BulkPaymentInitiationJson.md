# BulkPaymentInitiationJson

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**batchBookingPreferred** | **Boolean** |  |  [optional]
**debtorAccount** | [**AccountReference**](AccountReference.md) |  | 
**requestedExecutionDate** | [**LocalDate**](LocalDate.md) |  |  [optional]
**requestedExecutionTime** | [**OffsetDateTime**](OffsetDateTime.md) |  |  [optional]
**payments** | [**List&lt;PaymentInitiationBulkElementJson&gt;**](PaymentInitiationBulkElementJson.md) | A list of generic JSON bodies payment initiations for bulk payments via JSON.  Note: Some fields from single payments do not occur in a bulk payment element  | 
**debtorName** | **String** |  |  [optional]
