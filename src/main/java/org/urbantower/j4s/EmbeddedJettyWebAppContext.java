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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

/**
 * This class wrapping the regular ApplicationContext and turn it
 * to WebApplicationContext.
 */
class EmbeddedJettyWebAppContext implements WebApplicationContext {

	/**
	 * regular simple spring context application
	 */
	private final ApplicationContext context;

	/**
	 * servlet context filled by {@link }
	 */
	private ServletContext servletContext;

	/**
	 * Constructor
	 */
	public EmbeddedJettyWebAppContext(ApplicationContext context) {
		this.context = context;
	}


	/**
	 * Setter is called during context initialization
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}


	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public String getId() {
		return context.getId();
	}

	@Override
	public String getApplicationName() {
		return context.getApplicationName();
	}

	@Override
	public String getDisplayName() {
		return context.getDisplayName();
	}

	@Override
	public long getStartupDate() {
		return context.getStartupDate();
	}

	@Override
	public ApplicationContext getParent() {
		return context;
	}

	@Override
	public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
		return context.getAutowireCapableBeanFactory();
	}

	@Override
	public void publishEvent(ApplicationEvent event) {
		context.publishEvent(event);
	}

	@Override
	public Environment getEnvironment() {
		return context.getEnvironment();
	}

	@Override
	public BeanFactory getParentBeanFactory() {
		return context.getParentBeanFactory();
	}

	@Override
	public boolean containsLocalBean(String s) {
		return context.containsLocalBean(s);
	}

	@Override
	public boolean containsBeanDefinition(String s) {
		return context.containsBeanDefinition(s);
	}

	@Override
	public int getBeanDefinitionCount() {
		return context.getBeanDefinitionCount();
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return context.getBeanDefinitionNames();
	}

	@Override
	public String[] getBeanNamesForType(Class<?> aClass) {
		return context.getBeanNamesForType(aClass);
	}

	@Override
	public String[] getBeanNamesForType(Class<?> aClass, boolean b, boolean b2) {
		return context.getBeanNamesForType(aClass, b, b2);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> tClass) throws BeansException {
		return context.getBeansOfType(tClass);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> tClass, boolean b, boolean b2) throws BeansException {
		return context.getBeansOfType(tClass, b, b2);
	}

	@Override
	public String[] getBeanNamesForAnnotation(Class<? extends Annotation> aClass) {
		return context.getBeanNamesForAnnotation(aClass);
	}

	@Override
	public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> aClass) throws BeansException {
		return context.getBeansWithAnnotation(aClass);
	}

	@Override
	public <A extends Annotation> A findAnnotationOnBean(String s, Class<A> aClass) {
		return context.findAnnotationOnBean(s, aClass);
	}

	@Override
	public Object getBean(String s) throws BeansException {
		return context.getBean(s);
	}

	@Override
	public <T> T getBean(String s, Class<T> tClass) throws BeansException {
		return context.getBean(s, tClass);
	}

	@Override
	public <T> T getBean(Class<T> tClass) throws BeansException {
		return context.getBean(tClass);
	}

	@Override
	public Object getBean(String s, Object... objects) throws BeansException {
		return context.getBean(s, objects);
	}

	@Override
	public <T> T getBean(Class<T> aClass, Object... objects) throws BeansException {
		return context.getBean(aClass, objects);
	}

	@Override
	public boolean containsBean(String s) {
		return context.containsBean(s);
	}

	@Override
	public boolean isSingleton(String s) throws NoSuchBeanDefinitionException {
		return context.isSingleton(s);
	}

	@Override
	public boolean isPrototype(String s) throws NoSuchBeanDefinitionException {
		return context.isPrototype(s);
	}

	@Override
	public boolean isTypeMatch(String s, Class<?> aClass) throws NoSuchBeanDefinitionException {
		return context.isTypeMatch(s, aClass);
	}

	@Override
	public Class<?> getType(String s) throws NoSuchBeanDefinitionException {
		return context.getType(s);
	}

	@Override
	public String[] getAliases(String s) {
		return context.getAliases(s);
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return context.getMessage(code, args, defaultMessage, locale);
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		return context.getMessage(code, args, locale);
	}

	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		return context.getMessage(resolvable, locale);
	}

	@Override
	public Resource[] getResources(String s) throws IOException {
		return context.getResources(s);
	}

	@Override
	public Resource getResource(String s) {
		return context.getResource(s);
	}

	@Override
	public ClassLoader getClassLoader() {
		return context.getClassLoader();
	}
}
