package com.future.study.spring.large.scale.solution;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Dexterleslie.Chan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Config.class})
public class ElasticsearchTests {
    private final static Logger logger = LogManager.getLogger(ElasticsearchTests.class);

    @Autowired
    private UserRepository userRepository;

    RestHighLevelClient client;

    @Before
    public void setup(){
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.1.243", 9200, "http")));
    }

    @After
    public void teardown() throws IOException {
        if(client != null){
            client.close();
        }
    }

    @Test
    public void test() throws IOException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int j = 0; j < 100; j++) {
            final int j1 = j;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        int batchSize = 100000;
                        ObjectMapper objectMapper = new ObjectMapper();
                        int start = j1 * batchSize + 1;
                        int end = start + batchSize;
                        for (int i = start; i <= end; i++) {
                            User user = userRepository.findById(i).orElse(null);
                            IndexRequest request = new IndexRequest(
                                    "chat",
                                    "user",
                                    String.valueOf(user.getId()));
                            String json = objectMapper.writeValueAsString(user);
                            request.source(json, XContentType.JSON);
                            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
                            Assert.assertEquals(RestStatus.CREATED, response.status());
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        while(!executorService.awaitTermination(1, TimeUnit.SECONDS));
    }

    @Test
    public void findByPhoneAndPassword() throws IOException, InterruptedException {
        Random random = new Random();
        int range = 10000000;
        // 随机抽取10000000内数据2000条
        List<Integer> ids = new ArrayList<Integer>();
        while(ids.size() < 2000){
            int i1 = random.nextInt(range);
            if(!ids.contains(i1)){
                ids.add(i1);
            }
        }

        List<User> users = new ArrayList<User>();
        for(int i = 0 ; i < ids.size(); i++){
            User user = userRepository.findById(ids.get(i)).orElse(null);
            users.add(user);
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0 ;i < 500; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    int userSize = users.size();
                    try {
                        for(int j = 0; j < 1000; j++) {
                            if(j != 0 && j%100 == 0){
                                logger.debug(Thread.currentThread().getName()+"检查点"+j);
                            }
                            int indexTemp = random.nextInt(userSize);
                            User user = users.get(indexTemp);
                            String phone = user.getPhone();
                            String password = user.getPassword();
                            SearchRequest searchRequest = new SearchRequest("chat");
                            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("phone", phone);
                            searchSourceBuilder.query(matchQueryBuilder);
                            matchQueryBuilder = new MatchQueryBuilder("password", password);
                            searchSourceBuilder.query(matchQueryBuilder);
                            searchRequest.source(searchSourceBuilder);
                            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
                            SearchHits searchHits = response.getHits();
                            Map<String, Object> objectMap = searchHits.getAt(0).getSourceAsMap();
                            int id = (Integer) objectMap.get("id");
                            Assert.assertEquals(user.getId(),id);
                            long milliseconds = response.getTook().getMillis();
                            if(milliseconds > 100){
                                logger.debug("查询用户["+user.getId()+"]耗时"+milliseconds+"毫秒");
                            }
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown();
        while(!executorService.awaitTermination(1,TimeUnit.SECONDS));
    }

    @Test
    public void testWritePerformance() throws InterruptedException {
        List<Integer> sex = new ArrayList<Integer>();
        sex.add(0);
        sex.add(1);
        sex.add(2);

        ExecutorService executorService = Executors.newCachedThreadPool();
        Date date1 =new Date();
        for(int j = 0; j < 100 ; j++) {
            final int j1 = j;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Random random = new Random();
                        int size = sex.size();
                        ObjectMapper objectMapper = new ObjectMapper();
                        int batchSize = 1000;
                        int start = j1 * batchSize + 1;
                        int end = start + batchSize;
                        for (int i = start; i <= end; i++) {
                            User user = new User();
                            user.setId(i);
                            user.setCreateTime(new Date());
                            user.setLoginname("ln" + RandomUtils.getCharacterAndNumber(20));
                            user.setNickname("nn" + RandomUtils.getCharacterAndNumber(20));
                            user.setPassword(RandomUtils.getCharacterAndNumber(20));
                            user.setPhone("135" + RandomUtils.getCharacterAndNumber(20));
                            user.setEmail("gm" + RandomUtils.getCharacterAndNumber(20) + "@gmail.com");
                            int index = random.nextInt(size);
                            user.setSex(sex.get(index));

                            IndexRequest request = new IndexRequest(
                                    "chat",
                                    "user",
                                    String.valueOf(user.getId()));
                            String json = objectMapper.writeValueAsString(user);
                            request.source(json, XContentType.JSON);
                            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
                            Assert.assertEquals(RestStatus.CREATED, response.status());
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown();
        while(!executorService.awaitTermination(1, TimeUnit.SECONDS));

        Date date2 = new Date();
        long milliseconds = date2.getTime() - date1.getTime();
        logger.debug("耗时："+milliseconds+"毫秒");
    }
}
