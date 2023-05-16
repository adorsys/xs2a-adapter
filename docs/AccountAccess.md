# AccountAccess

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accounts** | [**List&lt;AccountReference&gt;**](AccountReference.md) | Is asking for detailed account information.   If the array is empty in a request, the TPP is asking for an accessible account list.  This may be restricted in a PSU/ASPSP authorization dialogue.  If the array is empty, also the arrays for balances, additionalInformation sub attributes or transactions shall be empty, if used.  |  [optional]
**balances** | [**List&lt;AccountReference&gt;**](AccountReference.md) | Is asking for balances of the addressed accounts.  If the array is empty in the request, the TPP is asking for the balances of all accessible account lists.  This may be restricted in a PSU/ASPSP authorization dialogue.  If the array is empty, also the arrays for accounts, additionalInformation sub attributes or transactions shall be empty, if used.  |  [optional]
**transactions** | [**List&lt;AccountReference&gt;**](AccountReference.md) | Is asking for transactions of the addressed accounts.   If the array is empty in the request, the TPP is asking for the transactions of all accessible account lists.  This may be restricted in a PSU/ASPSP authorization dialogue.  If the array is empty, also the arrays for accounts, additionalInformation sub attributes or balances shall be empty, if used.  |  [optional]
**additionalInformation** | [**AdditionalInformationAccess**](AdditionalInformationAccess.md) |  |  [optional]
**availableAccounts** | [**AvailableAccountsEnum**](#AvailableAccountsEnum) | Optional if supported by API provider.  The values \&quot;allAccounts\&quot; and \&quot;allAccountsWithOwnerName\&quot; are admitted.  The support of the \&quot;allAccountsWithOwnerName\&quot; value by the ASPSP is optional.  |  [optional]
**availableAccountsWithBalance** | [**AvailableAccountsWithBalanceEnum**](#AvailableAccountsWithBalanceEnum) | Optional if supported by API provider.  The values \&quot;allAccounts\&quot; and \&quot;allAccountsWithOwnerName\&quot; are admitted.  The support of the \&quot;allAccountsWithOwnerName\&quot; value by the ASPSP is optional.  |  [optional]
**allPsd2** | [**AllPsd2Enum**](#AllPsd2Enum) | Optional if supported by API provider.  The values \&quot;allAccounts\&quot; and \&quot;allAccountsWithOwnerName\&quot; are admitted.  The support of the \&quot;allAccountsWithOwnerName\&quot; value by the ASPSP is optional.  |  [optional]
**restrictedTo** | **List&lt;String&gt;** | If the TPP requests access to accounts via availableAccounts (List of available accounts), global  or bank driven consents, the TPP may include this element to restrict access to the referred  account types. Absence of the element is interpreted as \&quot;no restriction\&quot; (therefore access to  accounts of all types is requested). The element may only occur, if each of the elements    - accounts    - balances    - transactions  is either not present or contains an empty array.   |  [optional]

<a name="AvailableAccountsEnum"></a>
## Enum: AvailableAccountsEnum
Name | Value
---- | -----
ALLACCOUNTS | &quot;allAccounts&quot;
ALLACCOUNTSWITHOWNERNAME | &quot;allAccountsWithOwnerName&quot;

<a name="AvailableAccountsWithBalanceEnum"></a>
## Enum: AvailableAccountsWithBalanceEnum
Name | Value
---- | -----
ALLACCOUNTS | &quot;allAccounts&quot;
ALLACCOUNTSWITHOWNERNAME | &quot;allAccountsWithOwnerName&quot;

<a name="AllPsd2Enum"></a>
## Enum: AllPsd2Enum
Name | Value
---- | -----
ALLACCOUNTS | &quot;allAccounts&quot;
ALLACCOUNTSWITHOWNERNAME | &quot;allAccountsWithOwnerName&quot;
