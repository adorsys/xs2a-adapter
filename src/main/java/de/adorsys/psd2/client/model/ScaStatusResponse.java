/*
 * NextGenPSD2 XS2A Framework
 * # Summary The **NextGenPSD2** *Framework Version 1.3.12* offers a modern, open, harmonised and interoperable set of Application Programming Interfaces (APIs) as the safest and most efficient way to provide data securely. The NextGenPSD2 Framework reduces XS2A complexity and costs, addresses the problem of multiple competing standards  in Europe and, aligned with the goals of the Euro Retail Payments Board, enables European banking customers to benefit from innovative products and services ('Banking as a Service') by granting TPPs safe and secure (authenticated and authorised) access to their bank accounts and financial data.  The possible Approaches are:   * Redirect SCA Approach    * OAuth SCA Approach   * Decoupled SCA Approach    * Embedded SCA Approach without SCA method   * Embedded SCA Approach with only one SCA method available   * Embedded SCA Approach with Selection of a SCA method    Not every message defined in this API definition is necessary for all approaches.    Furthermore this API definition does not differ between methods which are mandatory, conditional, or optional.   Therefore for a particular implementation of a Berlin Group PSD2 compliant API it is only necessary to support    a certain subset of the methods defined in this API definition.    **Please have a look at the implementation guidelines if you are not sure    which message has to be used for the approach you are going to use.**  ## Some General Remarks Related to this version of the OpenAPI Specification: * **This API definition is based on the Implementation Guidelines of the Berlin Group PSD2 API.**    It is not a replacement in any sense.   The main specification is (at the moment) always the Implementation Guidelines of the Berlin Group PSD2 API. * **This API definition contains the REST-API for requests from the PISP to the ASPSP.** * **This API definition contains the messages for all different approaches defined in the Implementation Guidelines.** * According to the OpenAPI-Specification [https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md]        \"If in is \"header\" and the name field is \"Accept\", \"Content-Type\" or \"Authorization\", the parameter definition SHALL be ignored.\"      The element \"Accept\" will not be defined in this file at any place.      The elements \"Content-Type\" and \"Authorization\" are implicitly defined by the OpenApi tags \"content\" and \"security\".    * There are several predefined types which might occur in payment initiation messages,    but are not used in the standard JSON messages in the Implementation Guidelines.   Therefore they are not used in the corresponding messages in this file either.   We added them for the convenience of the user.   If there is a payment product, which needs these fields, one can easily use the predefined types.   But the ASPSP need not to accept them in general.    * **We omit the definition of all standard HTTP header elements (mandatory/optional/conditional)    except they are mentioned in the Implementation Guidelines.**   Therefore the implementer might add these in his own realisation of a PSD2 complient API in addition to the elements defined in this file.     ## General Remarks on Data Types  The Berlin Group definition of UTF-8 strings in context of the PSD2 API has to support at least the following characters  a b c d e f g h i j k l m n o p q r s t u v w x y z  A B C D E F G H I J K L M N O P Q R S T U V W X Y Z  0 1 2 3 4 5 6 7 8 9  / - ? : ( ) . , ' +  Space 
 *
 * OpenAPI spec version: 1.3.12_2022-07-01
 * Contact: info@berlin-group.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package de.adorsys.psd2.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.adorsys.psd2.client.model.LinksAll;
import de.adorsys.psd2.client.model.ScaStatus;
import de.adorsys.psd2.client.model.TppMessageGeneric;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Body of the JSON response with SCA Status.
 */
@Schema(description = "Body of the JSON response with SCA Status.")

public class ScaStatusResponse {
  @SerializedName("scaStatus")
  private ScaStatus scaStatus = null;

  @SerializedName("psuMessage")
  private String psuMessage = null;

  @SerializedName("psuName")
  private String psuName = null;

  @SerializedName("trustedBeneficiaryFlag")
  private Boolean trustedBeneficiaryFlag = null;

  @SerializedName("_links")
  private LinksAll _links = null;

  @SerializedName("tppMessages")
  private List<TppMessageGeneric> tppMessages = null;

  public ScaStatusResponse scaStatus(ScaStatus scaStatus) {
    this.scaStatus = scaStatus;
    return this;
  }

