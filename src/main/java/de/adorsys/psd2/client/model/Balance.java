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
import de.adorsys.psd2.client.model.Amount;
import de.adorsys.psd2.client.model.BalanceType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
/**
 * A single balance element. 
 */
@Schema(description = "A single balance element. ")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2023-05-15T10:07:19.009+02:00[Europe/Berlin]")
public class Balance {
  @SerializedName("balanceAmount")
  private Amount balanceAmount = null;

  @SerializedName("balanceType")
  private BalanceType balanceType = null;

  @SerializedName("creditLimitIncluded")
  private Boolean creditLimitIncluded = null;

  @SerializedName("lastChangeDateTime")
  private OffsetDateTime lastChangeDateTime = null;

  @SerializedName("referenceDate")
  private LocalDate referenceDate = null;

  @SerializedName("lastCommittedTransaction")
  private String lastCommittedTransaction = null;

  public Balance balanceAmount(Amount balanceAmount) {
    this.balanceAmount = balanceAmount;
    return this;
  }

   /**
   * Get balanceAmount
   * @return balanceAmount
  **/
  @Schema(required = true, description = "")
  public Amount getBalanceAmount() {
    return balanceAmount;
  }

  public void setBalanceAmount(Amount balanceAmount) {
    this.balanceAmount = balanceAmount;
  }

  public Balance balanceType(BalanceType balanceType) {
    this.balanceType = balanceType;
    return this;
  }

   /**
   * Get balanceType
   * @return balanceType
  **/
  @Schema(required = true, description = "")
  public BalanceType getBalanceType() {
    return balanceType;
  }

  public void setBalanceType(BalanceType balanceType) {
    this.balanceType = balanceType;
  }

  public Balance creditLimitIncluded(Boolean creditLimitIncluded) {
    this.creditLimitIncluded = creditLimitIncluded;
    return this;
  }

   /**
   * A flag indicating if the credit limit of the corresponding account  is included in the calculation of the balance, where applicable. 
   * @return creditLimitIncluded
  **/
  @Schema(example = "false", description = "A flag indicating if the credit limit of the corresponding account  is included in the calculation of the balance, where applicable. ")
  public Boolean isCreditLimitIncluded() {
    return creditLimitIncluded;
  }

  public void setCreditLimitIncluded(Boolean creditLimitIncluded) {
    this.creditLimitIncluded = creditLimitIncluded;
  }

  public Balance lastChangeDateTime(OffsetDateTime lastChangeDateTime) {
    this.lastChangeDateTime = lastChangeDateTime;
    return this;
  }

   /**
   * This data element might be used to indicate e.g. with the expected or booked balance that no action is known  on the account, which is not yet booked. 
   * @return lastChangeDateTime
  **/
  @Schema(description = "This data element might be used to indicate e.g. with the expected or booked balance that no action is known  on the account, which is not yet booked. ")
  public OffsetDateTime getLastChangeDateTime() {
    return lastChangeDateTime;
  }

  public void setLastChangeDateTime(OffsetDateTime lastChangeDateTime) {
    this.lastChangeDateTime = lastChangeDateTime;
  }

  public Balance referenceDate(LocalDate referenceDate) {
    this.referenceDate = referenceDate;
    return this;
  }

   /**
   * Indicates the date of the balance.
   * @return referenceDate
  **/
  @Schema(description = "Indicates the date of the balance.")
  public LocalDate getReferenceDate() {
    return referenceDate;
  }

  public void setReferenceDate(LocalDate referenceDate) {
    this.referenceDate = referenceDate;
  }

  public Balance lastCommittedTransaction(String lastCommittedTransaction) {
    this.lastCommittedTransaction = lastCommittedTransaction;
    return this;
  }

   /**
   * \&quot;entryReference\&quot; of the last committed transaction to support the TPP in identifying whether all  PSU transactions are already known. 
   * @return lastCommittedTransaction
  **/
  @Schema(description = "\"entryReference\" of the last committed transaction to support the TPP in identifying whether all  PSU transactions are already known. ")
  public String getLastCommittedTransaction() {
    return lastCommittedTransaction;
  }

  public void setLastCommittedTransaction(String lastCommittedTransaction) {
    this.lastCommittedTransaction = lastCommittedTransaction;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Balance balance = (Balance) o;
    return Objects.equals(this.balanceAmount, balance.balanceAmount) &&
        Objects.equals(this.balanceType, balance.balanceType) &&
        Objects.equals(this.creditLimitIncluded, balance.creditLimitIncluded) &&
        Objects.equals(this.lastChangeDateTime, balance.lastChangeDateTime) &&
        Objects.equals(this.referenceDate, balance.referenceDate) &&
        Objects.equals(this.lastCommittedTransaction, balance.lastCommittedTransaction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(balanceAmount, balanceType, creditLimitIncluded, lastChangeDateTime, referenceDate, lastCommittedTransaction);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Balance {\n");
    
    sb.append("    balanceAmount: ").append(toIndentedString(balanceAmount)).append("\n");
    sb.append("    balanceType: ").append(toIndentedString(balanceType)).append("\n");
    sb.append("    creditLimitIncluded: ").append(toIndentedString(creditLimitIncluded)).append("\n");
    sb.append("    lastChangeDateTime: ").append(toIndentedString(lastChangeDateTime)).append("\n");
    sb.append("    referenceDate: ").append(toIndentedString(referenceDate)).append("\n");
    sb.append("    lastCommittedTransaction: ").append(toIndentedString(lastCommittedTransaction)).append("\n");
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
