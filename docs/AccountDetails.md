# AccountDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**resourceId** | **String** | This shall be filled, if addressable resource are created by the ASPSP on the /accounts or /card-accounts endpoint. |  [optional]
**iban** | **String** |  |  [optional]
**bban** | **String** |  |  [optional]
**msisdn** | **String** |  |  [optional]
**currency** | **String** |  | 
**name** | **String** | Name of the account given by the bank or the PSU in online-banking. |  [optional]
**product** | **String** | Product name of the bank for this account, proprietary definition. |  [optional]
**cashAccountType** | **String** |  |  [optional]
**status** | [**AccountStatus**](AccountStatus.md) |  |  [optional]
**bic** | **String** |  |  [optional]
**linkedAccounts** | **String** | Case of a set of pending card transactions, the APSP will provide the relevant cash account the card is set up on. |  [optional]
**usage** | [**UsageEnum**](#UsageEnum) | Specifies the usage of the account:   * PRIV: private personal account   * ORGA: professional account  |  [optional]
**details** | **String** | Specifications that might be provided by the ASPSP:   - characteristics of the account   - characteristics of the relevant card  |  [optional]
**balances** | [**BalanceList**](BalanceList.md) |  |  [optional]
**_links** | [**LinksAccountDetails**](LinksAccountDetails.md) |  |  [optional]
**ownerName** | **String** | Name of the legal account owner. If there is more than one owner, then e.g. two names might be noted here. |  [optional]
**ownerAddress** | [**Address**](Address.md) |  |  [optional]

<a name="UsageEnum"></a>
## Enum: UsageEnum
Name | Value
---- | -----
PRIV | &quot;PRIV&quot;
ORGA | &quot;ORGA&quot;
