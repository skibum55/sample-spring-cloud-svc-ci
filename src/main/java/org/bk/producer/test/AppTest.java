package org.bk.producer.test;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.web.client.RestTemplate;

/**
 * Integration test for webapp.
 */
public class AppTest extends Assert {


    @Test
    @Category(SmokeTest.class)
    public void smoke() throws Exception {
        testApp(20*1000);
    }

    @Test
    @Category(AcceptanceTest.class)
    public void acceptance() throws Exception {
        testApp(60*1000);
    }


    public void testApp(int duration) throws Exception {
        URI app = getSUT();
        RestTemplate restTemplate = new RestTemplate();
        String contents = restTemplate.postForObject(app, "test", String.class);
        
        assertTrue(contents.contains("Hello!"));

        // this is supposed to be an integration test,
        // let's take some time. We want this to be longer than the build for sure.
        Thread.sleep(duration);
    }

    private URI getSUT() throws Exception {
        String url = System.getProperty("url");
        assertTrue("Subject under test should be passed in via -Durl=...", url!=null);
        return new URI(url);
    }
}
