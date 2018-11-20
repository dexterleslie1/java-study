package com.future.study.spring.large.scale.solution;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Dexterleslie.Chan
 */
public class KafkaTests {
    private final static Logger logger = LogManager.getLogger(KafkaTests.class);
    private final static String TOPIC = "test-topic-1";

    @Test
    public void testProducer() throws InterruptedException {
        Date date1 = new Date();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int j = 0; j < 100; j++) {
            final int j1 = j;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Properties props = new Properties();
                    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                            "192.168.1.244:9092");
                    props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
                    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                            StringSerializer.class.getName());
                    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                            StringSerializer.class.getName());
                    KafkaProducer<String, String> producer = null;
                    int batchSize = 10000;

                    try {
                        producer = new KafkaProducer<String, String>(props);
                        int start = j1 * batchSize + 1;
                        int end = start + batchSize;
                        Random random = new Random();
                        ObjectMapper mapper = new ObjectMapper();
                        for (int i = start; i < end; i++) {
                            Map<String,Object> mapTemp = new HashMap<String,Object>();
                            mapTemp.put("id",i);
                            String randomString = RandomUtils.getCharacterAndNumber(30);
                            mapTemp.put("phone",randomString);
                            mapTemp.put("loginname",randomString);
                            byte bytes[] = new byte[10240];
                            random.nextBytes(bytes);
                            mapTemp.put("l1",Base64.getEncoder().encode(bytes));
                            String json = mapper.writeValueAsString(mapTemp);

                            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "key#" + mapTemp.get("id"), json);
                            producer.send(record);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        if (producer != null) {
                            producer.flush();
                            producer.close();
                        }
                    }
                }
            });
        }

        executorService.shutdown();
        while(!executorService.awaitTermination(1, TimeUnit.SECONDS));

        Date date2 = new Date();
        long milliseconds = date2.getTime() - date1.getTime();
        logger.debug("耗时"+milliseconds+"毫秒");
    }


    private boolean isStop = false;
    private int counter = 0;
    @Test
    public void testConsumer(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                final Properties props = new Properties();
                props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                        "192.168.1.244:9092");
                props.put(ConsumerConfig.GROUP_ID_CONFIG,
                        "KafkaExampleConsumer");
                props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                        StringDeserializer.class.getName());
                props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                        StringDeserializer.class.getName());
                Consumer<String, String> consumer = null;
                try{
                    consumer = new KafkaConsumer<>(props);
                    consumer.subscribe(Arrays.asList(TOPIC));

                    while (!isStop) {
                        final ConsumerRecords<String, String> consumerRecords =
                                consumer.poll(Duration.ofSeconds(1));
                        if (consumerRecords.count()==0) {
                            continue;
                        }
                        logger.debug("Consumer收到消息"+consumerRecords.count()+"条");
                        counter = counter + consumerRecords.count();
//                        consumerRecords.forEach(record -> {
//                            System.out.printf("Consumer Record:(%s, %s, %d, %d)\n",
//                                    record.key(), record.value(),
//                                    record.partition(), record.offset());
//                        });
                        consumer.commitAsync();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    if(consumer != null){
                        consumer.close();
                    }
                }
            }
        });

        isStop = true;
        logger.debug("Consumer接收到总共"+counter+"条消息");
    }
}
