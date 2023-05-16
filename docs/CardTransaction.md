# CardTransaction

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**cardTransactionId** | **String** |  |  [optional]
**terminalId** | **String** |  |  [optional]
**transactionDate** | [**LocalDate**](LocalDate.md) |  |  [optional]
**acceptorTransactionDateTime** | [**OffsetDateTime**](OffsetDateTime.md) | Timestamp of the actual card transaction within the acceptance system |  [optional]
**bookingDate** | [**LocalDate**](LocalDate.md) |  |  [optional]
**valueDate** | [**LocalDate**](LocalDate.md) | The Date at which assets become available to the account owner in case of a credit, or cease to be available to the account owner in case of a debit entry. For card transactions this is the payment due date of related booked transactions of a card. |  [optional]
**transactionAmount** | [**Amount**](Amount.md) |  | 
**grandTotalAmount** | [**GrandTotalAmount**](GrandTotalAmount.md) |  |  [optional]
**currencyExchange** | [**ReportExchangeRateList**](ReportExchangeRateList.md) |  |  [optional]
**originalAmount** | [**Amount**](Amount.md) |  |  [optional]
**markupFee** | [**Amount**](Amount.md) |  |  [optional]
**markupFeePercentage** | **String** |  |  [optional]
**cardAcceptorId** | **String** |  |  [optional]
**cardAcceptorAddress** | [**Address**](Address.md) |  |  [optional]
**cardAcceptorPhone** | **String** |  |  [optional]
**merchantCategoryCode** | **String** |  |  [optional]
**maskedPAN** | **String** |  |  [optional]
**transactionDetails** | **String** |  |  [optional]
**invoiced** | **Boolean** |  |  [optional]
**proprietaryBankTransactionCode** | **String** |  |  [optional]
