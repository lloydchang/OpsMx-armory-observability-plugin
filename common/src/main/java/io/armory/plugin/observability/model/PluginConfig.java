/*
 * Copyright 2020 Armory, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.armory.plugin.observability.model;

import lombok.Data;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Data
@Configuration
@ConfigurationProperties("spinnaker.extensibility.plugins.armory.observability-plugin.config")
public class PluginConfig extends WebSecurityConfigurerAdapter {
    PluginMetricsConfig metrics = new PluginMetricsConfig();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (metrics.getPrometheus().isEnabled()) {
            http.requestMatcher(EndpointRequest.to("aop-prometheus")).authorizeRequests((requests) ->
                    requests.anyRequest().permitAll());
        }
    }

}
