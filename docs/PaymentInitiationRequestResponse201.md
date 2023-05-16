# PaymentInitiationRequestResponse201

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transactionStatus** | [**TransactionStatus**](TransactionStatus.md) |  | 
**paymentId** | **String** |  | 
**transactionFees** | [**Amount**](Amount.md) |  |  [optional]
**currencyConversionFee** | [**Amount**](Amount.md) |  |  [optional]
**estimatedTotalAmount** | [**Amount**](Amount.md) |  |  [optional]
**estimatedInterbankSettlementAmount** | [**Amount**](Amount.md) |  |  [optional]
**transactionFeeIndicator** | **Boolean** |  |  [optional]
**scaMethods** | [**ScaMethods**](ScaMethods.md) |  |  [optional]
**chosenScaMethod** | [**ChosenScaMethod**](ChosenScaMethod.md) |  |  [optional]
**challengeData** | [**ChallengeData**](ChallengeData.md) |  |  [optional]
**_links** | [**LinksPaymentInitiation**](LinksPaymentInitiation.md) |  | 
**psuMessage** | **String** |  |  [optional]
**tppMessages** | [**List&lt;TppMessage201PaymentInitiation&gt;**](TppMessage201PaymentInitiation.md) |  |  [optional]
**scaStatus** | [**ScaStatus**](ScaStatus.md) |  |  [optional]
