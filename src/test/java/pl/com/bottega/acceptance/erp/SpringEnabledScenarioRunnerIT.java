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

import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingPaths;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.junit.spring.SpringAnnotatedPathRunner;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.junit.runner.RunWith;

import pl.com.bottega.acceptance.erp.SpringEnabledScenarioRunnerIT.MyReportBuilder;

@RunWith(SpringAnnotatedPathRunner.class)
@UsingPaths(searchIn = "src/test/resources")
@Configure(storyReporterBuilder = MyReportBuilder.class, pendingStepStrategy = FailingUponPendingStep.class)
@UsingEmbedder
@UsingSpring(resources = { "classpath:/stepsConfiguration.xml", "classpath:/rmiClientContext.xml" })
public class SpringEnabledScenarioRunnerIT {

    public static class MyReportBuilder extends StoryReporterBuilder {
        public MyReportBuilder() {
            withFailureTrace(true).withFormats(org.jbehave.core.reporters.Format.CONSOLE);
        }
    }
}