/*
 * BG PSD2 API
 * # Summary The **NextGenPSD2** *Framework Version 1.2* offers a modern, open, harmonised and interoperable set of  Application Programming Interfaces (APIs) as the safest and most efficient way to provide data securely.  The NextGenPSD2 Framework reduces XS2A complexity and costs, addresses the problem of multiple competing standards  in Europe and, aligned with the goals of the Euro Retail Payments Board, enables European banking customers to benefit from innovative products and services ('Banking as a Service')  by granting TPPs safe and secure (authenticated and authorised) access to their bank accounts and financial data.  The possible Approaches are:   * Redirect SCA Approach   * OAuth SCA Approach   * Decoupled SCA Approach   * Embedded SCA Approach without SCA method   * Embedded SCA Approach with only one SCA method available   * Embedded SCA Approach with Selection of a SCA method    Not every message defined in this API definition is necessary for all approaches.    Futhermore this API definition does not differ between methods which are mandatory, conditional, or optional   Therfore for a particular implementation of a Berlin Group PSD2 compliant API it is only necessary to support    a certain subset of the methods defined in this API definition.    **Please have a look at the implementation guidelines if you are not sure    which message has to be used for the approach you are going to use.**  ## Some General Remarks Related to this version of the OpenAPI Specification: * **This API definition is based on the Implementation Guidelines of the Berlin Group PSD2 API.**    It is not an replacement in any sense.   The main specification is (at the moment) allways the Implementation Guidelines of the Berlin Group PSD2 API. * **This API definition contains the REST-API for requests from the PISP to the ASPSP.** * **This API definition contains the messages for all different approaches defined in the Implementation Guidelines.** * According to the OpenAPI-Specification [https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md]        \"If in is \"header\" and the name field is \"Accept\", \"Content-Type\" or \"Authorization\", the parameter definition SHALL be ignored.\"      The element \"Accept\" will not be defined in this file at any place.      The elements \"Content-Type\" and \"Authorization\" are implicitly defined by the OpenApi tags \"content\" and \"security\".    * There are several predefined types which might occur in payment initiation messages,    but are not used in the standard JSON messages in the Implementation Guidelines.   Therefore they are not used in the corresponding messages in this file either.   We added them for the convinience of the user.   If there is a payment product, which need these field, one can easily use the predefined types.   But the ASPSP need not to accept them in general.    * **We ommit the definition of all standard HTTP header elements (mandatory/optional/conditional)    except they are mention in the Implementation Guidelines.**   Therefore the implementer might add the in his own realisation of a PSD2 comlient API in addition to the elements define in this file.     ## General Remarks on Data Types  The Berlin Group definition of UTF-8 strings in context of the PSD2 API have to support at least the following characters  a b c d e f g h i j k l m n o p q r s t u v w x y z  A B C D E F G H I J K L M N O P Q R S T U V W X Y Z  0 1 2 3 4 5 6 7 8 9  / - ? : ( ) . , ' +  Space 
 *
 * OpenAPI spec version: 1.2
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
import de.adorsys.psd2.client.model.Address;
import de.adorsys.psd2.client.model.Amount;
import de.adorsys.psd2.client.model.DayOfExecution;
import de.adorsys.psd2.client.model.ExecutionRule;
import de.adorsys.psd2.client.model.FrequencyCode;
import de.adorsys.psd2.client.model.TransactionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.time.LocalDate;

/**
 * JSON response body consistion of the corresponding periodic TARGET-2 payment initation JSON body together with  an optional transaction status field. 
 */
