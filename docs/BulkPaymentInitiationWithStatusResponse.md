# BulkPaymentInitiationWithStatusResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**batchBookingPreferred** | **Boolean** |  |  [optional]
**requestedExecutionDate** | [**LocalDate**](LocalDate.md) |  |  [optional]
**debtorAccount** | [**AccountReference**](AccountReference.md) |  | 
**payments** | [**List&lt;PaymentInitiationBulkElementJson&gt;**](PaymentInitiationBulkElementJson.md) | A list of generic JSON bodies payment initations for bulk payments via JSON.  Note: Some fields from single payments do not occcur in a bulk payment element  | 
**transactionStatus** | [**TransactionStatus**](TransactionStatus.md) |  |  [optional]
