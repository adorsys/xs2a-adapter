# PaymentInitiationJson

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
**creditorAgentName** | **String** |  |  [optional]
**creditorName** | **String** |  | 
**creditorAddress** | [**Address**](Address.md) |  |  [optional]
**creditorId** | **String** | Identification of Creditors, e.g. a SEPA Creditor ID. |  [optional]
**ultimateCreditor** | **String** |  |  [optional]
**purposeCode** | [**PurposeCode**](PurposeCode.md) |  |  [optional]
**chargeBearer** | [**ChargeBearer**](ChargeBearer.md) |  |  [optional]
**remittanceInformationUnstructured** | **String** |  |  [optional]
**remittanceInformationUnstructuredArray** | [**RemittanceInformationUnstructuredArray**](RemittanceInformationUnstructuredArray.md) |  |  [optional]
**remittanceInformationStructured** | [**RemittanceInformationStructuredMax140**](RemittanceInformationStructuredMax140.md) |  |  [optional]
**remittanceInformationStructuredArray** | [**RemittanceInformationStructuredArray**](RemittanceInformationStructuredArray.md) |  |  [optional]
**requestedExecutionDate** | [**LocalDate**](LocalDate.md) |  |  [optional]
