# PaymentInitationRequestResponse201

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transactionStatus** | [**TransactionStatus**](TransactionStatus.md) |  | 
**paymentId** | **String** |  | 
**transactionFees** | [**Amount**](Amount.md) |  |  [optional]
**transactionFeeIndicator** | **Boolean** |  |  [optional]
**scaMethods** | [**ScaMethods**](ScaMethods.md) |  |  [optional]
**chosenScaMethod** | [**ChosenScaMethod**](ChosenScaMethod.md) |  |  [optional]
**challengeData** | [**ChallengeData**](ChallengeData.md) |  |  [optional]
**_links** | [**LinksPaymentInitiation**](LinksPaymentInitiation.md) |  | 
**psuMessage** | **String** |  |  [optional]
**tppMessages** | [**List&lt;TppMessage2XX&gt;**](TppMessage2XX.md) |  |  [optional]
