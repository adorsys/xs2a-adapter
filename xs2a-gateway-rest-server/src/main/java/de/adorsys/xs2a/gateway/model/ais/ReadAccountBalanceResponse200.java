/*
 * Copyright 2018-2019 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.gateway.model.ais;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Body of the response for a successful read balance for an account request.
 */
@ApiModel(description = "Body of the response for a successful read balance for an account request.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-01-11T12:48:04.675377+02:00[Europe/Kiev]")

public class ReadAccountBalanceResponse200 {
  @JsonProperty("account")
  private AccountReferenceTO account = null;

  @JsonProperty("balances")
  private BalanceList balances = null;

  public ReadAccountBalanceResponse200 account(AccountReferenceTO account) {
    this.account = account;
    return this;
  }

  /**
   * Get account
   * @return account
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AccountReferenceTO getAccount() {
    return account;
  }

  public void setAccount(AccountReferenceTO account) {
    this.account = account;
  }

  public ReadAccountBalanceResponse200 balances(BalanceList balances) {
    this.balances = balances;
    return this;
  }

  /**
   * Get balances
   * @return balances
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public BalanceList getBalances() {
    return balances;
  }

  public void setBalances(BalanceList balances) {
    this.balances = balances;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReadAccountBalanceResponse200 readAccountBalanceResponse200 = (ReadAccountBalanceResponse200) o;
    return Objects.equals(this.account, readAccountBalanceResponse200.account) &&
        Objects.equals(this.balances, readAccountBalanceResponse200.balances);
  }

  @Override
  public int hashCode() {
    return Objects.hash(account, balances);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReadAccountBalanceResponse200 {\n");
    
    sb.append("    account: ").append(toIndentedString(account)).append("\n");
    sb.append("    balances: ").append(toIndentedString(balances)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

