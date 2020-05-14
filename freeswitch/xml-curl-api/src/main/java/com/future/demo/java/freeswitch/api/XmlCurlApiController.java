package com.future.demo.java.freeswitch.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private final static Logger logger = LoggerFactory.getLogger(XmlCurlApiController.class);
	
	@Autowired
	private Client client = null;
	
	/**
    *
    * @param request
    * @return
    * @throws ServletRequestBindingException
    */
   @RequestMapping(value = "directory", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<String> directory(HttpServletRequest request) throws ServletRequestBindingException {
	   Document document = null;
	   try {
           String sipAuthUsername = ServletRequestUtils.getStringParameter(request, "user");
//           long userId = 0;
//           try {
//               userId = Long.parseLong(sipAuthUsername);
//           } catch (Exception ex) {
//               logger.error(ex.getMessage(), ex);
//           }

//           SipInfoVO sipInfoVO = this.sipInfoService.get(userId);
//           if (sipInfoVO == null) {
//               document = DocumentHelper.createDocument();
//               Element rootElement = document.addElement("document");
//               rootElement.addAttribute("type", "freeswitch/xml");
//
//               Element sectionElement = rootElement.addElement("section");
//               sectionElement.addAttribute("name", "result");
//
//               Element resultElement = sectionElement.addElement("result");
//               resultElement.addAttribute("status", "not found");
//           } else {
               document = DocumentHelper.createDocument();
               Element rootElement = document.addElement("document");
               rootElement.addAttribute("type", "freeswitch/xml");

               Element sectionElement = rootElement.addElement("section");
               sectionElement.addAttribute("name", "directory");

               Element domainElement = sectionElement.addElement("domain");
               String keyValue = ServletRequestUtils.getStringParameter(request, "domain");
               domainElement.addAttribute("name", keyValue);

               Element paramsElement = domainElement.addElement("params");
               Element paramElement = paramsElement.addElement("param");
               paramElement.addAttribute("name", "dial-string");
               paramElement.addAttribute("value", "{presence_id=${dialed_user}@${dialed_domain}}${sofia_contact(${dialed_user}@${dialed_domain})}");

               Element groupsElement = domainElement.addElement("groups");
               Element groupElement = groupsElement.addElement("group");
               groupElement.addAttribute("name", "default");
               Element usersElement = groupElement.addElement("users");
               Element userElement = usersElement.addElement("user");
               userElement.addAttribute("id", sipAuthUsername);
               Element variablesElement = userElement.addElement("variables");

               Element variableElement = variablesElement.addElement("variable");
               variableElement.addAttribute("name", "toll_allow");
               variableElement.addAttribute("value", "domestic,international,local");

               variableElement = variablesElement.addElement("variable");
               variableElement.addAttribute("name", "accountcode");
               variableElement.addAttribute("value", sipAuthUsername);

               variableElement = variablesElement.addElement("variable");
               variableElement.addAttribute("name", "user_context");
               variableElement.addAttribute("value", "default");

               variableElement = variablesElement.addElement("variable");
               variableElement.addAttribute("name", "effective_caller_id_name");
               variableElement.addAttribute("value", "Extension " + sipAuthUsername);

               variableElement = variablesElement.addElement("variable");
               variableElement.addAttribute("name", "effective_caller_id_number");
               variableElement.addAttribute("value", sipAuthUsername);

               variableElement = variablesElement.addElement("variable");
               variableElement.addAttribute("name", "callgroup");
               variableElement.addAttribute("value", "default");

               paramsElement = userElement.addElement("params");
               paramElement = paramsElement.addElement("param");
               paramElement.addAttribute("name", "password");
               paramElement.addAttribute("value", "123456");

               paramElement = paramsElement.addElement("param");
               paramElement.addAttribute("name", "vm-password");
               paramElement.addAttribute("value", "123456");
//           }
       } catch (Exception exception) {
           String sipAuthUsername = ServletRequestUtils.getStringParameter(request, "user");
           String message = "尝试生成sip用户：" + sipAuthUsername + " 目录（directory）， 意料之外错误：" + exception.getMessage();
           logger.error(message, exception);
           return null;
       }
       String xml = document.asXML();
       return ResponseEntity.ok(xml);
   }
	
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