   /**
   * Get scaStatus
   * @return scaStatus
  **/
  @Schema(required = true, description = "")
  public ScaStatus getScaStatus() {
    return scaStatus;
  }

  public void setScaStatus(ScaStatus scaStatus) {
    this.scaStatus = scaStatus;
  }

  public ScaStatusResponse psuMessage(String psuMessage) {
    this.psuMessage = psuMessage;
    return this;
  }

   /**
   * Get psuMessage
   * @return psuMessage
  **/
  @Schema(description = "")
  public String getPsuMessage() {
    return psuMessage;
  }

  public void setPsuMessage(String psuMessage) {
    this.psuMessage = psuMessage;
  }

  public ScaStatusResponse psuName(String psuName) {
    this.psuName = psuName;
    return this;
  }

   /**
   * Name of the PSU. In case of a corporate account, this might be the person acting on behalf of the corporate. 
   * @return psuName
  **/
  @Schema(description = "Name of the PSU. In case of a corporate account, this might be the person acting on behalf of the corporate. ")
  public String getPsuName() {
    return psuName;
  }

  public void setPsuName(String psuName) {
    this.psuName = psuName;
  }

  public ScaStatusResponse trustedBeneficiaryFlag(Boolean trustedBeneficiaryFlag) {
    this.trustedBeneficiaryFlag = trustedBeneficiaryFlag;
    return this;
  }

   /**
   * Get trustedBeneficiaryFlag
   * @return trustedBeneficiaryFlag
  **/
  @Schema(description = "")
  public Boolean getTrustedBeneficiaryFlag() {
    return trustedBeneficiaryFlag;
  }

  public void setTrustedBeneficiaryFlag(Boolean trustedBeneficiaryFlag) {
    this.trustedBeneficiaryFlag = trustedBeneficiaryFlag;
  }

  public ScaStatusResponse _links(LinksAll _links) {
    this._links = _links;
    return this;
  }

   /**
   * Get _links
   * @return _links
  **/
  @Schema(description = "")
  public LinksAll getLinks() {
    return _links;
  }

  public void setLinks(LinksAll _links) {
    this._links = _links;
  }

  public ScaStatusResponse tppMessages(List<TppMessageGeneric> tppMessages) {
    this.tppMessages = tppMessages;
    return this;
  }

  public ScaStatusResponse addTppMessagesItem(TppMessageGeneric tppMessagesItem) {
    if (this.tppMessages == null) {
      this.tppMessages = new ArrayList<>();
    }
    this.tppMessages.add(tppMessagesItem);
    return this;
  }

   /**
   * Messages to the TPP on operational issues.
   * @return tppMessages
  **/
  @Schema(description = "Messages to the TPP on operational issues.")
  public List<TppMessageGeneric> getTppMessages() {
    return tppMessages;
  }

  public void setTppMessages(List<TppMessageGeneric> tppMessages) {
    this.tppMessages = tppMessages;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScaStatusResponse scaStatusResponse = (ScaStatusResponse) o;
    return Objects.equals(this.scaStatus, scaStatusResponse.scaStatus) &&
        Objects.equals(this.psuMessage, scaStatusResponse.psuMessage) &&
        Objects.equals(this.psuName, scaStatusResponse.psuName) &&
        Objects.equals(this.trustedBeneficiaryFlag, scaStatusResponse.trustedBeneficiaryFlag) &&
        Objects.equals(this._links, scaStatusResponse._links) &&
        Objects.equals(this.tppMessages, scaStatusResponse.tppMessages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scaStatus, psuMessage, psuName, trustedBeneficiaryFlag, _links, tppMessages);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScaStatusResponse {\n");
    
    sb.append("    scaStatus: ").append(toIndentedString(scaStatus)).append("\n");
    sb.append("    psuMessage: ").append(toIndentedString(psuMessage)).append("\n");
    sb.append("    psuName: ").append(toIndentedString(psuName)).append("\n");
    sb.append("    trustedBeneficiaryFlag: ").append(toIndentedString(trustedBeneficiaryFlag)).append("\n");
    sb.append("    _links: ").append(toIndentedString(_links)).append("\n");
    sb.append("    tppMessages: ").append(toIndentedString(tppMessages)).append("\n");
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