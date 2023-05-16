# StandingOrderDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**startDate** | [**LocalDate**](LocalDate.md) |  | 
**frequency** | [**FrequencyCode**](FrequencyCode.md) |  | 
**endDate** | [**LocalDate**](LocalDate.md) |  |  [optional]
**executionRule** | [**ExecutionRule**](ExecutionRule.md) |  |  [optional]
**withinAMonthFlag** | **Boolean** | This element is only used in case of frequency equals \&quot;Monthly\&quot;.  If this element equals false it has no effect. If this element equals true, then the execution rule is overruled if the day of execution would fall into a different month using the execution rule.  Example: executionRule equals \&quot;preceding\&quot;, dayOfExecution equals \&quot;02\&quot; and the second of a month is a Sunday.  In this case, the transaction date would be on the last day of the month before.  This would be overruled if withinAMonthFlag equals true and the payment is processed on Monday the third of the Month. Remark: This attribute is rarely supported in the market.  |  [optional]
**monthsOfExecution** | [**MonthsOfExecution**](MonthsOfExecution.md) |  |  [optional]
**multiplicator** | **Integer** | This is multiplying the given frequency resulting the exact frequency, e.g. Frequency&#x3D;weekly and multiplicator&#x3D;3 means every 3 weeks. Remark: This attribute is rarely supported in the market.  |  [optional]
**dayOfExecution** | [**DayOfExecution**](DayOfExecution.md) |  |  [optional]
**limitAmount** | [**Amount**](Amount.md) |  |  [optional]
