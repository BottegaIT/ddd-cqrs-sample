/*
 * Copyright 2011-2012 the original author or authors.
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
 */
package pl.com.bottega.acceptance.commons;

import java.io.IOException;
import java.util.Set;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class AgentComponentFilter implements TypeFilter {

    private static enum Agent {
        BROWSER(BrowserAgent.class), REMOTE(RemoteAgent.class);

        private final String annotationClass;

        private Agent(Class<?> annotationClass) {
            this.annotationClass = annotationClass.getName();
        }
    }

    private static final Agent DEFAULT = Agent.BROWSER;
    private static final String SYSTEM_PROPERTY = "useAgent";

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        String agentAnnotation = getSelectedAgent().annotationClass;
        Set<String> presentAnnotations = metadataReader.getAnnotationMetadata().getAnnotationTypes();
        return presentAnnotations.contains(agentAnnotation);
    }

    private Agent getSelectedAgent() {
        String agent = System.getProperty(SYSTEM_PROPERTY);
        try {
            return Agent.valueOf(agent.toUpperCase());
        } catch (Exception e) {
            return DEFAULT;
        }
    }
}
