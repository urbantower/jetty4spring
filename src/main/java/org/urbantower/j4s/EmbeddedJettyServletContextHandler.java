package org.urbantower.j4s;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * This is the customized servlet context handler that ensure the correct
 * parent application context for child web contexts.
 *
 * This solution is little bit messy but ensure wiring of beans from parent
 * to web child contexts. Without this the web context cannot see the beans
 * in parent application context.
 */
public class EmbeddedJettyServletContextHandler extends ServletContextHandler implements ApplicationContextAware {
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		addEventListener(new EmbeddedJettyContexLoaderListener(applicationContext));
	}
}
