package com.future.demo.kamailio.api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dexterleslie@gmail.com
 */
@RestController
public class ApiController {
	private final static Logger logger = LoggerFactory.getLogger(ApiController.class);

    /**
     *
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value="/")
    public ResponseEntity<String> api(
    		@RequestParam(value = "caller", defaultValue="") String caller,
    		@RequestParam(value = "callee", defaultValue="") String callee){
		if(StringUtils.isEmpty(caller) ||
				StringUtils.isEmpty(callee)) {
			logger.warn("非法参数 caller=" + caller + ",callee=" + callee);
			return ResponseEntity.status(400).body("非法参数 caller=" + caller + ",callee=" + callee);
		}
		
		// sip->sip、sip->PSTN、PSTN->sip 3种场景识别方式通过被叫或者主叫是否为null区别
		// sip->sip、sip->PSTN 不能呼叫自己
		// DID月租过期控制
//		String responseStr = "a[560]E#您号码月租已过期，被限制呼出";
//		String responseStr = "a[561]E#您app月租已过期，被限制呼出";
//		String responseStr = "a[562]E#非好友关系";
//		String responseStr = "a[563]E#余额不足，至少余额：2元，当前余额：1元";
//		String responseStr = "a[564]E#不能呼叫自己";
//		String responseStr = "a[565]E#您未申请号码，不能拨打：" + callee;
		
		String responseStr = "75";
		logger.info("/ 接口被调用，参数caller=" + caller + ",callee=" + callee + "，返回 " + responseStr);
        return ResponseEntity.ok(responseStr);
    }
	
	/**
    *
    * @param request
    * @param response
    * @return
	 * @throws IOException 
    */
	@RequestMapping(value="/publishWakeupAPNS")
   public ResponseEntity<String> publishWakeupAPNS(
   		@RequestParam(value = "caller", defaultValue="") String caller,
   		@RequestParam(value = "callee", defaultValue="") String callee,
   		HttpServletResponse response) throws IOException{
		if(StringUtils.isEmpty(caller) ||
				StringUtils.isEmpty(callee)) {
			logger.warn("非法参数 caller=" + caller + ",callee=" + callee);
			return ResponseEntity.status(400).body("非法参数 caller=" + caller + ",callee=" + callee);
		}
		
		String responseStr = "60";
		logger.info("/publishWakeupAPNS 接口被调用，参数caller=" + caller + ",callee=" + callee + "，返回 " + responseStr);
		return ResponseEntity.ok(responseStr);
   }
}
