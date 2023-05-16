# PeriodicPaymentInitiationJson

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**endToEndIdentification** | **String** |  |  [optional]
**instructionIdentification** | **String** |  |  [optional]
**debtorName** | **String** |  |  [optional]
**debtorAccount** | [**AccountReference**](AccountReference.md) |  | 
**ultimateDebtor** | **String** |  |  [optional]
**instructedAmount** | [**Amount**](Amount.md) |  | 
**creditorAccount** | [**AccountReference**](AccountReference.md) |  | 
**creditorAgent** | **String** |  |  [optional]
**creditorId** | **String** | Identification of Creditors, e.g. a SEPA Creditor ID. |  [optional]
**creditorName** | **String** |  | 
**creditorAddress** | [**Address**](Address.md) |  |  [optional]
**ultimateCreditor** | **String** |  |  [optional]
**purposeCode** | [**PurposeCode**](PurposeCode.md) |  |  [optional]
**remittanceInformationUnstructured** | **String** |  |  [optional]
**remittanceInformationUnstructuredArray** | [**RemittanceInformationUnstructuredArray**](RemittanceInformationUnstructuredArray.md) |  |  [optional]
**remittanceInformationStructured** | [**RemittanceInformationStructuredMax140**](RemittanceInformationStructuredMax140.md) |  |  [optional]
**remittanceInformationStructuredArray** | [**RemittanceInformationStructuredArray**](RemittanceInformationStructuredArray.md) |  |  [optional]
**startDate** | [**LocalDate**](LocalDate.md) |  | 
**endDate** | [**LocalDate**](LocalDate.md) |  |  [optional]
**executionRule** | [**ExecutionRule**](ExecutionRule.md) |  |  [optional]
**frequency** | [**FrequencyCode**](FrequencyCode.md) |  | 
**dayOfExecution** | [**DayOfExecution**](DayOfExecution.md) |  |  [optional]
**monthsOfExecution** | [**MonthsOfExecution**](MonthsOfExecution.md) |  |  [optional]
