package com.future.demo.prometheus.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.BasicAuthHttpConnectionFactory;
import io.prometheus.client.exporter.PushGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusProperties;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusPushGatewayManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import java.net.*;
import java.time.Duration;
import java.util.Map;

@Configuration
public class ConfigPushGateway {
    @Value("${management.metrics.export.prometheus.pushgateway.user}")
    String pushGatewayUser;
    @Value("${management.metrics.export.prometheus.pushgateway.password}")
    String pushGatewayPassword;
    @Value("${server.ip}")
    String serverIp;
    @Value("${server.port}")
    int serverPort;

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(@Value("${spring.application.name}") String applicationName) {
        String instance = serverIp+":"+serverPort;
        return registry -> registry.config().commonTags("application", applicationName, "instance", instance);
    }

    @ConditionalOnProperty(prefix = "management.metrics.export.prometheus.pushgateway", value = "enabled", havingValue = "true")
    @Bean
    @Primary
    public PrometheusPushGatewayManager prometheusPushGatewayManager(CollectorRegistry collectorRegistry,
                                                                     PrometheusProperties prometheusProperties,
                                                                     @Value("${spring.application.name}") String applicationName) throws MalformedURLException {
        PrometheusProperties.Pushgateway properties = prometheusProperties.getPushgateway();
        Duration pushRate = properties.getPushRate();
        Map<String, String> groupingKey = properties.getGroupingKey();
        PrometheusPushGatewayManager.ShutdownOperation shutdownOperation = properties.getShutdownOperation();
        PushGateway pushGateway = new PushGateway(new URL(properties.getBaseUrl()));
        if(!StringUtils.isEmpty(pushGatewayUser)) {
            pushGateway.setConnectionFactory(new BasicAuthHttpConnectionFactory(pushGatewayUser, pushGatewayPassword));
        }
        return new PrometheusPushGatewayManager(pushGateway, collectorRegistry, pushRate, applicationName, groupingKey, shutdownOperation);
    }
}
