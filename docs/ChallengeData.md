# ChallengeData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**image** | **byte[]** | PNG data (max. 512 kilobyte) to be displayed to the PSU, Base64 encoding, cp. [RFC4648]. This attribute is used only, when PHOTO_OTP or CHIP_OTP is the selected SCA method.  |  [optional]
**data** | **List&lt;String&gt;** | A collection of strings as challenge data. |  [optional]
**imageLink** | **String** | A link where the ASPSP will provides the challenge image for the TPP. |  [optional]
**otpMaxLength** | **Integer** | The maximal length for the OTP to be typed in by the PSU. |  [optional]
**otpFormat** | [**OtpFormatEnum**](#OtpFormatEnum) | The format type of the OTP to be typed in. The admitted values are \&quot;characters\&quot; or \&quot;integer\&quot;. |  [optional]
**additionalInformation** | **String** | Additional explanation for the PSU to explain e.g. fallback mechanism for the chosen SCA method. The TPP is obliged to show this to the PSU.  |  [optional]

<a name="OtpFormatEnum"></a>
## Enum: OtpFormatEnum
Name | Value
---- | -----
CHARACTERS | &quot;characters&quot;
INTEGER | &quot;integer&quot;
