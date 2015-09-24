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

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * registration of all supported tags and parsers in jetty namespace
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
