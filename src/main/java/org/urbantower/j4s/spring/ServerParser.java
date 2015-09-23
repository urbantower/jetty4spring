package org.urbantower.j4s.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.urbantower.j4s.SpringServer;


/**
 * Created by z.vrabel on 17. 9. 2015.
 */
public class ServerParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(SpringServer.class);

        //set port
        String port = element.getAttribute("http-port");
        builder.addPropertyValue("httpPort", Integer.parseInt(port));

        //parse & set handlers
        ManagedList<BeanDefinition> handlers = new ManagedList<>();

        if (element.hasAttribute("handler")) {
            String handlerRef = element.getAttribute("handler");
            builder.addPropertyValue("handler", new RuntimeBeanReference(handlerRef));
        } else {
            Element handlerElm = DomUtils.getChildElements(element).get(0);
            BeanDefinition handlerDef = parserContext.getDelegate().parseCustomElement(handlerElm, builder.getBeanDefinition());
            builder.addPropertyValue("handler", handlerDef);
        }

        //get id
        String id = parserContext.getReaderContext().generateBeanName(builder.getBeanDefinition());
        if (element.hasAttribute("id")) {
            id = element.getAttribute("id");
        }

        //get the thread-pool
        if (element.hasAttribute("thread-pool")) {
            builder.addConstructorArgValue(new RuntimeBeanReference(element.getAttribute("thread-pool")));
        }

        //register server def.
        parserContext.getRegistry().registerBeanDefinition(id, builder.getBeanDefinition());
        return builder.getBeanDefinition();
    }
}
