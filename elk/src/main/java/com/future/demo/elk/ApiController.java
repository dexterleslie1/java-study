package com.future.demo.elk;

import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RestController
public class ApiController {
    final static Logger logger = LoggerFactory.getLogger(ApiController.class);

    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/test1",method = RequestMethod.GET)
    public void test1(){
        logger.info("测试消息");

        Exception exception = new Exception("测试异常");
        logger.error(exception.getMessage(), exception);

        /*
         * Add "name":"value" to the JSON output,
         * but only add the value to the formatted message.
         *
         * The formatted message will be `log message value`
         */
        logger.info("log message {}", StructuredArguments.value("name", "value"));

        /*
         * Add "name":"value" to the JSON output,
         * and add name=value to the formatted message.
         *
         * The formatted message will be `log message name=value`
         */
        logger.info("log message {}", StructuredArguments.keyValue("name", "value"));

        /*
         * Add "name":"value" ONLY to the JSON output.
         *
         * Since there is no parameter for the argument,
         * the formatted message will NOT contain the key/value.
         *
         * If this looks funny to you or to static analyzers,
         * consider using Markers instead.
         */
        logger.info("log message", StructuredArguments.keyValue("name", "value"));

        /*
         * Add multiple key value pairs to both JSON and formatted message
         */
        logger.info("log message {} {}", StructuredArguments.keyValue("name1", "value1"), StructuredArguments.keyValue("name2", "value2"));

        /*
         * Add "name":"value" to the JSON output and
         * add name=[value] to the formatted message using a custom format.
         */
        logger.info("log message {}", StructuredArguments.keyValue("name", "value", "{0}=[{1}]"));

        /*
         * In the JSON output, values will be serialized by Jackson's ObjectMapper.
         * In the formatted message, values will follow the same behavior as logback
         * (formatting of an array or if not an array `toString()` is called).
         *
         * Add "foo":{...} to the JSON output and add `foo.toString()` to the formatted message:
         *
         * The formatted message will be `log message <result of foo.toString()>`
         */
        Foo foo  = new Foo();
        logger.info("log message {}", StructuredArguments.value("foo", foo));

        /*
         * Add "name1":"value1","name2":"value2" to the JSON output by using a Map,
         * and add `myMap.toString()` to the formatted message.
         *
         * Note the values can be any object that can be serialized by Jackson's ObjectMapper
         * (e.g. other Maps, JsonNodes, numbers, arrays, etc)
         */
        Map myMap = new HashMap();
        myMap.put("name1", "value1");
        myMap.put("name2", "value2");
        logger.info("log message {}", StructuredArguments.entries(myMap));

        /*
         * Add "array":[1,2,3] to the JSON output,
         * and array=[1,2,3] to the formatted message.
         */
        logger.info("log message {}", StructuredArguments.array("array", 1, 2, 3));

        /*
         * Add fields of any object that can be unwrapped by Jackson's UnwrappableBeanSerializer to the JSON output.
         * i.e. The fields of an object can be written directly into the JSON output.
         * This is similar to the @JsonUnwrapped annotation.
         *
         * The formatted message will contain `myobject.toString()`
         */
        logger.info("log message {}", StructuredArguments.fields(foo));
    }
}
