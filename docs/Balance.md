
# Balance

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**balanceAmount** | [**Amount**](Amount.md) |  | 
**balanceType** | [**BalanceType**](BalanceType.md) |  | 
**lastChangeDateTime** | [**OffsetDateTime**](OffsetDateTime.md) | This data element might be used to indicate e.g. with the expected or booked balance that no action is known  on the account, which is not yet booked.  |  [optional]
**referenceDate** | [**LocalDate**](LocalDate.md) | Reference date of the balance |  [optional]
**lastCommittedTransaction** | **String** | \&quot;entryReference\&quot; of the last commited transaction to support the TPP in identifying whether all  PSU transactions are already known.  |  [optional]




