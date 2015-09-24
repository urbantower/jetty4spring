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
 * Parser for 'handlers' which create bean definition of {HandlerCollection}
 * @see HandlerCollection
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
