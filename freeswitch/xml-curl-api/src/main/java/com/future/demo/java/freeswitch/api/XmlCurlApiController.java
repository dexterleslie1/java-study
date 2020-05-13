package com.future.demo.java.freeswitch.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dexterleslie@gmail.com
 */
@RestController
@RequestMapping(value="/api/v1/freeswitch")
public class XmlCurlApiController {
	@Autowired
	private Client client = null;
	
    /**
     * 
     * @param request
     * @return
     * @throws ServletRequestBindingException
     * @throws DocumentException
     */
    @RequestMapping(value = "dialplan", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> dialplan(HttpServletRequest request) throws ServletRequestBindingException {
    	String dialplan = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
    			"<document type=\"freeswitch/xml\">\n" + 
    			"  <section name=\"dialplan\">\n" + 
    			"    <context name=\"default\">" + 
    			"		<extension name=\"Local_Extension\">\n" + 
    			"      		<condition field=\"destination_number\" expression=\"^(10[01][0-9])$\">\n" + 
    			"        		<action application=\"export\" data=\"dialed_extension=$1\"/>\n" + 
				"        		<action application=\"sleep\" data=\"1000\"/>\n" + 
				"        		<action application=\"ring_ready\"/>\n" + 
				"        		<action application=\"bridge\" data=\"user/${dialed_extension}\"/>\n" + 
				"        		<action application=\"hangup\"/>\n" + 
    			"      		</condition>\n" + 
    			"    	</extension>" + 
    			"	</context>\n" + 
    			"	<context name=\"public\">\n" +
    			"		<extension name=\"public_extensions\">\n" +
    			"	  		<condition field=\"destination_number\" expression=\"^(10[01][0-9])$\">\n" +
    			"	    		<action application=\"transfer\" data=\"$1 XML default\"/>\n" +
    			"	  		</condition>\n" +
    			"		</extension>\n" +
    			"	</context>\n" +
    			"  </section>\n" + 
    			"</document>";
    	
    	String callee = ServletRequestUtils.getStringParameter(request, "Caller-Destination-Number");
    	
    	EslMessage message = client.sendSyncApiCommand("sofia_contact", callee);
    	List<String> response = message.getBodyLines();
    	Date startTime = new Date();
    	while(response.size()>0 && "error/user_not_registered".equals(response.get(0))) {
    		System.out.println("user_not_registered");
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//
			}
    		Date currentTime = new Date();
    		long milliseconds = currentTime.getTime() - startTime.getTime();
    		if(milliseconds>=30000) {
    			break;
    		}
    		message = client.sendSyncApiCommand("sofia_contact", callee);
    		response = message.getBodyLines();
    	}
    	
        return ResponseEntity.ok(dialplan);
    }
}
