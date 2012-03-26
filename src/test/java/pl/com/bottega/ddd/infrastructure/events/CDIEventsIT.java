package pl.com.bottega.ddd.infrastructure.events;

import javax.inject.Inject;

import junit.framework.Assert;

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

import pl.com.bottega.ddd.application.ApplicationEventPublisher;
import pl.com.bottega.ddd.domain.DomainEventPublisher;
import pl.com.bottega.ddd.infrastructure.events.impl.CDIEventsIntializer;

/**
 * 
 * Integration test for validation of correct event registering and handling routines in JEE container.
 */

@RunWith(Arquillian.class)
public class CDIEventsIT {

    @Deployment
	public static Archive<?> createTestArchive()
	{
		return ShrinkWrap.create(JavaArchive.class, CDIEventsIT.class.getName() + ".jar").addPackages(false,
				CDIEventsIntializer.class.getPackage()).addPackage(SomeSynchronousDomainEventListener.class.getPackage()).addClasses(
				EventListener.class, EventListeners.class)
				.addAsManifestResource(new ByteArrayAsset("<beans />".getBytes()), ArchivePaths.create("beans.xml"))
				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml");
	}

    @Inject
    DomainEventPublisher domainPublisher;
    @Inject
    ApplicationEventPublisher appEventPublisher;
    @Inject
    SomeSynchronousDomainEventListener synchronousDomainEventListener;
    @Inject
    AllDomainEventSynchronousListener listenerForAllDomainEvents;
    @Inject
    AllEventSynchronousListener listenerForAnyEvent;
    @Inject
    SerializableEventsListener serializableEventListener;
    
    @Before
    public void prepare()
    {
    	synchronousDomainEventListener.reset();
    	listenerForAllDomainEvents.reset();
    	listenerForAnyEvent.reset();
    	serializableEventListener.reset();
    }
	@Test
	public void synchronousListenerHandlesConcreteEventType() 
	{
		// when
		domainPublisher.publish(new SomeTestDomainEvent());
		
		// then
		Assert.assertTrue(synchronousDomainEventListener.hasHandledNumberOfEvents(1));
		Assert.assertTrue(listenerForAllDomainEvents.hasHandledNumberOfEvents(1));
		Assert.assertTrue(listenerForAnyEvent.hasHandledNumberOfEvents(1));
	}
	
	@Test
	public void ApplicationEventNotCapturedByDomainEventHandlers() 
	{
		// when
		appEventPublisher.publish(new TestApplicationEvent());
		
		// then
		Assert.assertTrue(synchronousDomainEventListener.hasHandledNumberOfEvents(0));
		Assert.assertTrue(listenerForAllDomainEvents.hasHandledNumberOfEvents(0));
		Assert.assertTrue(listenerForAnyEvent.hasHandledNumberOfEvents(1));
		Assert.assertTrue(serializableEventListener.hasHandledNumberOfEvents(1));
	}
	
}
