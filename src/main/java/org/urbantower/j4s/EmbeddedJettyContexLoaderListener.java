package org.urbantower.j4s;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * customized context loader listener that ignores 'applicationContex.xml'
 * and use the given application context as parent context.
 */
class EmbeddedJettyContexLoaderListener extends ContextLoaderListener {

	/**
	 * reffering to wrapped application context
	 */
	private final EmbeddedJettyWebAppContext context;

	/**
	 * Constructor for regular application context
	 */
	public EmbeddedJettyContexLoaderListener(ApplicationContext applicationContext) {
		this(new EmbeddedJettyWebAppContext(applicationContext));
	}


	/**
	 * this is hidden constructor, use the public one
	 */
	private EmbeddedJettyContexLoaderListener(EmbeddedJettyWebAppContext context) {
		super(context);
		this.context = context;
	}


	/**
	 * during context initialization is filled servlet context into webapp context
	 * wrapper
	 * @param event
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		context.setServletContext(event.getServletContext());
		super.contextInitialized(event);
	}

}
