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
package org.urbantower.j4s;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.LinkedList;
import java.util.List;

/**
 * Extended Jetty Server class with Spring support.
 */
public class SpringServer extends Server implements InitializingBean, ApplicationContextAware {

    /// the 'http' port property
    private int httpPort;

    /// application context ref.
    private ApplicationContext context;

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public SpringServer() {
        super();
    }

    public SpringServer(ThreadPool pool) {
        super(pool);
    }

    /**
     * this method is called by Spring after construction
     * and start the jetty server.
     */
    void init() throws Exception {

        // setup connectors
        List<Connector> connectors = new LinkedList<>();
        if (httpPort > 0) {
            ServerConnector httpConnector = new ServerConnector(this);
            httpConnector.setPort(httpPort);
            connectors.add(httpConnector);
        }

        this.setConnectors(connectors.toArray(new Connector[]{}));

        //add default handler if no handler is present
        if (this.getHandler() == null) {
            this.setHandler(new DefaultHandler());
        }

        this.start();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
