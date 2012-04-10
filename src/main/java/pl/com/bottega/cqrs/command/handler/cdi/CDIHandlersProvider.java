package pl.com.bottega.cqrs.command.handler.cdi;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import pl.com.bottega.cqrs.command.handler.CommandHandler;
import pl.com.bottega.cqrs.command.impl.RunEnvironment.HandlersProvider;

@Singleton
// Registers handlers only on context initialization
public class CDIHandlersProvider implements HandlersProvider {

	private BeanManager beanManager;
	private Map<Class<?>, String> handlers = new HashMap<Class<?>, String>();

	protected BeanManager getBeanManager() {
		if (this.beanManager == null) {
			try {
				InitialContext initialContext = new InitialContext();
				this.beanManager = (BeanManager) initialContext.lookup("java:comp/BeanManager");
			}
			catch (NamingException e) {
				throw new IllegalStateException("unable to lookup bean manager", e);
			}
		}
		return beanManager;
	}

	@PostConstruct
	public void initHandlerMap()
	{
		BeanManager mgr = getBeanManager();
		Set<Bean<?>> allHandlers = mgr.getBeans(CommandHandler.class);
		for (Bean<?> bean : allHandlers)
		{
			Class<?> handlerClass = bean.getBeanClass();
			handlers.put(getHandledCommandType(handlerClass), bean.getName());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public CommandHandler<Object, Object> getHandler(Object command) 
	{
		String beanName = handlers.get(command.getClass());
		if (beanName == null)
		{
			throw new IllegalStateException("No command handler registered for command " + command.getClass().getName() + " Are you sure your handlers are @Named beans?");			
		}
		BeanManager beanManager = getBeanManager();
		Set<Bean<?>> beanset = beanManager.getBeans(beanName);

		if (beanset.size() > 1)
		{
			throw new IllegalStateException("Should never occur: name " + beanName + " has more than one registered CDI bean!");
		}
	
		Bean<?> bean = beanset.iterator().next();
		CreationalContext<?> creationalContext = beanManager.createCreationalContext(bean);
		
        Object reference = beanManager.getReference(bean, CommandHandler.class, creationalContext);
		return (CommandHandler<Object,Object>)reference;
	}

	private Class<?> getHandledCommandType(Class<?> clazz) {
		Type[] genericInterfaces = clazz.getGenericInterfaces();
		ParameterizedType type = findByRawType(genericInterfaces, CommandHandler.class);
		return (Class<?>) type.getActualTypeArguments()[0];
	}

	private ParameterizedType findByRawType(Type[] genericInterfaces, Class<?> expectedRawType) {
		for (Type type : genericInterfaces) {
			if (type instanceof ParameterizedType) {
				ParameterizedType parametrized = (ParameterizedType) type;
				if (expectedRawType.equals(parametrized.getRawType())) {
					return parametrized;
				}
			}
		}
		throw new RuntimeException();
	}
}
