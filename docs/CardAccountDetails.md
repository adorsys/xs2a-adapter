# CardAccountDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**resourceId** | **String** | This is the data element to be used in the path when retrieving data from a dedicated account. This shall be filled, if addressable resource are created by the ASPSP on the /card-accounts endpoint.  |  [optional]
**maskedPan** | **String** |  | 
**currency** | **String** |  | 
**name** | **String** | Name of the account given by the bank or the PSU in online-banking. |  [optional]
**product** | **String** | Product name of the bank for this account, proprietary definition. |  [optional]
**status** | [**AccountStatus**](AccountStatus.md) |  |  [optional]
**usage** | [**UsageEnum**](#UsageEnum) | Specifies the usage of the account:   * PRIV: private personal account   * ORGA: professional account  |  [optional]
**details** | **String** | Specifications that might be provided by the ASPSP:   - characteristics of the account   - characteristics of the relevant card  |  [optional]
**creditLimit** | [**Amount**](Amount.md) |  |  [optional]
**balances** | [**BalanceList**](BalanceList.md) |  |  [optional]
**_links** | [**LinksAccountDetails**](LinksAccountDetails.md) |  |  [optional]

<a name="UsageEnum"></a>
## Enum: UsageEnum
Name | Value
---- | -----
PRIV | &quot;PRIV&quot;
ORGA | &quot;ORGA&quot;
