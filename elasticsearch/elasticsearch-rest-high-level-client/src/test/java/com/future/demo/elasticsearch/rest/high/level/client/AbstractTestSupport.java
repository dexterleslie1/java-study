package com.future.demo.elasticsearch.rest.high.level.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

/**
 *
 */
public class AbstractTestSupport {
    public RestHighLevelClient client = null;

    @Before
    public void setup() {
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(Config.Host, Config.Port, "http")));
    }

    @After
    public void teardown() throws IOException {
        client.close();
    }
}
