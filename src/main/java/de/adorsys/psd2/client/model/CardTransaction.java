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
import de.adorsys.psd2.client.model.Address;
import de.adorsys.psd2.client.model.Amount;
import de.adorsys.psd2.client.model.GrandTotalAmount;
import de.adorsys.psd2.client.model.ReportExchangeRateList;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
/**
 * Card transaction information.
 */
@Schema(description = "Card transaction information.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2023-05-15T10:07:19.009+02:00[Europe/Berlin]")
public class CardTransaction {
  @SerializedName("cardTransactionId")
  private String cardTransactionId = null;

  @SerializedName("terminalId")
  private String terminalId = null;

  @SerializedName("transactionDate")
  private LocalDate transactionDate = null;

  @SerializedName("acceptorTransactionDateTime")
  private OffsetDateTime acceptorTransactionDateTime = null;

  @SerializedName("bookingDate")
  private LocalDate bookingDate = null;

  @SerializedName("valueDate")
  private LocalDate valueDate = null;

  @SerializedName("transactionAmount")
  private Amount transactionAmount = null;

  @SerializedName("grandTotalAmount")
  private GrandTotalAmount grandTotalAmount = null;

  @SerializedName("currencyExchange")
  private ReportExchangeRateList currencyExchange = null;

  @SerializedName("originalAmount")
  private Amount originalAmount = null;

  @SerializedName("markupFee")
  private Amount markupFee = null;

  @SerializedName("markupFeePercentage")
  private String markupFeePercentage = null;

  @SerializedName("cardAcceptorId")
  private String cardAcceptorId = null;

  @SerializedName("cardAcceptorAddress")
  private Address cardAcceptorAddress = null;

  @SerializedName("cardAcceptorPhone")
  private String cardAcceptorPhone = null;

  @SerializedName("merchantCategoryCode")
  private String merchantCategoryCode = null;

  @SerializedName("maskedPAN")
  private String maskedPAN = null;

  @SerializedName("transactionDetails")
  private String transactionDetails = null;

  @SerializedName("invoiced")
  private Boolean invoiced = null;

  @SerializedName("proprietaryBankTransactionCode")
  private String proprietaryBankTransactionCode = null;

  public CardTransaction cardTransactionId(String cardTransactionId) {
    this.cardTransactionId = cardTransactionId;
    return this;
  }

   /**
   * Get cardTransactionId
   * @return cardTransactionId
  **/
  @Schema(description = "")
  public String getCardTransactionId() {
    return cardTransactionId;
  }

  public void setCardTransactionId(String cardTransactionId) {
    this.cardTransactionId = cardTransactionId;
  }

  public CardTransaction terminalId(String terminalId) {
    this.terminalId = terminalId;
    return this;
  }

