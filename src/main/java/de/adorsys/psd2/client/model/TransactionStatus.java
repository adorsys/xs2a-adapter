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
import io.swagger.v3.oas.annotations.media.Schema;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * The transaction status is filled with codes of the ISO 20022 data table: - &#x27;ACCC&#x27;: &#x27;AcceptedSettlementCompleted&#x27; -   Settlement on the creditor&#x27;s account has been completed. - &#x27;ACCP&#x27;: &#x27;AcceptedCustomerProfile&#x27; -    Preceding check of technical validation was successful.    Customer profile check was also successful. - &#x27;ACSC&#x27;: &#x27;AcceptedSettlementCompleted&#x27; -    Settlement on the debtor�s account has been completed.      **Usage:** this can be used by the first agent to report to the debtor that the transaction has been completed.       **Warning:** this status is provided for transaction status reasons, not for financial information.    It can only be used after bilateral agreement. - &#x27;ACSP&#x27;: &#x27;AcceptedSettlementInProcess&#x27; -    All preceding checks such as technical validation and customer profile were successful and therefore the payment initiation has been accepted for execution. - &#x27;ACTC&#x27;: &#x27;AcceptedTechnicalValidation&#x27; -    Authentication and syntactical and semantical validation are successful. - &#x27;ACWC&#x27;: &#x27;AcceptedWithChange&#x27; -    Instruction is accepted but a change will be made, such as date or remittance not sent. - &#x27;ACWP&#x27;: &#x27;AcceptedWithoutPosting&#x27; -    Payment instruction included in the credit transfer is accepted without being posted to the creditor customer�s account. - &#x27;RCVD&#x27;: &#x27;Received&#x27; -    Payment initiation has been received by the receiving agent. - &#x27;PDNG&#x27;: &#x27;Pending&#x27; -    Payment initiation or individual transaction included in the payment initiation is pending.    Further checks and status update will be performed. - &#x27;RJCT&#x27;: &#x27;Rejected&#x27; -    Payment initiation or individual transaction included in the payment initiation has been rejected. - &#x27;CANC&#x27;: &#x27;Cancelled&#x27;   Payment initiation has been cancelled before execution   Remark: This codeis accepted as new code by ISO20022. - &#x27;ACFC&#x27;: &#x27;AcceptedFundsChecked&#x27; -   Preceding check of technical validation and customer profile was successful and an automatic funds check was positive .   Remark: This code is accepted as new code by ISO20022. - &#x27;PATC&#x27;: &#x27;PartiallyAcceptedTechnical&#x27;   Correct The payment initiation needs multiple authentications, where some but not yet all have been performed. Syntactical and semantical validations are successful.   Remark: This code is accepted as new code by ISO20022. - &#x27;PART&#x27;: &#x27;PartiallyAccepted&#x27; -   A number of transactions have been accepted, whereas another number of transactions have not yet achieved &#x27;accepted&#x27; status.   Remark: This code may be used only in case of bulk payments. It is only used in a situation where all mandated authorisations have been applied, but some payments have been rejected.    
 */
@JsonAdapter(TransactionStatus.Adapter.class)
public enum TransactionStatus {
  ACCC("ACCC"),
  ACCP("ACCP"),
  ACSC("ACSC"),
  ACSP("ACSP"),
  ACTC("ACTC"),
  ACWC("ACWC"),
  ACWP("ACWP"),
  RCVD("RCVD"),
  PDNG("PDNG"),
  RJCT("RJCT"),
  CANC("CANC"),
  ACFC("ACFC"),
  PATC("PATC"),
  PART("PART");

  private String value;

  TransactionStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static TransactionStatus fromValue(String text) {
    for (TransactionStatus b : TransactionStatus.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }

  public static class Adapter extends TypeAdapter<TransactionStatus> {
    @Override
    public void write(final JsonWriter jsonWriter, final TransactionStatus enumeration) throws IOException {
      jsonWriter.value(enumeration.getValue());
    }

    @Override
    public TransactionStatus read(final JsonReader jsonReader) throws IOException {
      String value = jsonReader.nextString();
      return TransactionStatus.fromValue(String.valueOf(value));
    }
  }
}
