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
package pl.com.bottega.acceptance.erp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.jbehave.web.selenium.ContextView;
import org.jbehave.web.selenium.LocalFrameContextView;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumStepMonitor;
import org.jbehave.web.selenium.WebDriverProvider;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.google.common.util.concurrent.MoreExecutors;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public class SpringEnabledScenarioRunnerIT extends JUnitStories {

	private Configuration configuration;

	public SpringEnabledScenarioRunnerIT() {
		configuredEmbedder().useExecutorService(MoreExecutors.sameThreadExecutor());
		configuredEmbedder().useMetaFilters(Arrays.asList("-skip"));

		WebDriverProvider driverProvider = new PropertyWebDriverProvider();
		SeleniumContext context = new SeleniumContext();
		ContextView contextView = new LocalFrameContextView().sized(500, 100);

		Class<? extends Embeddable> embeddableClass = this.getClass();
		configuration = new SeleniumConfiguration()
		        .useSeleniumContext(context)
		        .useWebDriverProvider(driverProvider)
		        .useStepMonitor(new SeleniumStepMonitor(contextView, context, new SilentStepMonitor()))
		        .useStoryLoader(new LoadFromClasspath(embeddableClass))
		        .useStoryReporterBuilder(
		                new StoryReporterBuilder()
		                        .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
		                        .withDefaultFormats().withFormats(Format.CONSOLE, Format.TXT, Format.HTML, Format.XML));
	}

	@Override
	protected List<String> storyPaths() {
		return new StoryFinder().findPaths("src/test/resources", Arrays.asList("**/*.story"), new ArrayList<String>());
	}

	@Override
	public Configuration configuration() {
		return configuration;
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		ApplicationContext applicationContext = new GenericXmlApplicationContext("classpath:/stepsConfiguration.xml",
		        "classpath:/rmiClientContext.xml");
		return new SpringStepsFactory(configuration, applicationContext);
	}
}