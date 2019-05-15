/*
 * NextGenPSD2 XS2A Framework
 * # Summary The **NextGenPSD2** *Framework Version 1.3.2* offers a modern, open, harmonised and interoperable set of  Application Programming Interfaces (APIs) as the safest and most efficient way to provide data securely.  The NextGenPSD2 Framework reduces XS2A complexity and costs, addresses the problem of multiple competing standards  in Europe and, aligned with the goals of the Euro Retail Payments Board, enables European banking customers to benefit from innovative products and services ('Banking as a Service')  by granting TPPs safe and secure (authenticated and authorised) access to their bank accounts and financial data.  The possible Approaches are:   * Redirect SCA Approach   * OAuth SCA Approach   * Decoupled SCA Approach   * Embedded SCA Approach without SCA method   * Embedded SCA Approach with only one SCA method available   * Embedded SCA Approach with Selection of a SCA method    Not every message defined in this API definition is necessary for all approaches.    Furthermore this API definition does not differ between methods which are mandatory, conditional, or optional   Therefore for a particular implementation of a Berlin Group PSD2 compliant API it is only necessary to support    a certain subset of the methods defined in this API definition.    **Please have a look at the implementation guidelines if you are not sure    which message has to be used for the approach you are going to use.**  ## Some General Remarks Related to this version of the OpenAPI Specification: * **This API definition is based on the Implementation Guidelines of the Berlin Group PSD2 API.**    It is not an replacement in any sense.   The main specification is (at the moment) always the Implementation Guidelines of the Berlin Group PSD2 API. * **This API definition contains the REST-API for requests from the PISP to the ASPSP.** * **This API definition contains the messages for all different approaches defined in the Implementation Guidelines.** * According to the OpenAPI-Specification [https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md]        \"If in is \"header\" and the name field is \"Accept\", \"Content-Type\" or \"Authorization\", the parameter definition SHALL be ignored.\"      The element \"Accept\" will not be defined in this file at any place.      The elements \"Content-Type\" and \"Authorization\" are implicitly defined by the OpenApi tags \"content\" and \"security\".    * There are several predefined types which might occur in payment initiation messages,    but are not used in the standard JSON messages in the Implementation Guidelines.   Therefore they are not used in the corresponding messages in this file either.   We added them for the convenience of the user.   If there is a payment product, which need these field, one can easily use the predefined types.   But the ASPSP need not to accept them in general.    * **We omit the definition of all standard HTTP header elements (mandatory/optional/conditional)    except they are mention in the Implementation Guidelines.**   Therefore the implementer might add the in his own realisation of a PSD2 comlient API in addition to the elements define in this file.     ## General Remarks on Data Types  The Berlin Group definition of UTF-8 strings in context of the PSD2 API have to support at least the following characters  a b c d e f g h i j k l m n o p q r s t u v w x y z  A B C D E F G H I J K L M N O P Q R S T U V W X Y Z  0 1 2 3 4 5 6 7 8 9  / - ? : ( ) . , ' +  Space 
 *
 * OpenAPI spec version: 1.3.3 Mar 29th 2019
 * Contact: info@berlin-group.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package de.adorsys.psd2.client.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.adorsys.psd2.client.model.Error400AISAdditionalErrors;
import de.adorsys.psd2.client.model.MessageCode400AIS;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Standardised definition of reporting error information according to [RFC7807]  in case of a HTTP error code 400 for AIS. 
 */
@Schema(description = "Standardised definition of reporting error information according to [RFC7807]  in case of a HTTP error code 400 for AIS. ")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-05-15T12:45:45.795+02:00[Europe/Berlin]")public class Error400AIS {

  @SerializedName("type")
  private String type = null;

  @SerializedName("title")
  private String title = null;

  @SerializedName("detail")
  private String detail = null;

  @SerializedName("code")
  private MessageCode400AIS code = null;

  @SerializedName("additionalErrors")
  private List<Error400AISAdditionalErrors> additionalErrors = null;

  @SerializedName("_links")
  private Map _links = null;
  public Error400AIS type(String type) {
    this.type = type;
    return this;
  }

  

  /**
  * A URI reference [RFC3986] that identifies the problem type.  Remark For Future: These URI will be provided by NextGenPSD2 in future. 
  * @return type
  **/
  @Schema(required = true, description = "A URI reference [RFC3986] that identifies the problem type.  Remark For Future: These URI will be provided by NextGenPSD2 in future. ")
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public Error400AIS title(String title) {
    this.title = title;
    return this;
  }

  

  /**
  * Short human readable description of error type.  Could be in local language.  To be provided by ASPSPs. 
  * @return title
  **/
  @Schema(description = "Short human readable description of error type.  Could be in local language.  To be provided by ASPSPs. ")
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public Error400AIS detail(String detail) {
    this.detail = detail;
    return this;
  }

  

  /**
  * Detailed human readable text specific to this instance of the error.  XPath might be used to point to the issue generating the error in addition. Remark for Future: In future, a dedicated field might be introduced for the XPath. 
  * @return detail
  **/
  @Schema(description = "Detailed human readable text specific to this instance of the error.  XPath might be used to point to the issue generating the error in addition. Remark for Future: In future, a dedicated field might be introduced for the XPath. ")
  public String getDetail() {
    return detail;
  }
  public void setDetail(String detail) {
    this.detail = detail;
  }
  public Error400AIS code(MessageCode400AIS code) {
    this.code = code;
    return this;
  }

  

  /**
  * Get code
  * @return code
  **/
  @Schema(required = true, description = "")
  public MessageCode400AIS getCode() {
    return code;
  }
  public void setCode(MessageCode400AIS code) {
    this.code = code;
  }
  public Error400AIS additionalErrors(List<Error400AISAdditionalErrors> additionalErrors) {
    this.additionalErrors = additionalErrors;
    return this;
  }

  public Error400AIS addAdditionalErrorsItem(Error400AISAdditionalErrors additionalErrorsItem) {
    if (this.additionalErrors == null) {
      this.additionalErrors = new ArrayList<>();
    }
    this.additionalErrors.add(additionalErrorsItem);
    return this;
  }

  /**
  * Array of Error Information Blocks.  Might be used if more than one error is to be communicated 
  * @return additionalErrors
  **/
  @Schema(description = "Array of Error Information Blocks.  Might be used if more than one error is to be communicated ")
  public List<Error400AISAdditionalErrors> getAdditionalErrors() {
    return additionalErrors;
  }
  public void setAdditionalErrors(List<Error400AISAdditionalErrors> additionalErrors) {
    this.additionalErrors = additionalErrors;
  }
  public Error400AIS _links(Map _links) {
    this._links = _links;
    return this;
  }

  

  /**
  * Get _links
  * @return _links
  **/
  @Schema(description = "")
  public Map getLinks() {
    return _links;
  }
  public void setLinks(Map _links) {
    this._links = _links;
  }
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error400AIS error400AIS = (Error400AIS) o;
    return Objects.equals(this.type, error400AIS.type) &&
        Objects.equals(this.title, error400AIS.title) &&
        Objects.equals(this.detail, error400AIS.detail) &&
        Objects.equals(this.code, error400AIS.code) &&
        Objects.equals(this.additionalErrors, error400AIS.additionalErrors) &&
        Objects.equals(this._links, error400AIS._links);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(type, title, detail, code, additionalErrors, _links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error400AIS {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    additionalErrors: ").append(toIndentedString(additionalErrors)).append("\n");
    sb.append("    _links: ").append(toIndentedString(_links)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
