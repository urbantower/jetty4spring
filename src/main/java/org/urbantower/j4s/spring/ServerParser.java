/**
 * Copyright 2015 vrabel.zdenko@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import org.w3c.dom.Node;

import java.util.List;


/**
 * Parse 'server' tag and create Jetty Server bean definition.
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
            List<Element> childs = DomUtils.getChildElements(element);
            if (childs != null && childs.size() > 0) {
                BeanDefinition handlerDef = parserContext.getDelegate().parseCustomElement(childs.get(0), builder.getBeanDefinition());
                builder.addPropertyValue("handler", handlerDef);
            }
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
