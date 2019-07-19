/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
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

package de.adorsys.xs2a.adapter.service.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.service.model.Link;

import java.util.List;

public class DbLinks {
    @JsonProperty("balances")
    private Link balance;
    @JsonProperty("download")
    private List<Link> downloads;
    private Link account;

    public Link getBalance() {
        return balance;
    }

    public void setBalance(Link balance) {
        this.balance = balance;
    }

    public List<Link> getDownloads() {
        return downloads;
    }

    public void setDownloads(List<Link> downloads) {
        this.downloads = downloads;
    }

    public Link getAccount() {
        return account;
    }

    public void setAccount(Link account) {
        this.account = account;
    }
}
