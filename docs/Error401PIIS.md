# Error401PIIS

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | **String** | A URI reference [RFC3986] that identifies the problem type. Remark For Future: These URI will be provided by NextGenPSD2 in future.  | 
**title** | **String** | Short human readable description of error type. Could be in local language. To be provided by ASPSPs.  |  [optional]
**detail** | **String** | Detailed human readable text specific to this instance of the error. XPath might be used to point to the issue generating the error in addition. Remark for Future: In future, a dedicated field might be introduced for the XPath.  |  [optional]
**code** | **String** |  | 
**additionalErrors** | [**List&lt;Error401PIISAdditionalErrors&gt;**](Error401PIISAdditionalErrors.md) | Array of Error Information Blocks.  Might be used if more than one error is to be communicated  |  [optional]
**_links** | [**LinksAll**](LinksAll.md) |  |  [optional]
