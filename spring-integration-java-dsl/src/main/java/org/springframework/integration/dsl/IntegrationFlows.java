/*
 * Copyright 2014 the original author or authors.
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

package org.springframework.integration.dsl;

import org.springframework.integration.config.SourcePollingChannelAdapterFactoryBean;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.channel.MessageChannelSpec;
import org.springframework.integration.dsl.support.EndpointConfigurer;
import org.springframework.messaging.MessageChannel;

/**
 * @author Artem Bilan
 */
public final class IntegrationFlows {

	public static IntegrationFlowBuilder from(MessageChannel messageChannel) {
		return new IntegrationFlowBuilder().channel(messageChannel);
	}

	public static IntegrationFlowBuilder from(MessageChannelSpec<?, ?> messageChannelSpec) {
		return from(messageChannelSpec.get());
	}

	public static IntegrationFlowBuilder from(MessageSource<?> messageSource) {
		return from(messageSource, null);
	}

	public static IntegrationFlowBuilder from(MessageSource<?> messageSource, EndpointConfigurer<SourcePollingChannelAdapterSpec> endpointConfigurer) {
		SourcePollingChannelAdapterSpec spec = new SourcePollingChannelAdapterSpec(messageSource);
		if (endpointConfigurer != null) {
			endpointConfigurer.configure(spec);
		}
		SourcePollingChannelAdapterFactoryBean sourcePollingChannelAdapterFactoryBean = spec.get().getT1();
		return new IntegrationFlowBuilder().addComponent(sourcePollingChannelAdapterFactoryBean).currentComponent(sourcePollingChannelAdapterFactoryBean);
	}

	/*public static IntegrationFlowBuilder from(AbstractEndpoint endpoint) {
		return new IntegrationFlowBuilder();
	}*/

	private IntegrationFlows() {
	}

}
