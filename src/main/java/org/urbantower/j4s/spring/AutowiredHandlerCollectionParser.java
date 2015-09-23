package org.urbantower.j4s.spring;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.urbantower.j4s.AutowiredHandlerCollection;

/**
 * Created by z.vrabel on 21. 9. 2015.
 */
public class AutowiredHandlerCollectionParser extends AbstractBeanDefinitionParser {
    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(AutowiredHandlerCollection.class);
        return builder.getBeanDefinition();
    }
}
