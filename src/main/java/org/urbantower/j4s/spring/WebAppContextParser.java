package org.urbantower.j4s.spring;

import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.urbantower.j4s.SpringWebAppContext;

import java.net.URL;

/**
 * Created by z.vrabel on 21. 9. 2015.
 */
public class WebAppContextParser extends AbstractBeanDefinitionParser {

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(SpringWebAppContext.class);
        builder.addPropertyValue("contextPath", element.getAttribute("context-path"));

        if (element.hasAttribute("war")) {
            builder.addPropertyValue("war", element.getAttribute("war"));
        } else {
            builder.addPropertyValue("descriptor", element.getAttribute("descriptor"));
            builder.addPropertyValue("resourceBase", element.getAttribute("resource-base"));
        }

        return builder.getBeanDefinition();
    }

}
