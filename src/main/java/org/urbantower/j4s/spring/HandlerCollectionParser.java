package org.urbantower.j4s.spring;

import org.eclipse.jetty.server.handler.HandlerCollection;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

/**
 * Created by z.vrabel on 18. 9. 2015.
 */
public class HandlerCollectionParser extends AbstractBeanDefinitionParser {

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(HandlerCollection.class);

        List<Element> handlerElms = DomUtils.getChildElements(element);
        List<Object> handlerDefs = new ManagedList<>();
        for (Element elm : handlerElms) {
            if ("handler".equals(elm.getLocalName())) {
                RuntimeBeanReference reference = new RuntimeBeanReference(elm.getAttribute("ref"));
                handlerDefs.add(reference);
            } else {
                BeanDefinition handlerDef = parserContext.getDelegate().parseCustomElement(elm, builder.getBeanDefinition());
                handlerDefs.add(handlerDef);
            }
        }

        builder.addPropertyValue("handlers", handlerDefs);
        return builder.getBeanDefinition();
    }
}
