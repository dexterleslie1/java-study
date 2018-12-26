package com.future.study.jmeter.java.request.plugin;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.sql.SQLException;

/**
 * @author dexterleslie@gmail.com
 */
public class JDBCWriteTest extends AbstractJavaSamplerClient {
    private JDBCWriteTester tester = new JDBCWriteTester();

//    @Override
//    public Arguments getDefaultParameters() {
//        Date date = new Date();
////        System.out.println(date + " - getDefaultParameters方法被调用");
//        Arguments arguments = new Arguments();
//        arguments.addArgument("var1","测试变量1");
//        return arguments;
//    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        try {
            this.tester.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult result = new SampleResult();
        result.sampleStart();

        if(this.tester != null){
            this.tester.doInsert();
        }

        result.sampleEnd();
        result.setSuccessful(true);
        result.setDataType(SampleResult.TEXT);
        return result;
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        if(this.tester != null){
            try {
                this.tester.teardown();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
