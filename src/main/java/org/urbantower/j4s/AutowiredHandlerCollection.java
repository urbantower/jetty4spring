package org.urbantower.j4s;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Created by z.vrabel on 18. 9. 2015.
 */
public class AutowiredHandlerCollection extends HandlerCollection implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Handler> handlers = applicationContext.getBeansOfType(Handler.class);
        for (Handler h : handlers.values()) {
            if (h.getClass() != SpringServer.class) {
                this.addHandler(h);
            }
        }
    }

}
