# AccountAccess

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accounts** | [**List&lt;AccountReference&gt;**](AccountReference.md) | Is asking for detailed account information.  If the array is empty, the TPP is asking for an accessible account list. This may be restricted in a PSU/ASPSP authorization dialogue. If the array is empty, also the arrays for balances or transactions shall be empty, if used.  |  [optional]
**balances** | [**List&lt;AccountReference&gt;**](AccountReference.md) | Is asking for balances of the addressed accounts.  If the array is empty, the TPP is asking for the balances of all accessible account lists. This may be restricted in a PSU/ASPSP authorization dialogue. If the array is empty, also the arrays for accounts or transactions shall be empty, if used.  |  [optional]
**transactions** | [**List&lt;AccountReference&gt;**](AccountReference.md) | Is asking for transactions of the addressed accounts.  If the array is empty, the TPP is asking for the transactions of all accessible account lists. This may be restricted in a PSU/ASPSP authorization dialogue. If the array is empty, also the arrays for accounts or balances shall be empty, if used.  |  [optional]
**availableAccounts** | [**AvailableAccountsEnum**](#AvailableAccountsEnum) | Optional if supported by API provider.  Only the value \&quot;allAccounts\&quot; is admitted.  |  [optional]
**availableAccountsWithBalance** | [**AvailableAccountsWithBalanceEnum**](#AvailableAccountsWithBalanceEnum) | Optional if supported by API provider.  Only the value \&quot;allAccounts\&quot; is admitted.  |  [optional]
**allPsd2** | [**AllPsd2Enum**](#AllPsd2Enum) | Optional if supported by API provider.  Only the value \&quot;allAccounts\&quot; is admitted.  |  [optional]
**additionalAccountInformation** | [**AdditionalInformationAccess**](AdditionalInformationAccess.md) |  |  [optional]

<a name="AvailableAccountsEnum"></a>
## Enum: AvailableAccountsEnum
Name | Value
---- | -----
ALLACCOUNTS | &quot;allAccounts&quot;

<a name="AvailableAccountsWithBalanceEnum"></a>
## Enum: AvailableAccountsWithBalanceEnum
Name | Value
---- | -----
ALLACCOUNTS | &quot;allAccounts&quot;

<a name="AllPsd2Enum"></a>
## Enum: AllPsd2Enum
Name | Value
---- | -----
ALLACCOUNTS | &quot;allAccounts&quot;
