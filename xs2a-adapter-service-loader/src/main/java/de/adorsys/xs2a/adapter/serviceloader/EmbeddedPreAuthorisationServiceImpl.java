/*
 * Copyright 2018-2020 adorsys GmbH & Co KG
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

package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.EmbeddedPreAuthorisationService;
import de.adorsys.xs2a.adapter.api.model.EmbeddedPreAuthorisationRequest;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;

public class EmbeddedPreAuthorisationServiceImpl implements EmbeddedPreAuthorisationService {
    @Override
    public TokenResponse getToken(EmbeddedPreAuthorisationRequest request) {
        throw new UnsupportedOperationException("will be implemented in https://jira.adorsys.de/browse/XS2AAD-732");
    }
}
