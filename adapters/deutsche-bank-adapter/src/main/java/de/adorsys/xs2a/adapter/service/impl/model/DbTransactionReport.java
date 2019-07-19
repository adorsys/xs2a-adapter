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

import java.util.List;

public class DbTransactionReport {
    private List<String> tppMessages;
    @JsonProperty("_links")
    private DbLinks links;

    public List<String> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<String> tppMessages) {
        this.tppMessages = tppMessages;
    }

    public DbLinks getLinks() {
        return links;
    }

    public void setLinks(DbLinks links) {
        this.links = links;
    }
}
