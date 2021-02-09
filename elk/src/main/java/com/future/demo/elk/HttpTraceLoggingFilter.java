package com.future.demo.elk;

import net.logstash.logback.argument.StructuredArguments;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class HttpTraceLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(HttpTraceLoggingFilter.class);

    private static final int payloadMaxLength = 1024;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
            processAccessLog(request, response, null);
        } catch (Exception ex) {
            processAccessLog(request, response, ex);
            throw ex;
        }
    }

    private void processAccessLog(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Exception exception) throws IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String protocol = request.getProtocol();
        String remoteHost = request.getRemoteHost();
        int contentLength = request.getContentLength();

        long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        Map<String,String[]> mapTemp=requestWrapper.getParameterMap();
        String requestPayload="";
        List listTemp=new ArrayList();
        if(mapTemp!=null){
            for(String key:mapTemp.keySet()){
                String[] stringArr=mapTemp.get(key);
                String convertStr=String.join(",",stringArr);
                if(convertStr.length()>512){
                    convertStr=convertStr.substring(0,512);
                }
                String stringTemp=key+":"+convertStr;
                listTemp.add(stringTemp);
            }
        }
        if(listTemp.size()>0)
            requestPayload=String.join(",",listTemp);

        String responsePayload = getPayLoad(responseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());
        responseWrapper.copyBodyToResponse();
        int statusCode = response.getStatus();
        long elapsedTime = System.currentTimeMillis() - startTime;
        String requestHeaders = "";
        String responseHeaders = "";

        logger.info(""
                ,StructuredArguments.keyValue("method", method)
                ,StructuredArguments.keyValue("protocol", protocol)
                ,StructuredArguments.keyValue("status_code", statusCode)
                ,StructuredArguments.keyValue("remote_host", remoteHost)
                ,StructuredArguments.keyValue("uri", uri)
                ,StructuredArguments.keyValue("content_length", contentLength)
                ,StructuredArguments.keyValue("elapsed_time", elapsedTime)
                ,StructuredArguments.keyValue("request_headers", requestHeaders)
                ,StructuredArguments.keyValue("response_headers", responseHeaders)
                ,StructuredArguments.keyValue("request_payload", requestPayload)
                ,StructuredArguments.keyValue("response_payload", responsePayload)
                ,exception);
    }

    private String getPayLoad(byte[] buf, String characterEncoding) {
        String payload = "";
        if (buf == null) return payload;
        if (buf.length > 0) {
            int length = Math.min(buf.length, payloadMaxLength);
            try {
                payload = new String(buf, 0, length, characterEncoding);
            } catch (UnsupportedEncodingException ex) {
                payload = "[unknown]";
            }
        }
        return payload;
    }
}
