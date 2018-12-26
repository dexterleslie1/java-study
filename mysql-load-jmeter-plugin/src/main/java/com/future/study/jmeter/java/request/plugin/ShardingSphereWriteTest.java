package com.future.study.jmeter.java.request.plugin;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.sql.SQLException;

/**
 * @author dexterleslie@gmail.com
 */
public class ShardingSphereWriteTest extends AbstractJavaSamplerClient {
    private ShardingSphereWriteTester shardingSphereTester = new ShardingSphereWriteTester();

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
            this.shardingSphereTester.setup();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult result = new SampleResult();
        result.sampleStart();

        if(this.shardingSphereTester != null){
            this.shardingSphereTester.doInsert();
        }

        result.sampleEnd();
        result.setSuccessful(true);
        result.setDataType(SampleResult.TEXT);
        return result;
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        if(this.shardingSphereTester != null){
            try {
                this.shardingSphereTester.teardown();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
