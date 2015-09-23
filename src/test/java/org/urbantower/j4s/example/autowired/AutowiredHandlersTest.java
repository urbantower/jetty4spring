package org.urbantower.j4s.example.autowired;

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

/**
 * Created by z.vrabel on 21. 9. 2015.
 */
@ContextConfiguration(locations = {"classpath:autowired/autowired-test.xml"})
public class AutowiredHandlersTest extends AbstractTestNGSpringContextTests {

    CloseableHttpClient httpclient = HttpClients.createDefault();

    @Test
    public void isJettyServerRunning() throws InterruptedException, IOException {
        HttpGet request = new HttpGet("http://localhost:9093/test/servlet");
        CloseableHttpResponse response = httpclient.execute(request);
        String body = EntityUtils.toString(response.getEntity());
        Assert.assertEquals(body, "Hello Servlet");
    }
}
