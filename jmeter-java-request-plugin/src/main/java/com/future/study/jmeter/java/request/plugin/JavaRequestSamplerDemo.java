package com.future.study.jmeter.java.request.plugin;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.Date;

/**
 * @author dexterleslie@gmail.com
 */
public class JavaRequestSamplerDemo extends AbstractJavaSamplerClient {
    @Override
    public Arguments getDefaultParameters() {
        Date date = new Date();
        System.out.println(date + " - getDefaultParameters方法被调用");
        Arguments arguments = new Arguments();
        arguments.addArgument("var1","测试变量1");
        return arguments;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        Date date = new Date();
        System.out.println(date + " - setupTest方法被调用");
//        String var1 = context.getParameter("var1");
//        System.out.println("var1 = " + var1);
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        Date date = new Date();
        System.out.println(date + " - runTest方法被调用");
        SampleResult result = new SampleResult();
        result.sampleStart();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result.sampleEnd();
        result.setSuccessful(true);
        String var1 = context.getParameter("var1");
        result.setSampleLabel(var1);
        result.setDataType(SampleResult.TEXT);
        result.setResponseData(var1, null);
        return result;
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        Date date = new Date();
        System.out.println(date + " - teardownTest方法被调用");
    }
}
