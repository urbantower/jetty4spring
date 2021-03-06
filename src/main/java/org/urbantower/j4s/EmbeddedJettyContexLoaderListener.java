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

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * customized context loader listener that ignores 'applicationContex.xml'
 * and use the given application context as parent context.
 */
class EmbeddedJettyContexLoaderListener extends ContextLoaderListener {

	/**
	 * reffering to wrapped application context
	 */
	private final EmbeddedJettyWebAppContext context;

	/**
	 * Constructor for regular application context
	 */
	public EmbeddedJettyContexLoaderListener(ApplicationContext applicationContext) {
		this(new EmbeddedJettyWebAppContext(applicationContext));
	}


	/**
	 * this is hidden constructor, use the public one
	 */
	private EmbeddedJettyContexLoaderListener(EmbeddedJettyWebAppContext context) {
		super(context);
		this.context = context;
	}


	/**
	 * during context initialization is filled servlet context into webapp context
	 * wrapper
	 * @param event
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		context.setServletContext(event.getServletContext());
		super.contextInitialized(event);
	}

}
