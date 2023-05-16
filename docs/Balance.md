# Balance

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**balanceAmount** | [**Amount**](Amount.md) |  | 
**balanceType** | [**BalanceType**](BalanceType.md) |  | 
**creditLimitIncluded** | **Boolean** | A flag indicating if the credit limit of the corresponding account  is included in the calculation of the balance, where applicable.  |  [optional]
**lastChangeDateTime** | [**OffsetDateTime**](OffsetDateTime.md) | This data element might be used to indicate e.g. with the expected or booked balance that no action is known  on the account, which is not yet booked.  |  [optional]
**referenceDate** | [**LocalDate**](LocalDate.md) | Indicates the date of the balance. |  [optional]
**lastCommittedTransaction** | **String** | \&quot;entryReference\&quot; of the last committed transaction to support the TPP in identifying whether all  PSU transactions are already known.  |  [optional]
