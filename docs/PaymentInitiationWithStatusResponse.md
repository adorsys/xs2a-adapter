# PaymentInitiationWithStatusResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**endToEndIdentification** | **String** |  |  [optional]
**debtorAccount** | [**AccountReference**](AccountReference.md) |  | 
**ultimateDebtor** | **String** |  |  [optional]
**instructedAmount** | [**Amount**](Amount.md) |  | 
**creditorAccount** | [**AccountReference**](AccountReference.md) |  | 
**creditorAgent** | **String** |  |  [optional]
**creditorName** | **String** |  | 
**creditorAddress** | [**Address**](Address.md) |  |  [optional]
**ultimateCreditor** | **String** |  |  [optional]
**purposeCode** | [**PurposeCode**](PurposeCode.md) |  |  [optional]
**remittanceInformationUnstructured** | **String** |  |  [optional]
**remittanceInformationStructured** | [**RemittanceInformationStructured**](RemittanceInformationStructured.md) |  |  [optional]
**requestedExecutionDate** | [**LocalDate**](LocalDate.md) |  |  [optional]
**transactionStatus** | [**TransactionStatus**](TransactionStatus.md) |  |  [optional]
