package com.future.demo.elasticsearch.rest.high.level.client;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 *
 */
public class IndexApiTests extends AbstractTestSupport {
    /**
     *
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {
        String index = "index1";
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if(exists) {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
            AcknowledgedResponse response =
                    client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            Assert.assertTrue(response.isAcknowledged());
        }
        CreateIndexRequest request = new CreateIndexRequest(index);
        CreateIndexResponse createIndexResponse =
                client.indices().create(request, RequestOptions.DEFAULT);
        Assert.assertTrue(createIndexResponse.isAcknowledged());
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void deleteIndex() throws IOException {
        String index = "index1";
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if(!exists) {
            CreateIndexRequest request = new CreateIndexRequest(index);
            CreateIndexResponse createIndexResponse =
                    client.indices().create(request, RequestOptions.DEFAULT);
            Assert.assertTrue(createIndexResponse.isAcknowledged());
        }
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        AcknowledgedResponse response =
                client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        Assert.assertTrue(response.isAcknowledged());
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void indicesExists() throws IOException {
        String index = "index1";
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if(!exists) {
            CreateIndexRequest request = new CreateIndexRequest(index);
            CreateIndexResponse createIndexResponse =
                    client.indices().create(request, RequestOptions.DEFAULT);
            Assert.assertTrue(createIndexResponse.isAcknowledged());
        }
        exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        Assert.assertTrue(exists);
    }

    @Test
    public void openIndex() throws IOException {
        String index = "index1";
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if(!exists) {
            CreateIndexRequest request = new CreateIndexRequest(index);
            CreateIndexResponse createIndexResponse =
                    client.indices().create(request, RequestOptions.DEFAULT);
            Assert.assertTrue(createIndexResponse.isAcknowledged());
        }

        OpenIndexRequest openIndexRequest = new OpenIndexRequest(index);
        OpenIndexResponse openIndexResponse =
                client.indices().open(openIndexRequest, RequestOptions.DEFAULT);
        Assert.assertTrue(openIndexResponse.isAcknowledged());
    }

    @Test
    public void closeIndex() throws IOException {
        String index = "index1";
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if(!exists) {
            CreateIndexRequest request = new CreateIndexRequest(index);
            CreateIndexResponse createIndexResponse =
                    client.indices().create(request, RequestOptions.DEFAULT);
            Assert.assertTrue(createIndexResponse.isAcknowledged());
        }

        CloseIndexRequest closeIndexRequest = new CloseIndexRequest(index);
        AcknowledgedResponse acknowledgedResponse =
                client.indices().close(closeIndexRequest, RequestOptions.DEFAULT);
        Assert.assertTrue(acknowledgedResponse.isAcknowledged());
    }

    @Test
    public void putAndGetMappings() throws IOException {
        String index = "index1";
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (exists) {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
            AcknowledgedResponse acknowledgedResponse =
                    client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            Assert.assertTrue(acknowledgedResponse.isAcknowledged());
        }

        CreateIndexRequest request = new CreateIndexRequest(index);
        CreateIndexResponse createIndexResponse =
                client.indices().create(request, RequestOptions.DEFAULT);
        Assert.assertTrue(createIndexResponse.isAcknowledged());

        PutMappingRequest putMappingRequest = new PutMappingRequest(index);
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject();
        xContentBuilder.startObject("properties");
        xContentBuilder.startObject("message");
        xContentBuilder.field("type", "text");
        xContentBuilder.endObject();
        xContentBuilder.endObject();
        xContentBuilder.endObject();
        putMappingRequest.source(xContentBuilder);
        AcknowledgedResponse acknowledgedResponse =
                client.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
        Assert.assertTrue(acknowledgedResponse.isAcknowledged());

        GetMappingsRequest getMappingsRequest = new GetMappingsRequest();
        getMappingsRequest.includeDefaults(true);
        getMappingsRequest.indices(index);
        GetMappingsResponse getMappingsResponse =
                client.indices().getMapping(getMappingsRequest, RequestOptions.DEFAULT);
        Map<String, MappingMetaData> mappingMetaDataMap = getMappingsResponse.mappings();
        MappingMetaData mappingMetaData = mappingMetaDataMap.get(index);
        Assert.assertNotNull(mappingMetaData);
    }
}
