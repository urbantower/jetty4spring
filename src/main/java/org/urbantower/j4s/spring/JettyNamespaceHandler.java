package org.urbantower.j4s.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by z.vrabel on 24. 8. 2015.
 */
public class JettyNamespaceHandler extends NamespaceHandlerSupport  {
    @Override
    public void init() {
        registerBeanDefinitionParser("server",             new ServerParser());
        registerBeanDefinitionParser("handlers",           new HandlerCollectionParser());
        registerBeanDefinitionParser("default-handler",    new DefaultHandlerParser());
        registerBeanDefinitionParser("autowired-handlers", new AutowiredHandlerCollectionParser());
        registerBeanDefinitionParser("servlet-context",    new ServletContextHandlerParser());
        registerBeanDefinitionParser("webapp-context",     new WebAppContextParser());

    }
}
