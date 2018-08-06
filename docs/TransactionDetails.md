
# TransactionDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transactionId** | **String** | the Transaction Id can be used as access-ID in the API, where more details on an transaction is offered.  If this data attribute is provided this shows that the AIS can get access on more details about this  transaction using the GET Transaction Details Request   |  [optional]
**entryReference** | **String** | Is the identification of the transaction as used e.g. for reference for deltafunction on application level.  The same identification as for example used within camt.05x messages.  |  [optional]
**endToEndId** | **String** | Unique end to end identity. |  [optional]
**mandateId** | **String** | Identification of Mandates, e.g. a SEPA Mandate ID. |  [optional]
**checkId** | **String** | Identification of a Cheque. |  [optional]
**creditorId** | **String** | Identification of Creditors, e.g. a SEPA Creditor ID. |  [optional]
**bookingDate** | [**LocalDate**](LocalDate.md) |  |  [optional]
**valueDate** | [**LocalDate**](LocalDate.md) | The Date at which assets become available to the account owner in case of a credit. |  [optional]
**transactionAmount** | [**Amount**](Amount.md) |  | 
**exchangeRate** | [**ExchangeRateList**](ExchangeRateList.md) |  |  [optional]
**creditorName** | **String** |  |  [optional]
**creditorAccount** | **Object** |  |  [optional]
**ultimateCreditor** | **String** |  |  [optional]
**debtorName** | **String** |  |  [optional]
**debtorAccount** | **Object** |  |  [optional]
**ultimateDebtor** | **String** |  |  [optional]
**remittanceInformationUnstructured** | **String** |  |  [optional]
**remittanceInformationStructured** | **String** | Reference as contained in the structured remittance reference structure (without the surrounding XML structure).  Different from other places the content is containt in plain form not in form of a structered field.  |  [optional]
**purposeCode** | [**PurposeCode**](PurposeCode.md) |  |  [optional]
**bankTransactionCode** | **String** |  |  [optional]
**proprietaryBankTransactionCode** | **String** |  |  [optional]
**_links** | [**Map**](Map.md) |  |  [optional]




