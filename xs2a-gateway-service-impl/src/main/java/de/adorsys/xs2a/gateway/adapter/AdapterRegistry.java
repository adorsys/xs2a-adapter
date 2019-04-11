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

package de.adorsys.xs2a.gateway.adapter;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class AdapterRegistry {
    private static final AdapterRegistry instance = new AdapterRegistry();
    private ServiceLoader<AdapterProvider> loader;

    private AdapterRegistry() {
        loader = ServiceLoader.load(AdapterProvider.class);
    }

    public static AdapterRegistry getInstance() {
        return instance;
    }

    public AdapterManager getAdapterManager(String bankCode) {
        AdapterProvider provider = StreamSupport.stream(loader.spliterator(), false)
                                           .filter(a -> a.getBankCode().equalsIgnoreCase(bankCode))
                                           .findFirst().orElseThrow(() -> new BankNotSupportedException(bankCode));
        return provider.getManager();
    }
}