   /**
   * Get terminalId
   * @return terminalId
  **/
  @Schema(description = "")
  public String getTerminalId() {
    return terminalId;
  }

  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
  }

  public CardTransaction transactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
    return this;
  }

   /**
   * Get transactionDate
   * @return transactionDate
  **/
  @Schema(description = "")
  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
  }

  public CardTransaction acceptorTransactionDateTime(OffsetDateTime acceptorTransactionDateTime) {
    this.acceptorTransactionDateTime = acceptorTransactionDateTime;
    return this;
  }

   /**
   * Timestamp of the actual card transaction within the acceptance system
   * @return acceptorTransactionDateTime
  **/
  @Schema(description = "Timestamp of the actual card transaction within the acceptance system")
  public OffsetDateTime getAcceptorTransactionDateTime() {
    return acceptorTransactionDateTime;
  }

  public void setAcceptorTransactionDateTime(OffsetDateTime acceptorTransactionDateTime) {
    this.acceptorTransactionDateTime = acceptorTransactionDateTime;
  }

  public CardTransaction bookingDate(LocalDate bookingDate) {
    this.bookingDate = bookingDate;
    return this;
  }

   /**
   * Get bookingDate
   * @return bookingDate
  **/
  @Schema(description = "")
  public LocalDate getBookingDate() {
    return bookingDate;
  }

  public void setBookingDate(LocalDate bookingDate) {
    this.bookingDate = bookingDate;
  }

  public CardTransaction valueDate(LocalDate valueDate) {
    this.valueDate = valueDate;
    return this;
  }

   /**
   * The Date at which assets become available to the account owner in case of a credit, or cease to be available to the account owner in case of a debit entry. For card transactions this is the payment due date of related booked transactions of a card.
   * @return valueDate
  **/
  @Schema(description = "The Date at which assets become available to the account owner in case of a credit, or cease to be available to the account owner in case of a debit entry. For card transactions this is the payment due date of related booked transactions of a card.")
  public LocalDate getValueDate() {
    return valueDate;
  }

  public void setValueDate(LocalDate valueDate) {
    this.valueDate = valueDate;
  }

  public CardTransaction transactionAmount(Amount transactionAmount) {
    this.transactionAmount = transactionAmount;
    return this;
  }

   /**
   * Get transactionAmount
   * @return transactionAmount
  **/
  @Schema(required = true, description = "")
  public Amount getTransactionAmount() {
    return transactionAmount;
  }

  public void setTransactionAmount(Amount transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  public CardTransaction grandTotalAmount(GrandTotalAmount grandTotalAmount) {
    this.grandTotalAmount = grandTotalAmount;
    return this;
  }

   /**
   * Get grandTotalAmount
   * @return grandTotalAmount
  **/
  @Schema(description = "")
  public GrandTotalAmount getGrandTotalAmount() {
    return grandTotalAmount;
  }

  public void setGrandTotalAmount(GrandTotalAmount grandTotalAmount) {
    this.grandTotalAmount = grandTotalAmount;
  }

  public CardTransaction currencyExchange(ReportExchangeRateList currencyExchange) {
    this.currencyExchange = currencyExchange;
    return this;
  }

   /**
   * Get currencyExchange
   * @return currencyExchange
  **/
  @Schema(description = "")
  public ReportExchangeRateList getCurrencyExchange() {
    return currencyExchange;
  }

  public void setCurrencyExchange(ReportExchangeRateList currencyExchange) {
    this.currencyExchange = currencyExchange;
  }

  public CardTransaction originalAmount(Amount originalAmount) {
    this.originalAmount = originalAmount;
    return this;
  }

   /**
   * Get originalAmount
   * @return originalAmount
  **/
  @Schema(description = "")
  public Amount getOriginalAmount() {
    return originalAmount;
  }

  public void setOriginalAmount(Amount originalAmount) {
    this.originalAmount = originalAmount;
  }

  public CardTransaction markupFee(Amount markupFee) {
    this.markupFee = markupFee;
    return this;
  }

   /**
   * Get markupFee
   * @return markupFee
  **/
  @Schema(description = "")
  public Amount getMarkupFee() {
    return markupFee;
  }

  public void setMarkupFee(Amount markupFee) {
    this.markupFee = markupFee;
  }

  public CardTransaction markupFeePercentage(String markupFeePercentage) {
    this.markupFeePercentage = markupFeePercentage;
    return this;
  }

   /**
   * Get markupFeePercentage
   * @return markupFeePercentage
  **/
  @Schema(example = "0.3", description = "")
  public String getMarkupFeePercentage() {
    return markupFeePercentage;
  }

  public void setMarkupFeePercentage(String markupFeePercentage) {
    this.markupFeePercentage = markupFeePercentage;
  }

  public CardTransaction cardAcceptorId(String cardAcceptorId) {
    this.cardAcceptorId = cardAcceptorId;
    return this;
  }

   /**
   * Get cardAcceptorId
   * @return cardAcceptorId
  **/
  @Schema(description = "")
  public String getCardAcceptorId() {
    return cardAcceptorId;
  }

  public void setCardAcceptorId(String cardAcceptorId) {
    this.cardAcceptorId = cardAcceptorId;
  }

  public CardTransaction cardAcceptorAddress(Address cardAcceptorAddress) {
    this.cardAcceptorAddress = cardAcceptorAddress;
    return this;
  }

   /**
   * Get cardAcceptorAddress
   * @return cardAcceptorAddress
  **/
  @Schema(description = "")
  public Address getCardAcceptorAddress() {
    return cardAcceptorAddress;
  }

  public void setCardAcceptorAddress(Address cardAcceptorAddress) {
    this.cardAcceptorAddress = cardAcceptorAddress;
  }

  public CardTransaction cardAcceptorPhone(String cardAcceptorPhone) {
    this.cardAcceptorPhone = cardAcceptorPhone;
    return this;
  }

   /**
   * Get cardAcceptorPhone
   * @return cardAcceptorPhone
  **/
  @Schema(description = "")
  public String getCardAcceptorPhone() {
    return cardAcceptorPhone;
  }

  public void setCardAcceptorPhone(String cardAcceptorPhone) {
    this.cardAcceptorPhone = cardAcceptorPhone;
  }

  public CardTransaction merchantCategoryCode(String merchantCategoryCode) {
    this.merchantCategoryCode = merchantCategoryCode;
    return this;
  }

   /**
   * Get merchantCategoryCode
   * @return merchantCategoryCode
  **/
  @Schema(description = "")
  public String getMerchantCategoryCode() {
    return merchantCategoryCode;
  }

  public void setMerchantCategoryCode(String merchantCategoryCode) {
    this.merchantCategoryCode = merchantCategoryCode;
  }

  public CardTransaction maskedPAN(String maskedPAN) {
    this.maskedPAN = maskedPAN;
    return this;
  }

   /**
   * Get maskedPAN
   * @return maskedPAN
  **/
  @Schema(description = "")
  public String getMaskedPAN() {
    return maskedPAN;
  }

  public void setMaskedPAN(String maskedPAN) {
    this.maskedPAN = maskedPAN;
  }

  public CardTransaction transactionDetails(String transactionDetails) {
    this.transactionDetails = transactionDetails;
    return this;
  }

   /**
   * Get transactionDetails
   * @return transactionDetails
  **/
  @Schema(description = "")
  public String getTransactionDetails() {
    return transactionDetails;
  }

  public void setTransactionDetails(String transactionDetails) {
    this.transactionDetails = transactionDetails;
  }

  public CardTransaction invoiced(Boolean invoiced) {
    this.invoiced = invoiced;
    return this;
  }

   /**
   * Get invoiced
   * @return invoiced
  **/
  @Schema(description = "")
  public Boolean isInvoiced() {
    return invoiced;
  }

  public void setInvoiced(Boolean invoiced) {
    this.invoiced = invoiced;
  }

  public CardTransaction proprietaryBankTransactionCode(String proprietaryBankTransactionCode) {
    this.proprietaryBankTransactionCode = proprietaryBankTransactionCode;
    return this;
  }

   /**
   * Get proprietaryBankTransactionCode
   * @return proprietaryBankTransactionCode
  **/
  @Schema(description = "")
  public String getProprietaryBankTransactionCode() {
    return proprietaryBankTransactionCode;
  }

  public void setProprietaryBankTransactionCode(String proprietaryBankTransactionCode) {
    this.proprietaryBankTransactionCode = proprietaryBankTransactionCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CardTransaction cardTransaction = (CardTransaction) o;
    return Objects.equals(this.cardTransactionId, cardTransaction.cardTransactionId) &&
        Objects.equals(this.terminalId, cardTransaction.terminalId) &&
        Objects.equals(this.transactionDate, cardTransaction.transactionDate) &&
        Objects.equals(this.acceptorTransactionDateTime, cardTransaction.acceptorTransactionDateTime) &&
        Objects.equals(this.bookingDate, cardTransaction.bookingDate) &&
        Objects.equals(this.valueDate, cardTransaction.valueDate) &&
        Objects.equals(this.transactionAmount, cardTransaction.transactionAmount) &&
        Objects.equals(this.grandTotalAmount, cardTransaction.grandTotalAmount) &&
        Objects.equals(this.currencyExchange, cardTransaction.currencyExchange) &&
        Objects.equals(this.originalAmount, cardTransaction.originalAmount) &&
        Objects.equals(this.markupFee, cardTransaction.markupFee) &&
        Objects.equals(this.markupFeePercentage, cardTransaction.markupFeePercentage) &&
        Objects.equals(this.cardAcceptorId, cardTransaction.cardAcceptorId) &&
        Objects.equals(this.cardAcceptorAddress, cardTransaction.cardAcceptorAddress) &&
        Objects.equals(this.cardAcceptorPhone, cardTransaction.cardAcceptorPhone) &&
        Objects.equals(this.merchantCategoryCode, cardTransaction.merchantCategoryCode) &&
        Objects.equals(this.maskedPAN, cardTransaction.maskedPAN) &&
        Objects.equals(this.transactionDetails, cardTransaction.transactionDetails) &&
        Objects.equals(this.invoiced, cardTransaction.invoiced) &&
        Objects.equals(this.proprietaryBankTransactionCode, cardTransaction.proprietaryBankTransactionCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardTransactionId, terminalId, transactionDate, acceptorTransactionDateTime, bookingDate, valueDate, transactionAmount, grandTotalAmount, currencyExchange, originalAmount, markupFee, markupFeePercentage, cardAcceptorId, cardAcceptorAddress, cardAcceptorPhone, merchantCategoryCode, maskedPAN, transactionDetails, invoiced, proprietaryBankTransactionCode);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CardTransaction {\n");
    
    sb.append("    cardTransactionId: ").append(toIndentedString(cardTransactionId)).append("\n");
    sb.append("    terminalId: ").append(toIndentedString(terminalId)).append("\n");
    sb.append("    transactionDate: ").append(toIndentedString(transactionDate)).append("\n");
    sb.append("    acceptorTransactionDateTime: ").append(toIndentedString(acceptorTransactionDateTime)).append("\n");
    sb.append("    bookingDate: ").append(toIndentedString(bookingDate)).append("\n");
    sb.append("    valueDate: ").append(toIndentedString(valueDate)).append("\n");
    sb.append("    transactionAmount: ").append(toIndentedString(transactionAmount)).append("\n");
    sb.append("    grandTotalAmount: ").append(toIndentedString(grandTotalAmount)).append("\n");
    sb.append("    currencyExchange: ").append(toIndentedString(currencyExchange)).append("\n");
    sb.append("    originalAmount: ").append(toIndentedString(originalAmount)).append("\n");
    sb.append("    markupFee: ").append(toIndentedString(markupFee)).append("\n");
    sb.append("    markupFeePercentage: ").append(toIndentedString(markupFeePercentage)).append("\n");
    sb.append("    cardAcceptorId: ").append(toIndentedString(cardAcceptorId)).append("\n");
    sb.append("    cardAcceptorAddress: ").append(toIndentedString(cardAcceptorAddress)).append("\n");
    sb.append("    cardAcceptorPhone: ").append(toIndentedString(cardAcceptorPhone)).append("\n");
    sb.append("    merchantCategoryCode: ").append(toIndentedString(merchantCategoryCode)).append("\n");
    sb.append("    maskedPAN: ").append(toIndentedString(maskedPAN)).append("\n");
    sb.append("    transactionDetails: ").append(toIndentedString(transactionDetails)).append("\n");
    sb.append("    invoiced: ").append(toIndentedString(invoiced)).append("\n");
    sb.append("    proprietaryBankTransactionCode: ").append(toIndentedString(proprietaryBankTransactionCode)).append("\n");
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
