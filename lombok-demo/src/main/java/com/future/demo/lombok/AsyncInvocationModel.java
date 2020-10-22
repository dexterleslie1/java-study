package com.future.demo.lombok;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.IOException;
import java.util.Map;

/**
 *
 */
@Getter
@SuperBuilder
@NoArgsConstructor
public class AsyncInvocationModel {
    protected final static ObjectMapper OMInstance = new ObjectMapper();

    private AsyncInvocationType type;
    private Map<String, Object> parameters;

    /**
     *
     * @return
     */
    public String toJson() {
        String json = null;
        try {
            json = OMInstance.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    /**
     *
     * @param json
     * @return
     */
    public static AsyncInvocationModel fromJson(String json, Class<? extends AsyncInvocationModel> cls) {
        AsyncInvocationModel asyncInvocationModel = null;
        try {
            asyncInvocationModel = OMInstance.readValue(json, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return asyncInvocationModel;
    }
}
