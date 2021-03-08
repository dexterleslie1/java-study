http://127.0.0.1:8080/api/v1/test
http://127.0.0.1:8080/api/v1/test1
http://127.0.0.1:8080/api/v1/test2

变量定义和条件判断
https://dennis-xlc.gitbooks.io/the-logback-manual/content/en/chapter-3-configuration/configuration-file-syntax/variable-substitution.html
https://blog.csdn.net/wushengjun753/article/details/109510794

property、springProperty使用
https://blog.csdn.net/qq_34359363/article/details/104749341
1.该 <springProperty> 标签允许我们从Spring中显示属性，Environment 以便在Logback中使用。如果你想将 application.properties在回读配置中访问文件中的值，这将非常有用
2.标签的工作方式与Logback的标准 <property> 标签类似，但不是直接value 指定source属性（从Environment）指定。scope 如果需要将属性存储在local范围之外的其他位置，则可以使用该属性。如果您需要一个后备值，以防该属性未设置，则Environment可以使用该defaultValue属性。