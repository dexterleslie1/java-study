package com.future.study.ngrok;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class HttpTraceFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(HttpTraceFilter.class);

    private final static ObjectMapper ObjectMapperInstance = new ObjectMapper();
    private Integer payloadMaxLength = 1024;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

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

        String traceLog = String.format("Trace log uri=%s,requestPayload=%s,responsePaylod=%s",
                request.getRequestURI(),
                requestPayload,
                responsePayload);
        logger.info(traceLog);
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
