/*
 * Copyright 2015 floragunn UG (haftungsbeschränkt)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.floragunn.searchguard.ssl;

import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.settings.Settings;

import com.floragunn.searchguard.ssl.util.SSLConfigConstants;

public final class SearchGuardSSLModule extends AbstractModule {

    private final SearchGuardKeyStore sgks;

    public SearchGuardSSLModule(final Settings settings) {
        super();
        if(settings.getAsBoolean(SSLConfigConstants.SEARCHGUARD_SSL_CLIENT_ENABLE_EXTERNAL_CONTEXT, false)) {
            this.sgks = new ExternalSearchGuardKeyStore(settings);
        } else {
            this.sgks = new DefaultSearchGuardKeyStore(settings);
        }
    }

    @Override
    protected void configure() {
        bind(SearchGuardSSLSettingsFilter.class).asEagerSingleton();
        bind(SearchGuardKeyStore.class).toInstance(sgks);
    }
}
