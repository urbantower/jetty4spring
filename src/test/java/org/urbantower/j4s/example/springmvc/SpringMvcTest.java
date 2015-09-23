package org.urbantower.j4s.example.springmvc;

import junit.framework.Assert;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
}
