package com.future.demo.spring.cloud.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class ExceptionController {
	
	private final static Logger log = LoggerFactory.getLogger(ExceptionController.class);
	
    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody
    ResponseEntity<ObjectResponse<String>> handleIllegalArgumentException(IllegalArgumentException e)   {
        ObjectResponse<String> response = new ObjectResponse<>();
        response.setErrorCode(600);
        response.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public @ResponseBody
    ResponseEntity<ObjectResponse<String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e)   {
        ObjectResponse<String> response = new ObjectResponse<>();
        response.setErrorCode(600);
        response.setErrorMessage("Api调用缺失参数：" + e.getParameterName());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public @ResponseBody
    ResponseEntity<ObjectResponse<String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e)   {
        ObjectResponse<String> response = new ObjectResponse<>();
        response.setErrorCode(600);
        response.setErrorMessage("Api调用参数转换错误，参数：" + e.getName() + "，值：" + e.getValue());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<ObjectResponse<String>> handleError(NoHandlerFoundException e)   {
    	log.error(e.getMessage(),e);
        ObjectResponse<String> response = new ObjectResponse<>();
        response.setErrorMessage("访问资源 " + e.getRequestURL() + " 不存在");
        response.setErrorCode(600);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ObjectResponse<String>> handleException(
            HttpServletRequest request ,
            Exception e) {
        StringBuilder builder = new StringBuilder();
        builder.append(";" + e.getMessage());

        String queryString = request.getQueryString();
        if (queryString != null) {
            builder.append(";").append(queryString);
        }

        Map<String, String []> parameters = request.getParameterMap();
        if(parameters != null && parameters.size()>0) {
            builder.append(";params=");
            Set<String> keys = parameters.keySet();
            int size = keys.size();
            int counter = 0;
            for (String key : keys) {
                String [] arrayTemporary = parameters.get(key);
                builder.append(key + "=" + (arrayTemporary==null?"":String.join(",", arrayTemporary)));
                if(counter+1<size){
                    builder.append(",");
                }
                counter++;
            }
        }
    	log.error(builder.toString(),e);
        ObjectResponse<String> response = new ObjectResponse<>();
        response.setErrorCode(600);
        if(e instanceof ResourceAccessException){
            response.setErrorMessage("网络繁忙，稍后重试");
        }else{
            response.setErrorMessage("网络繁忙，稍后重试");
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

}
