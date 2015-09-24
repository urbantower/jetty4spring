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

import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.urbantower.j4s.SpringWebAppContext;

/**
 * Class parse 'webapp-context' and creates bean definition of {WebAppContext}.
 *
 * @see SpringWebAppContext
 * @see WebAppContext
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
