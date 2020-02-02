package com.future.demo.elasticsearch.rest.high.level.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

/**
 *
 */
public class InitializationTests {
    /**
     *
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        RestClientBuilder builder = RestClient.builder(new HttpHost(Config.Host, Config.Port, "http"));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        client.close();
    }
}
