# PaymentInitiationStatusResponse200Json

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transactionStatus** | [**TransactionStatus**](TransactionStatus.md) |  | 
**fundsAvailable** | **Boolean** |  |  [optional]
**psuMessage** | **String** |  |  [optional]
**ownerNames** | [**List&lt;AccountOwner&gt;**](AccountOwner.md) | List of owner names. Should only be delivered after successful SCA. Could be restricted to the current PSU by the ASPSP.  |  [optional]
**psuName** | **String** | Name of the PSU. In case of a corporate account, this might be the person acting on behalf of the corporate.  |  [optional]
**_links** | [**LinksPaymentInitiationStatus**](LinksPaymentInitiationStatus.md) |  |  [optional]
**tppMessages** | [**List&lt;TppMessageGeneric&gt;**](TppMessageGeneric.md) | Messages to the TPP on operational issues. |  [optional]
