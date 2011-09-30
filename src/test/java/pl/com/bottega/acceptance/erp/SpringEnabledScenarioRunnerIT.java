package pl.com.bottega.acceptance.erp;

import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingPaths;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.junit.spring.SpringAnnotatedPathRunner;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.junit.runner.RunWith;

import pl.com.bottega.acceptance.erp.SpringEnabledScenarioRunnerIT.MyReportBuilder;

@RunWith(SpringAnnotatedPathRunner.class)
@UsingPaths(searchIn = "src/test/resources")
@Configure(storyReporterBuilder = MyReportBuilder.class)
@UsingEmbedder
@UsingSpring(resources = "classpath:/stepsConfiguration.xml")
public class SpringEnabledScenarioRunnerIT {

    public static class MyReportBuilder extends StoryReporterBuilder {
        public MyReportBuilder() {
            this.withFormats(org.jbehave.core.reporters.Format.CONSOLE);
        }
    }
}