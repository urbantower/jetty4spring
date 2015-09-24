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
package org.urbantower.j4s.example.springmvc;

import junit.framework.Assert;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.IOException;

@ContextConfiguration(locations = {"classpath:spring-mvc/spring-mvc-test.xml"})
public class SpringMvcTest extends AbstractTestNGSpringContextTests {

    CloseableHttpClient httpclient = HttpClients.createDefault();

    @Test
    public void isJettyServerRunning() throws InterruptedException, IOException {
        CloseableHttpResponse response = httpclient.execute(new HttpGet("http://localhost:9091/webapp/helloworld"));
        String body = EntityUtils.toString(response.getEntity());
        Assert.assertTrue(body.contains("Hello World!"));
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mvc/spring-mvc-test.xml");
    }

}
