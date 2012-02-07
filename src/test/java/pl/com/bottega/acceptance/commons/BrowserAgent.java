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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate classes as required in "Browser" agent profile.
 * 
 * Agents work as proxy that connects to a specific interface of the application
 * that can be tested. Examples of agents: web browser or a remote protocol
 * (like XML or JSon over HTTP, AMF, web services, etc.)
 * 
 * Technical comment: Spring components are created for classes with this
 * annotation thanks to AgentComponentFilter. If JBehave supported Spring 3.1
 * then a combination of @Component and @Profile would be much better.
 * 
 * @author Rafał Jamróz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BrowserAgent {
}
