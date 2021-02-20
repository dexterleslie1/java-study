package com.future.demo.prometheus.client;

import io.prometheus.client.*;
import io.prometheus.client.exporter.BasicAuthHttpConnectionFactory;
import io.prometheus.client.exporter.PushGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ApiTests {
    @Value(value = "${push.gateway.url}")
    String pushgatewayUrl;
    @Value("${push.gateway.user}")
    String pushgatewayUser;
    @Value("${push.gateway.password}")
    String pushgatewayPassword;

    @Value("${local.server.port}")
    int serverPort;
    @Value("${spring.application.name}")
    String applicationName;

    @Test
    public void test() throws IOException, InterruptedException {
        String ip = Inet4Address.getLocalHost().getHostAddress();
        Random random = new Random();
        int maxLimit = 5000;
        int maxTotal = 10;
        // 模拟请求总数
        Counter counter = Counter.build()
                .name("prometheus_demo_request_total")
                .help("Prometheus demo request total.")
                .labelNames("url")
                .register();
        int randomInt = random.nextInt(maxTotal);
        counter.labels("/api/v1/test1").inc(randomInt);
        randomInt = random.nextInt(maxTotal);
        counter.labels("/api/v1/test2").inc(randomInt);

        // 模拟内存使用bytes
        Gauge gaugeMemoryUsage = Gauge.build().name("prometheus_demo_memory_usage_bytes")
                .help("Prometheus demo memory usage bytes.")
                .labelNames("type")
                .register();
        randomInt = random.nextInt(maxLimit);
        gaugeMemoryUsage.labels("direct").inc(randomInt);
        randomInt = random.nextInt(maxLimit);
        gaugeMemoryUsage.labels("heap").inc(randomInt);

        // 模拟api响应时间
        Summary summary = Summary.build().name("prometheus_demo_request_latency")
                .quantile(.3, .001)
                .quantile(.5, .001)
                .quantile(.8, .001)
                .help("Prometheus demo request latency.")
                .labelNames("url")
                .register();
        int maxSleepMilliseconds = 500;
        for(int i=0; i<50; i++) {
            Summary.Timer timer = summary.labels("/api/v1/test1").startTimer();

            int randomMilliseconds = random.nextInt(maxSleepMilliseconds);
            Thread.sleep(randomMilliseconds);

            timer.observeDuration();
        }
        for(int i=0; i<50; i++) {
            Summary.Timer timer = summary.labels("/api/v1/test2").startTimer();

            int randomMilliseconds = random.nextInt(maxSleepMilliseconds);
            Thread.sleep(randomMilliseconds);

            timer.observeDuration();
        }

        // 模拟api响应时间分布比例
        Histogram histogram = Histogram.build().name("prometheus_demo_request_latency_histogram")
                .buckets(0.1, 0.2, 0.5)
                .help("Prometheus demo request latency histogram")
                .labelNames("url")
                .register();
        for(int i=0; i<50; i++) {
            Histogram.Timer timer = histogram.labels("/api/v1/test1").startTimer();

            int randomMilliseconds = random.nextInt(maxSleepMilliseconds);
            Thread.sleep(randomMilliseconds);

            timer.observeDuration();
        }
        for(int i=0; i<50; i++) {
            Histogram.Timer timer = histogram.labels("/api/v1/test2").startTimer();

            int randomMilliseconds = random.nextInt(maxSleepMilliseconds);
            Thread.sleep(randomMilliseconds);

            timer.observeDuration();
        }

        PushGateway pushGateway = new PushGateway(this.pushgatewayUrl);
        if(!StringUtils.isEmpty(pushgatewayUser)) {
            pushGateway.setConnectionFactory(new BasicAuthHttpConnectionFactory(pushgatewayUser, pushgatewayPassword));
        }
        // 模拟微服务实例1
        String instance = ip+":"+serverPort;
        Map<String, String> groupingKey = new HashMap<>();
        groupingKey.put("application", applicationName);
        groupingKey.put("instance", instance);
        pushGateway.pushAdd(CollectorRegistry.defaultRegistry, "job1", groupingKey);

        randomInt = random.nextInt(maxTotal);
        counter.labels("/api/v1/test1").inc(randomInt);
        randomInt = random.nextInt(maxTotal);
        counter.labels("/api/v1/test2").inc(randomInt);
        randomInt = random.nextInt(maxLimit);
        gaugeMemoryUsage.labels("direct").inc(randomInt);
        randomInt = random.nextInt(maxLimit);
        gaugeMemoryUsage.labels("heap").inc(randomInt);

        for(int i=0; i<50; i++) {
            Summary.Timer timer = summary.labels("/api/v1/test1").startTimer();

            int randomMilliseconds = random.nextInt(maxSleepMilliseconds);
            Thread.sleep(randomMilliseconds);

            timer.observeDuration();
        }
        for(int i=0; i<50; i++) {
            Summary.Timer timer = summary.labels("/api/v1/test2").startTimer();

            int randomMilliseconds = random.nextInt(maxSleepMilliseconds);
            Thread.sleep(randomMilliseconds);

            timer.observeDuration();
        }

        for(int i=0; i<50; i++) {
            Histogram.Timer timer = histogram.labels("/api/v1/test1").startTimer();

            int randomMilliseconds = random.nextInt(maxSleepMilliseconds);
            Thread.sleep(randomMilliseconds);

            timer.observeDuration();
        }
        for(int i=0; i<50; i++) {
            Histogram.Timer timer = histogram.labels("/api/v1/test2").startTimer();

            int randomMilliseconds = random.nextInt(maxSleepMilliseconds);
            Thread.sleep(randomMilliseconds);

            timer.observeDuration();
        }

        // 模拟微服务实例2
        instance = ip+":"+(serverPort+1);
        groupingKey = new HashMap<>();
        groupingKey.put("application", applicationName);
        groupingKey.put("instance", instance);
        pushGateway.pushAdd(CollectorRegistry.defaultRegistry, "job1", groupingKey);
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void testDelete() throws IOException {
        Map<String, String> groupingKey = new HashMap<>();
        groupingKey.put("instance", "prometheus_demo_1");
        PushGateway pushGateway = new PushGateway(this.pushgatewayUrl);
        pushGateway.delete("job1", groupingKey);
    }
}