@Schema(description = "JSON response body consistion of the corresponding periodic TARGET-2 payment initation JSON body together with  an optional transaction status field. ")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2018-10-11T15:55:40.544+02:00[Europe/Berlin]")public class PeriodicPaymentInitiationTarget2WithStatusResponse {

  @SerializedName("endToEndIdentification")
  private String endToEndIdentification = null;

  @SerializedName("debtorAccount")
  private Object debtorAccount = null;

  @SerializedName("instructedAmount")
  private Amount instructedAmount = null;

  @SerializedName("creditorAccount")
  private Object creditorAccount = null;

  @SerializedName("creditorAgent")
  private String creditorAgent = null;

  @SerializedName("creditorName")
  private String creditorName = null;

  @SerializedName("creditorAddress")
  private Address creditorAddress = null;

  @SerializedName("remittanceInformationUnstructured")
  private String remittanceInformationUnstructured = null;

  @SerializedName("startDate")
  private LocalDate startDate = null;

  @SerializedName("endDate")
  private LocalDate endDate = null;

  @SerializedName("executionRule")
  private ExecutionRule executionRule = null;

  @SerializedName("frequency")
  private FrequencyCode frequency = null;

  @SerializedName("dayOfExecution")
  private DayOfExecution dayOfExecution = null;

  @SerializedName("transactionStatus")
  private TransactionStatus transactionStatus = null;
  public PeriodicPaymentInitiationTarget2WithStatusResponse endToEndIdentification(String endToEndIdentification) {
    this.endToEndIdentification = endToEndIdentification;
    return this;
  }

  

  /**
  * Get endToEndIdentification
  * @return endToEndIdentification
  **/
  @Schema(description = "")
  public String getEndToEndIdentification() {
    return endToEndIdentification;
  }
  public void setEndToEndIdentification(String endToEndIdentification) {
    this.endToEndIdentification = endToEndIdentification;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse debtorAccount(Object debtorAccount) {
    this.debtorAccount = debtorAccount;
    return this;
  }

  

  /**
  * Get debtorAccount
  * @return debtorAccount
  **/
  @Schema(required = true, description = "")
  public Object getDebtorAccount() {
    return debtorAccount;
  }
  public void setDebtorAccount(Object debtorAccount) {
    this.debtorAccount = debtorAccount;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse instructedAmount(Amount instructedAmount) {
    this.instructedAmount = instructedAmount;
    return this;
  }

  

  /**
  * Get instructedAmount
  * @return instructedAmount
  **/
  @Schema(required = true, description = "")
  public Amount getInstructedAmount() {
    return instructedAmount;
  }
  public void setInstructedAmount(Amount instructedAmount) {
    this.instructedAmount = instructedAmount;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse creditorAccount(Object creditorAccount) {
    this.creditorAccount = creditorAccount;
    return this;
  }

  

  /**
  * Get creditorAccount
  * @return creditorAccount
  **/
  @Schema(required = true, description = "")
  public Object getCreditorAccount() {
    return creditorAccount;
  }
  public void setCreditorAccount(Object creditorAccount) {
    this.creditorAccount = creditorAccount;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse creditorAgent(String creditorAgent) {
    this.creditorAgent = creditorAgent;
    return this;
  }

  

  /**
  * Get creditorAgent
  * @return creditorAgent
  **/
  @Schema(description = "")
  public String getCreditorAgent() {
    return creditorAgent;
  }
  public void setCreditorAgent(String creditorAgent) {
    this.creditorAgent = creditorAgent;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse creditorName(String creditorName) {
    this.creditorName = creditorName;
    return this;
  }

  

  /**
  * Get creditorName
  * @return creditorName
  **/
  @Schema(required = true, description = "")
  public String getCreditorName() {
    return creditorName;
  }
  public void setCreditorName(String creditorName) {
    this.creditorName = creditorName;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse creditorAddress(Address creditorAddress) {
    this.creditorAddress = creditorAddress;
    return this;
  }

  

  /**
  * Get creditorAddress
  * @return creditorAddress
  **/
  @Schema(description = "")
  public Address getCreditorAddress() {
    return creditorAddress;
  }
  public void setCreditorAddress(Address creditorAddress) {
    this.creditorAddress = creditorAddress;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse remittanceInformationUnstructured(String remittanceInformationUnstructured) {
    this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    return this;
  }

  

  /**
  * Get remittanceInformationUnstructured
  * @return remittanceInformationUnstructured
  **/
  @Schema(description = "")
  public String getRemittanceInformationUnstructured() {
    return remittanceInformationUnstructured;
  }
  public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
    this.remittanceInformationUnstructured = remittanceInformationUnstructured;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  

  /**
  * Get startDate
  * @return startDate
  **/
  @Schema(required = true, description = "")
  public LocalDate getStartDate() {
    return startDate;
  }
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse endDate(LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  

  /**
  * Get endDate
  * @return endDate
  **/
  @Schema(description = "")
  public LocalDate getEndDate() {
    return endDate;
  }
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse executionRule(ExecutionRule executionRule) {
    this.executionRule = executionRule;
    return this;
  }

  

  /**
  * Get executionRule
  * @return executionRule
  **/
  @Schema(description = "")
  public ExecutionRule getExecutionRule() {
    return executionRule;
  }
  public void setExecutionRule(ExecutionRule executionRule) {
    this.executionRule = executionRule;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse frequency(FrequencyCode frequency) {
    this.frequency = frequency;
    return this;
  }

  

  /**
  * Get frequency
  * @return frequency
  **/
  @Schema(required = true, description = "")
  public FrequencyCode getFrequency() {
    return frequency;
  }
  public void setFrequency(FrequencyCode frequency) {
    this.frequency = frequency;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse dayOfExecution(DayOfExecution dayOfExecution) {
    this.dayOfExecution = dayOfExecution;
    return this;
  }

  

  /**
  * Get dayOfExecution
  * @return dayOfExecution
  **/
  @Schema(description = "")
  public DayOfExecution getDayOfExecution() {
    return dayOfExecution;
  }
  public void setDayOfExecution(DayOfExecution dayOfExecution) {
    this.dayOfExecution = dayOfExecution;
  }
  public PeriodicPaymentInitiationTarget2WithStatusResponse transactionStatus(TransactionStatus transactionStatus) {
    this.transactionStatus = transactionStatus;
    return this;
  }

  

  /**
  * Get transactionStatus
  * @return transactionStatus
  **/
  @Schema(description = "")
  public TransactionStatus getTransactionStatus() {
    return transactionStatus;
  }
  public void setTransactionStatus(TransactionStatus transactionStatus) {
    this.transactionStatus = transactionStatus;
  }
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PeriodicPaymentInitiationTarget2WithStatusResponse periodicPaymentInitiationTarget2WithStatusResponse = (PeriodicPaymentInitiationTarget2WithStatusResponse) o;
    return Objects.equals(this.endToEndIdentification, periodicPaymentInitiationTarget2WithStatusResponse.endToEndIdentification) &&
        Objects.equals(this.debtorAccount, periodicPaymentInitiationTarget2WithStatusResponse.debtorAccount) &&
        Objects.equals(this.instructedAmount, periodicPaymentInitiationTarget2WithStatusResponse.instructedAmount) &&
        Objects.equals(this.creditorAccount, periodicPaymentInitiationTarget2WithStatusResponse.creditorAccount) &&
        Objects.equals(this.creditorAgent, periodicPaymentInitiationTarget2WithStatusResponse.creditorAgent) &&
        Objects.equals(this.creditorName, periodicPaymentInitiationTarget2WithStatusResponse.creditorName) &&
        Objects.equals(this.creditorAddress, periodicPaymentInitiationTarget2WithStatusResponse.creditorAddress) &&
        Objects.equals(this.remittanceInformationUnstructured, periodicPaymentInitiationTarget2WithStatusResponse.remittanceInformationUnstructured) &&
        Objects.equals(this.startDate, periodicPaymentInitiationTarget2WithStatusResponse.startDate) &&
        Objects.equals(this.endDate, periodicPaymentInitiationTarget2WithStatusResponse.endDate) &&
        Objects.equals(this.executionRule, periodicPaymentInitiationTarget2WithStatusResponse.executionRule) &&
        Objects.equals(this.frequency, periodicPaymentInitiationTarget2WithStatusResponse.frequency) &&
        Objects.equals(this.dayOfExecution, periodicPaymentInitiationTarget2WithStatusResponse.dayOfExecution) &&
        Objects.equals(this.transactionStatus, periodicPaymentInitiationTarget2WithStatusResponse.transactionStatus);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(endToEndIdentification, debtorAccount, instructedAmount, creditorAccount, creditorAgent, creditorName, creditorAddress, remittanceInformationUnstructured, startDate, endDate, executionRule, frequency, dayOfExecution, transactionStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PeriodicPaymentInitiationTarget2WithStatusResponse {\n");
    
    sb.append("    endToEndIdentification: ").append(toIndentedString(endToEndIdentification)).append("\n");
    sb.append("    debtorAccount: ").append(toIndentedString(debtorAccount)).append("\n");
    sb.append("    instructedAmount: ").append(toIndentedString(instructedAmount)).append("\n");
    sb.append("    creditorAccount: ").append(toIndentedString(creditorAccount)).append("\n");
    sb.append("    creditorAgent: ").append(toIndentedString(creditorAgent)).append("\n");
    sb.append("    creditorName: ").append(toIndentedString(creditorName)).append("\n");
    sb.append("    creditorAddress: ").append(toIndentedString(creditorAddress)).append("\n");
    sb.append("    remittanceInformationUnstructured: ").append(toIndentedString(remittanceInformationUnstructured)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    executionRule: ").append(toIndentedString(executionRule)).append("\n");
    sb.append("    frequency: ").append(toIndentedString(frequency)).append("\n");
    sb.append("    dayOfExecution: ").append(toIndentedString(dayOfExecution)).append("\n");
    sb.append("    transactionStatus: ").append(toIndentedString(transactionStatus)).append("\n");
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
