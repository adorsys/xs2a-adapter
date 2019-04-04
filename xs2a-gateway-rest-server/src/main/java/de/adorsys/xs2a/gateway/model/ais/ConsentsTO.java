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
import java.time.LocalDate;
import java.util.Objects;

/**
 * Content of the body of a consent request. 
 */
@ApiModel(description = "Content of the body of a consent request. ")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-01-11T12:48:04.675377+02:00[Europe/Kiev]")

public class ConsentsTO {
  @JsonProperty("access")
  private AccountAccessTO access = null;

  @JsonProperty("recurringIndicator")
  private Boolean recurringIndicator = null;

  @JsonProperty("validUntil")
  private LocalDate validUntil = null;

  @JsonProperty("frequencyPerDay")
  private Integer frequencyPerDay = null;

  @JsonProperty("combinedServiceIndicator")
  private Boolean combinedServiceIndicator = null;

  public ConsentsTO access(AccountAccessTO access) {
    this.access = access;
    return this;
  }

  /**
   * Get access
   * @return access
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public AccountAccessTO getAccess() {
    return access;
  }

  public void setAccess(AccountAccessTO access) {
    this.access = access;
  }

  public ConsentsTO recurringIndicator(Boolean recurringIndicator) {
    this.recurringIndicator = recurringIndicator;
    return this;
  }

  /**
   * Get recurringIndicator
   * @return recurringIndicator
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean getRecurringIndicator() {
    return recurringIndicator;
  }

  public void setRecurringIndicator(Boolean recurringIndicator) {
    this.recurringIndicator = recurringIndicator;
  }

  public ConsentsTO validUntil(LocalDate validUntil) {
    this.validUntil = validUntil;
    return this;
  }

  /**
   * Get validUntil
   * @return validUntil
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public LocalDate getValidUntil() {
    return validUntil;
  }

  public void setValidUntil(LocalDate validUntil) {
    this.validUntil = validUntil;
  }

  public ConsentsTO frequencyPerDay(Integer frequencyPerDay) {
    this.frequencyPerDay = frequencyPerDay;
    return this;
  }

  /**
   * Get frequencyPerDay
   * @return frequencyPerDay
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getFrequencyPerDay() {
    return frequencyPerDay;
  }

  public void setFrequencyPerDay(Integer frequencyPerDay) {
    this.frequencyPerDay = frequencyPerDay;
  }

  public ConsentsTO combinedServiceIndicator(Boolean combinedServiceIndicator) {
    this.combinedServiceIndicator = combinedServiceIndicator;
    return this;
  }

  /**
   * If \"true\" indicates that a payment initiation service will be addressed in the same \"session\". 
   * @return combinedServiceIndicator
  **/
  @ApiModelProperty(required = true, value = "If \"true\" indicates that a payment initiation service will be addressed in the same \"session\". ")
  @NotNull


  public Boolean isCombinedServiceIndicator() {
    return combinedServiceIndicator;
  }

  public void setCombinedServiceIndicator(Boolean combinedServiceIndicator) {
    this.combinedServiceIndicator = combinedServiceIndicator;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConsentsTO consents = (ConsentsTO) o;
    return Objects.equals(this.access, consents.access) &&
        Objects.equals(this.recurringIndicator, consents.recurringIndicator) &&
        Objects.equals(this.validUntil, consents.validUntil) &&
        Objects.equals(this.frequencyPerDay, consents.frequencyPerDay) &&
        Objects.equals(this.combinedServiceIndicator, consents.combinedServiceIndicator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(access, recurringIndicator, validUntil, frequencyPerDay, combinedServiceIndicator);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsentsTO {\n");
    
    sb.append("    access: ").append(toIndentedString(access)).append("\n");
    sb.append("    recurringIndicator: ").append(toIndentedString(recurringIndicator)).append("\n");
    sb.append("    validUntil: ").append(toIndentedString(validUntil)).append("\n");
    sb.append("    frequencyPerDay: ").append(toIndentedString(frequencyPerDay)).append("\n");
    sb.append("    combinedServiceIndicator: ").append(toIndentedString(combinedServiceIndicator)).append("\n");
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

