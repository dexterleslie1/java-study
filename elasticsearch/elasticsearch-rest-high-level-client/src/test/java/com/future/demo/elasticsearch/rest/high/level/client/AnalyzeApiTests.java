package com.future.demo.elasticsearch.rest.high.level.client;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.util.concurrent.CountDown;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class AnalyzeApiTests extends AbstractTestSupport{
    /**
     *
     */
    @Test
    public void analyzeSynchronous() throws IOException {
        AnalyzeRequest request = new AnalyzeRequest();
        request.text("Some text to analyze", "Some more text to analyze");
        request.analyzer("standard");
        AnalyzeResponse response = client.indices().analyze(request, RequestOptions.DEFAULT);
        List<AnalyzeResponse.AnalyzeToken> analyzeTokenList = response.getTokens();
        Assert.assertTrue(analyzeTokenList.size()>0);
    }

    /**
     *
     * @throws InterruptedException
     * @throws TimeoutException
     */
    @Test
    public void analyzeAsynchronous() throws InterruptedException, TimeoutException {
        AnalyzeRequest request = new AnalyzeRequest();
        request.text("Some text to analyze", "Some more text to analyze");
        request.analyzer("standard");
        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        AtomicInteger atomicInteger = new AtomicInteger();
        client.indices().analyzeAsync(request, RequestOptions.DEFAULT, new ActionListener<AnalyzeResponse>() {
            @Override
            public void onResponse(AnalyzeResponse analyzeTokens) {
                countDownLatch1.countDown();
            }

            @Override
            public void onFailure(Exception e) {
                atomicInteger.incrementAndGet();
            }
        });

        if(!countDownLatch1.await(1, TimeUnit.SECONDS)) {
            throw new TimeoutException();
        }
        Assert.assertEquals(0, atomicInteger.get());
    }
}
