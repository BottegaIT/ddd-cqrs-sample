package pl.com.bottega.ddd.sagas;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.com.bottega.ddd.domain.DomainEventPublisher;
import pl.com.bottega.ddd.infrastructure.events.impl.CDIEventPublisher;
import pl.com.bottega.ddd.infrastructure.events.impl.CDIEventsIntializer;
import pl.com.bottega.ddd.infrastructure.sagas.impl.CDISagaRegistry;
import pl.com.bottega.ddd.infrastructure.sagas.impl.SimpleSagaEngine;

@RunWith(Arquillian.class)
public class SagaIT {

    @Inject
    private DomainEventPublisher publisher;

    @Inject
    private SagaSpy spy;
    
    @Inject
    private SimpleSagaManager loader;

    @Deployment
	public static Archive<?> createTestArchive()
	{
		return ShrinkWrap.create(JavaArchive.class, SagaIT.class.getName() + ".jar").addPackages(false,
				SimpleSaga.class.getPackage()).addClasses(
	
				SagaSpy.class, CDIEventPublisher.class, SimpleSaga.class, SagaInstance.class, SimpleSagaData.class,
				SimpleSagaEngine.class, CDISagaRegistry.class, CDIEventsIntializer.class)
				.addAsManifestResource(new ByteArrayAsset("<beans />".getBytes()), ArchivePaths.create("beans.xml"))
				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml");
	}
    
    @Before
    public void prepare()
    {
    	// This would be unnecessary if there existed a simple way to reset container state between tests
    	spy.reset();
    	loader.reset();
    }
    /**
     * testing {@link SimpleSaga}
     */
    @Test
    public void shouldRunSimpleTwoStepSaga() throws Exception {
        // when
        publisher.publish(new SampleDomainEvent(1L));
        publisher.publish(new AnotherDomainEvent(1L, "data"));
        // then
        assertEquals(1, spy.getSampleEventHandledCount());
        assertEquals(1, spy.getAnotherEventHandledCount());
        assertEquals(1, spy.getSagaCompletedCount());
    }

    @Test
    public void shouldNotCompleteSameSagaTwice() throws Exception {
        // when
        publisher.publish(new SampleDomainEvent(1L));
        publisher.publish(new AnotherDomainEvent(1L, "data"));
        publisher.publish(new SampleDomainEvent(1L));
        // then
        assertEquals(2, spy.getSampleEventHandledCount());
        assertEquals(1, spy.getAnotherEventHandledCount());
        assertEquals(1, spy.getSagaCompletedCount());
    }
}
