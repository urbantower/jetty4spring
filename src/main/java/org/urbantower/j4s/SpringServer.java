package org.urbantower.j4s;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by z.vrabel on 11. 9. 2015.
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
