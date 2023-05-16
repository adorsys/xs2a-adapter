# AccountDetails

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**resourceId** | **String** | This shall be filled, if addressable resource are created by the ASPSP on the /accounts or /card-accounts endpoint. |  [optional]
**iban** | **String** |  |  [optional]
**bban** | **String** |  |  [optional]
**msisdn** | **String** |  |  [optional]
**currency** | **String** |  | 
**ownerName** | **String** |  |  [optional]
**ownerNames** | [**List&lt;AccountOwner&gt;**](AccountOwner.md) | List of owner names.  |  [optional]
**psuName** | **String** | Name of the PSU. In case of a corporate account, this might be the person acting on behalf of the corporate.  |  [optional]
**name** | **String** | Name of the account, as assigned by the ASPSP, in agreement with the account owner in order to provide an additional means of identification of the account. |  [optional]
**displayName** | **String** |  |  [optional]
**product** | **String** | Product name of the bank for this account, proprietary definition. |  [optional]
**cashAccountType** | **String** |  |  [optional]
**status** | [**AccountStatus**](AccountStatus.md) |  |  [optional]
**bic** | **String** |  |  [optional]
**linkedAccounts** | **String** | Case of a set of pending card transactions, the APSP will provide the relevant cash account the card is set up on. |  [optional]
**usage** | [**UsageEnum**](#UsageEnum) | Specifies the usage of the account:   * PRIV: private personal account   * ORGA: professional account  |  [optional]
**details** | **String** | Specifications that might be provided by the ASPSP:   - characteristics of the account   - characteristics of the relevant card  |  [optional]
**balances** | [**BalanceList**](BalanceList.md) |  |  [optional]
**_links** | [**LinksAccountDetails**](LinksAccountDetails.md) |  |  [optional]

<a name="UsageEnum"></a>
## Enum: UsageEnum
Name | Value
---- | -----
PRIV | &quot;PRIV&quot;
ORGA | &quot;ORGA&quot;
