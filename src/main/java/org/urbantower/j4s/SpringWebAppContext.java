package org.urbantower.j4s;

import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * Created by z.vrabel on 21. 9. 2015.
 */
public class SpringWebAppContext extends WebAppContext implements ApplicationContextAware {

    private ApplicationContext context;
    private String temporaryDescriptor;
    private String temporaryResourceBase;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        this.setDescriptor(temporaryDescriptor);
        this.setResourceBase(temporaryResourceBase);
    }


    @Override
    public void setDescriptor(String descriptor) {
        try {
            if (context != null) {
                Resource resource = context.getResource(descriptor);
                super.setDescriptor(resource.getURL().toString());
            } else {
                temporaryDescriptor = descriptor;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setResourceBase(String resourceBase) {
        try {
            if (context != null) {
                Resource resource = context.getResource(resourceBase);
                super.setResourceBase(resource.getURL().toString());
            } else {
                temporaryResourceBase = resourceBase;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
