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

import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.w3c.dom.Element;
import org.urbantower.j4s.EmbeddedJettyServletContextHandler;

import javax.servlet.Servlet;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is parsing  'servlet-context', 'servlet' and 'dispatcher-servlet' tags and
 * creates bean definition of {EmbeddedJettyServletContextHandler} which is
 * extended ServletContextHandler. This class is also handling the servlet registration
 * etc.
 *
 * @see EmbeddedJettyServletContextHandler
 * @see org.eclipse.jetty.servlet.ServletContextHandler
 *
 */
public class ServletContextHandlerParser extends AbstractBeanDefinitionParser {

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(EmbeddedJettyServletContextHandler.class)
                .addPropertyValue("sessionHandler", new SessionHandler())
                .addPropertyValue("contextPath", element.getAttribute("context-path"))
                .addPropertyValue("servletHandler", createServletHandler(element));

        return builder.getBeanDefinition();
    }


    /**
     * creates servlet handler
     */
    private ServletHandler createServletHandler(Element element) {
        ServletHandler servletHandler = new ServletHandler();

        //processing 'servlet' nodes
        List<Element> servletElms = DomUtils.getChildElementsByTagName(element, "servlet");
        for (Element servletElm : servletElms) {
            registerServlet(servletElm, servletHandler);
        }

        //processing 'dispatcher-servlet' nodes
        List<Element> dispatcherElms = DomUtils.getChildElementsByTagName(element, "servlet-dispatcher");
        for (Element dispatcherElm : dispatcherElms) {
            registerDispatcherServlet(dispatcherElm, servletHandler);
        }

        return servletHandler;
    }


    /**
     * method parse 'dispatcher-servlet' and register it into handler
     */
    private void registerDispatcherServlet(Element dispatcherElm, ServletHandler handler) {
        //get name
        String servletName = getServletName(dispatcherElm, DispatcherServlet.class);
        String contextConfigLocation = dispatcherElm.getAttribute("context-config-location");

        //register servlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setContextConfigLocation(contextConfigLocation);
        ServletHolder holder = new ServletHolder(servletName, dispatcherServlet);
        handler.addServlet(holder);


        //register mappings
        ServletMapping mapping = createMapping(dispatcherElm, handler, servletName);
        try {
            handler.addServletMapping(mapping);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * method parse 'servlet' and register it into handler
     */
    private void registerServlet(Element servletElm, ServletHandler handler) {
        try {
            //get servlet
            String servletClassName = servletElm.getAttribute("class");
            Class<? extends Servlet> servlerClass = (Class<? extends Servlet>) getClass().getClassLoader().loadClass(servletClassName);

            //get name
            String servletName = getServletName(servletElm, servlerClass);

            //register servlet
            handler.addServlet(new ServletHolder(servletName, servlerClass));

            //register mappings
            ServletMapping mapping = createMapping(servletElm, handler, servletName);
            handler.addServletMapping(mapping);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * method creates servlet mapping
     */
    private ServletMapping createMapping(Element servletElm, ServletHandler handler, String servletName) {
        List<String> paths = new LinkedList<>();
        if (servletElm.hasAttribute("url-pattern")) {
            paths.add(servletElm.getAttribute("url-pattern"));
        }

        List<Element> mappings = DomUtils.getChildElementsByTagName(servletElm, "url-pattern");
        for(Element m : mappings) {
            paths.add(m.getAttribute("url-pattern"));
        }

        if (paths.isEmpty()) {
            paths.add("/*");
        }

        ServletMapping mapping = new ServletMapping();
        mapping.setPathSpecs(paths.toArray(new String[]{}));
        mapping.setServletName(servletName);

        return mapping;
    }


    /**
     * method returns servlet's name (if it's not present, it's generated)
     */
    private String getServletName(Element servletElm, Class<? extends Servlet> servlerClass) {
        String servletName = servletElm.getAttribute("servlet-name");
        if (servletName == null || servletName.isEmpty()) {
            servletName = servlerClass.getName();
        }
        return servletName;
    }

}
