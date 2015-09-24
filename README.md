# Jetty4Spring

This library helps you with embedding Jetty to Spring application.

## Motivation

Embedding web container (Jetty) into application is a new way how to create standalone and lightweight applications. When I decided embed the 
Jetty into one application (before amazing Spring Boot), I've bother with huge and hard-to-read XML. I decided to write a Spring namespace that
simplify these XML fragments and make them more readable. This library is a result of this decision.
 
## How to use

  If you're using Maven, add dependencies below into your project.
  
     <dependency>
            <groupId>org.urbantower</groupId>
            <artifactId>j4s</artifactId>
            <version>0.1.0</version>
     </dependency>
             
  Then you have to add `http://www.urbantower.io/schema/jetty` XML namespace into your Spring XML and you can define Jetty server:
  
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:jetty="http://www.urbantower.io/schema/jetty"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                 http://www.urbantower.io/schema/jetty http://www.urbantower.io/schema/jetty/jetty.xsd">     
      
          <jetty:server http-port="9090">
          </jetty:server>
            
      </beans>
      
  When you run your spring application with this XML and type localhost:9090 into your browser, you should see `No context on this server matched or 
  handled this request.` message powered by Jetty.
    
### Registering servlets
  
  To produce a response to a request, Jetty requires a handler. Jetty4Spring support you with several handlers in XML.
  Everything what you have to do is use the `servlet-context` tag that prepare servlet context handler.
  
    <jetty:server http-port="9090">
        <jetty:servlet-context context-path="/webapp">
            <jetty:servlet url-pattern="/servlet" class="org.urbantower.j4s.example.HelloWorldServlet" />
        </jetty:servlet-context>          
    </jetty:server>               
    
  When you type into your browser http://localhost:9090/webapp/servlet, then you should see HelloWorldServlet's response.  
  *Note:* you have to ensure the `spring-web` dependency in your project.
   
### Registering Spring's DispatcherServlet
   
  You can easily use Spring DispatcherServlet servlet with current application context as a root context inside MessageDispatcher's context.

    <jetty:server http-port="9090">
        <jetty:servlet-context context-path="/webapp">
            <jetty:servlet-dispatcher context-config-location="classpath:/spring-mvc/rest.xml" />
        </jetty:servlet-context>
    </jetty:server>
    
   The dispatcher servlet creates sub-context from `rest.xml` and servlet is binded to `http://localhost:9090/webapp/*`. Be carefull with 
   `url-pattern` in servlet-dispatcher. In this case you have to be familiar with `alwaysUseFullPath` otherwise URL mapping will fail in any case.
   
### Using context handler collections
   
   You might have multiple handlers for your server. Jetty for that purpose is using HandlerCollection. This collection is representing by 
   `<handlers>` tag in XML. See example below:
   
    <jetty:server http-port="9090">
        <jetty:handlers>
            <jetty:servlet-context context-path="/module1">
                ...
            </jetty:servlet-context>
            <jetty:servlet-context context-path="/module2">
                ...
            </jetty:servlet-context>
            <jetty:default-handler/>
        </jetty:handlers>
    </jetty:server>               
   
   There is also another kind of collection, that will look for all Handler implementations in your app. context and create collection:
   
    <jetty:server http-port="9090">
           <jetty:handlers>
                <jetty:autowired-handlers/>
                <jetty:default-handler/>
            </jetty:handlers>
    </jetty:server>
       
    <jetty:servlet-context context-path="/module1">
        ...
    </jetty:servlet-context>
    
    <jetty:servlet-context context-path="/module2">
        ...
    </jetty:servlet-context>
    
# Troubleshooting
    
 Have a question or new requirement? Need a help? Create an [issue](https://github.com/urbantower/jetty4spring/issues/new).
   
# Licencing
    
 Copyright (C) 2011 Zdenko Vrabel Licensed under the Apache License, Version 2.0