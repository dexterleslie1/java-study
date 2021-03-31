package com.future.demo.chat.server.api;

import com.future.demo.chat.server.common.ListResponse;
import com.future.demo.chat.server.common.ObjectResponse;
import com.future.demo.chat.server.common.PageResponse;
import com.future.demo.chat.server.exception.BusinessException;
import com.future.demo.chat.server.model.ChatMessageModel;
import com.future.demo.chat.server.util.HttpEntityUtils;
import com.future.demo.chat.server.value.object.SendMessageResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class RestApi {
    private RestTemplate restTemplate = new RestTemplate();
    private String username;
    private String apiHost;
    private SocketClient socketClient = null;
    private OnMessageCallback onMessageCallback;

    public RestApi(String username, String apiHost) {
        this.username = username;
        this.apiHost = apiHost;
    }

    public String getUsername() {
        return this.username;
    }

    public interface OnMessageCallback {
        void onMessage(String message) throws BusinessException;
    }

    public void registerOnMessageCallback(OnMessageCallback onMessageCallback) {
        this.onMessageCallback = onMessageCallback;
    }

    public void connectSocket() throws InterruptedException, ExecutionException, IOException, TimeoutException {
        if(this.socketClient==null) {
            this.socketClient = new SocketClient(this.username, this.apiHost) {
                @Override
                public void onMessage(String message) throws Exception {
                    if(onMessageCallback!=null) {
                        synchronized (onMessageCallback) {
                            onMessageCallback.onMessage(message);
                        }
                    }
                }
            };
            this.socketClient.connect();
        }
    }

    public void disconnectSocket() {
        if(this.socketClient!=null) {
            try {
                this.socketClient.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        this.socketClient = null;
    }

    public ObjectResponse<SendMessageResultVO> send(String usernameTo,
                                                    String content,
                                                    int type) throws BusinessException {
        Map<String, String> params = new HashMap<>();
        params.put("usernameSelf", this.username);
        params.put("usernameTo", usernameTo);
        params.put("content", content);
        params.put("type", String.valueOf(type));
        ObjectResponse<SendMessageResultVO> response = this.postForObjectResponse(
                "/api/v1/send",
                params,
                new ParameterizedTypeReference<ObjectResponse<SendMessageResultVO>>() {}
        );
        return response;
    }

    public PageResponse<ChatMessageModel> pull() throws BusinessException {
        Map<String, String> params = new HashMap<>();
        params.put("usernameSelf", this.username);
        PageResponse<ChatMessageModel> response = this.postForPageResponse(
                "/api/v1/pull",
                params,
                new ParameterizedTypeReference<PageResponse<ChatMessageModel>>() {}
        );
        return response;
    }

    public ObjectResponse<String> confirm(List<Long> ids) throws BusinessException {
        List<String> idsString = new ArrayList<>();
        if(ids!=null && ids.size()>0) {
            for(Long id : ids) {
                idsString.add(String.valueOf(id));
            }
        }
        Map<String, String> params = new HashMap<>();
        params.put("usernameSelf", this.username);
        params.put("ids", String.join(",", idsString));
        ObjectResponse<String> response = this.postForObjectResponse(
                "/api/v1/confirm",
                params,
                new ParameterizedTypeReference<ObjectResponse<String>>() {}
        );
        return response;
    }

    public void postForVoidResponse(String requestUri, Map<String, String> params) throws BusinessException {
        HttpEntity httpEntity = HttpEntityUtils.createHttpEntity(params);
        ResponseEntity<ObjectResponse> responseEntity = this.restTemplate.exchange(
                getRequestUrl(requestUri),
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<ObjectResponse>() {});
        ObjectResponse objectResponse = responseEntity.getBody();
        if(objectResponse.getErrorCode() > 0){
            BusinessException businessException = new BusinessException(objectResponse.getErrorCode(), objectResponse.getErrorMessage());
            Object data = objectResponse.getData();
            businessException.setData(data);
            throw businessException;
        }
    }

    public <T> ObjectResponse<T> postForObjectResponse(
            String requestUri,
            Map<String, String> params,
            ParameterizedTypeReference<ObjectResponse<T>> parameterizedTypeReference) throws BusinessException{
        HttpEntity httpEntity = HttpEntityUtils.createHttpEntity(params);
        ResponseEntity<ObjectResponse<T>> responseEntity = this.restTemplate.exchange(
                getRequestUrl(requestUri),
                HttpMethod.POST,
                httpEntity,
                parameterizedTypeReference);
        ObjectResponse<T> objectResponse = responseEntity.getBody();
        if(objectResponse.getErrorCode() > 0){
            BusinessException businessException = new BusinessException(objectResponse.getErrorCode(), objectResponse.getErrorMessage());
            Object data = ((ObjectResponse)(objectResponse)).getData();
            businessException.setData(data);
            throw businessException;
        }
        return objectResponse;
    }

    public <T> ListResponse<T> postForListResponse(
            String requestUri,
            Map<String, String> params,
            ParameterizedTypeReference<ListResponse<T>> parameterizedTypeReference) throws BusinessException{
        HttpEntity httpEntity =  HttpEntityUtils.createHttpEntity(params);
        ResponseEntity<ListResponse<T>> responseEntity = this.restTemplate.exchange(
                getRequestUrl(requestUri),
                HttpMethod.POST,
                httpEntity,
                parameterizedTypeReference);
        ListResponse<T> response = responseEntity.getBody();
        if(response.getErrorCode() > 0){
            BusinessException businessException = new BusinessException(response.getErrorCode(), response.getErrorMessage());
            businessException.setData(response.getData());
            throw businessException;
        }
        return response;
    }


    public <T> PageResponse<T> postForPageResponse(
            String requestUri,
            Map<String, String> params,
            ParameterizedTypeReference<PageResponse<T>> parameterizedTypeReference) throws BusinessException{
        HttpEntity httpEntity = HttpEntityUtils.createHttpEntity(params);
        ResponseEntity<PageResponse<T>> responseEntity = this.restTemplate.exchange(
                getRequestUrl(requestUri),
                HttpMethod.POST,
                httpEntity,
                parameterizedTypeReference);
        PageResponse<T> response = responseEntity.getBody();
        if(response.getErrorCode() > 0){
            BusinessException businessException = new BusinessException(response.getErrorCode(), response.getErrorMessage());
            businessException.setData(response.getData());
            throw businessException;
        }
        return response;
    }
//
//    /**
//     * 上传文件
//     * @param requestUrl
//     * @param file
//     * @param params
//     * @return
//     * @throws BusinessException
//     */
//    public String uploadFile(String requestUrl, File file, Map<String,Object> params) throws BusinessException {
//        if(file==null){
//            throw new IllegalArgumentException("指定文件");
//        }
//        FileSystemResource fileSystemResource=new FileSystemResource(file);
//        if(params==null){
//            params=new HashMap<>();
//        }
//        params.put("file",fileSystemResource);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        HttpEntity httpEntity = null;
//        if(credential != null){
//            headers.put("token", Arrays.asList(credential.getToken()));
//            httpEntity = HttpEntityUtils.createHttpEntitySupportFile(params, headers);
//        }
//
//        ResponseEntity<ObjectResponse<String>> responseEntity = this.restTemplate.exchange(
//                requestUrl,
//                HttpMethod.POST,
//                httpEntity,
//                new ParameterizedTypeReference<ObjectResponse<String>>() {});
//        ObjectResponse<String> response = responseEntity.getBody();
//        if(response.getErrorCode() > 0){
//            BusinessException businessException = new BusinessException(response.getErrorCode(), response.getErrorMessage());
//            Object data = response.getData();
//            businessException.setData(data);
//            throw businessException;
//        }
//        if(response==null) {
//            return null;
//        }
//        return response.getData();
//    }
//
//    /**
//     * 下载文件
//     * @param url
//     * @param outputStream
//     * @param params
//     * @throws BusinessException
//     * @throws IOException
//     */
//    public void downloadFile(String url, OutputStream outputStream, Map<String,Object> params) throws BusinessException, IOException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.APPLICATION_OCTET_STREAM));
//
//        HttpEntity httpEntity = null;
//        if(credential != null){
//            headers.put("token", Arrays.asList(credential.getToken()));
//            httpEntity = HttpEntityUtils.createHttpEntitySupportFile(params, headers);
//        }
//
//        ResponseEntity responseEntity = this.restTemplate.exchange(
//                url,
//                HttpMethod.POST,
//                httpEntity,
//                byte[].class);
//
//        HttpHeaders responseHeaders = responseEntity.getHeaders();
//        if(responseHeaders.getContentType().equals(MediaType.APPLICATION_OCTET_STREAM)){
//            byte[] datas = (byte[])responseEntity.getBody();
//            IOUtils.write(datas, outputStream);
//            return ;
//        }
//
//        Object body = responseEntity.getBody();
//        ObjectMapper OMInstance = new ObjectMapper();
//        ObjectResponse<String> response = OMInstance.readValue((byte[])body, ObjectResponse.class);
//        int errorCode = response.getErrorCode();
//        if (errorCode > 0) {
//            String errorMessage = response.getErrorMessage();
//            Object dataObject = response.getData();
//            BusinessException businessException = new BusinessException(errorCode, errorMessage);
//            businessException.setData(dataObject);
//            throw businessException;
//        }
//    }
//

    private String getRequestUrl(String requestUri){
        if(requestUri.startsWith("/")) {
            requestUri = "http://" + apiHost + ":8080" + requestUri;
        }else{
            requestUri = "http://" + apiHost + ":8080" + "/" + requestUri;
        }
        return requestUri;
    }
//
//    /**
//     *
//     * @return
//     */
//    public LoginSuccessVO getCredential(){
//        return this.credential;
//    }
}
