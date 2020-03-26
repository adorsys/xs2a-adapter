# StandingOrderDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**startDate** | [**LocalDate**](LocalDate.md) | The first applicable day of execution starting from this date the first payment was/will be executed. | 
**endDate** | [**LocalDate**](LocalDate.md) | The last applicable day of execution if not given, it is an infinite standing order. |  [optional]
**executionRule** | [**ExecutionRule**](ExecutionRule.md) |  |  [optional]
**withinAMonthFlag** | **Boolean** | This element is only used in case of frequency equals \&quot;monthly\&quot;.  If this element equals false it has no effect. If this element equals true, then the execution rule is overruled if the day of execution would fall into a different month using the execution rule.  Example: executionRule equals \&quot;preceding\&quot;, dayOfExecution equals \&quot;02\&quot; and the second of a month is a Sunday. In this case, the transaction date would be on the last day of the month before. This would be overruled if withinAMonthFlag equals true and the payment is processed on Monday the third of the Month. Remark: This attribute is rarely supported in the market.  |  [optional]
**frequency** | [**FrequencyCode**](FrequencyCode.md) |  | 
**monthsOfExecution** | **List&lt;String&gt;** |  |  [optional]
**multiplicator** | **Integer** | This is multiplying the given frequency resulting the exact frequency, e.g. Frequency&#x3D;weekly and multiplicator&#x3D;3 means every 3 weeks. Remark: This attribute is rarely supported in the market.  |  [optional]
**dayOfExecution** | [**DayOfExecution**](DayOfExecution.md) |  |  [optional]
**limitAmount** | [**Amount**](Amount.md) |  |  [optional]
**standingOrderDetails** | [**StandingOrderDetails**](StandingOrderDetails.md) | Details of underlying standing orders.  |  [optional]
