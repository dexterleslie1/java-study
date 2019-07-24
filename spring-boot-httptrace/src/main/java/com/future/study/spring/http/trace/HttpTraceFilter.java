package com.future.study.spring.http.trace;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class HttpTraceFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(HttpTraceFilter.class);

    private String usernameKey = "username";
    private Integer payloadMaxLength = 1024;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        Long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        String requestPayload = getPayLoad(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());
        String responsePayload = getPayLoad(responseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());
        responseWrapper.copyBodyToResponse();

        Map<String, Object> mapTemporary = new HashMap<>();
        mapTemporary.put("requestPayload", requestPayload);
        mapTemporary.put("responsePayload", responsePayload);
        mapTemporary.put("time used", System.currentTimeMillis() - startTime + " 毫秒");
        logger.debug(new ObjectMapper().writeValueAsString(mapTemporary));
    }

    private String getPayLoad(byte[] buf, String characterEncoding) {
        String payload = "";
        if (buf == null) return payload;
        if (buf.length > 0) {
            int length = Math.min(buf.length, getPayloadMaxLength());
            try {
                payload = new String(buf, 0, length, characterEncoding);
            } catch (UnsupportedEncodingException ex) {
                payload = "[unknown]";
            }
        }
        return payload;
    }


    public String getUsernameKey() {
        return usernameKey;
    }

    public void setUsernameKey(String usernameKey) {
        this.usernameKey = usernameKey;
    }

    public Integer getPayloadMaxLength() {
        return payloadMaxLength;
    }

    public void setPayloadMaxLength(Integer payloadMaxLength) {
        this.payloadMaxLength = payloadMaxLength;
    }
}
