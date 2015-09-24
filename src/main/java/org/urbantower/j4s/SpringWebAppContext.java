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

import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * extended WebAppContext with Spring support
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
