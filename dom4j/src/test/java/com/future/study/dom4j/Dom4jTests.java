package com.future.study.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 *
 */
public class Dom4jTests {
    /**
     * <document type="freeswitch/xml">
     *   <section name="directory">
     *     <domain name="domain1.awesomevoipdomain.faketld">
     *       <params>
     *         <param name="dial-string" value="{presence_id=${dialed_user}@${dialed_domain}}${sofia_contact(${dialed_user}@${dialed_domain})}"/>
     *       </params>
     *       <groups>
     *         <group name="default">
     *          <users>
     *           <user id="1004">
     *             <params>
     *               <param name="password" value="some_password"/>
     *             </params>
     *           </user>
     *          </users>
     *         </group>
     *       </groups>
     *     </domain>
     *   </section>
     * </document>
     */
    @Test
    public void test() throws IOException, DocumentException {
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement("document");
        rootElement.addAttribute("type", "freeswitch/xml");

        Element sectionElement = rootElement.addElement("section");
        sectionElement.addAttribute("name", "directory");

        Element domainElement = sectionElement.addElement("domain");
        domainElement.addAttribute("name", "domain1.awesomevoipdomain.faketld");

        Element paramsElement = domainElement.addElement("params");
        Element paramElement = paramsElement.addElement("param");
        paramElement.addAttribute("name", "dial-string");
        paramElement.addAttribute("value", "{presence_id=${dialed_user}@${dialed_domain}}${sofia_contact(${dialed_user}@${dialed_domain})}");

        Element groupsElement = domainElement.addElement("groups");
        Element groupElement = groupsElement.addElement("group");
        groupElement.addAttribute("name", "default");
        Element usersElement = groupElement.addElement("users");
        Element userElement = usersElement.addElement("user");
        userElement.addAttribute("id", "1004");
        paramsElement = userElement.addElement("params");
        paramElement = paramsElement.addElement("param");
        paramElement.addAttribute("name", "password");
        paramElement.addAttribute("value", "some_password");

        FileOutputStream outputStream = null;
        File file = null;
        try {
            file = File.createTempFile(UUID.randomUUID().toString(), ".xml");
            outputStream = new FileOutputStream(file);
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(outputStream, format);
            writer.write(document);
            writer.flush();
        } finally {
            if(outputStream!=null) {
                outputStream.close();
                outputStream = null;
            }
        }

        SAXReader reader = new SAXReader();
        document = reader.read(file);

        rootElement = (Element)document.selectSingleNode("/document");
        String type = rootElement.attributeValue("type");
        Assert.assertEquals("freeswitch/xml", type);

        userElement = (Element)document.selectSingleNode("/document/section/domain/groups/group/users/user");
        Assert.assertEquals("1004", userElement.attributeValue("id"));
    }
}
