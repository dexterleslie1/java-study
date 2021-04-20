JWT 原理
https://www.jianshu.com/p/8c89683546ee
https://blog.csdn.net/xunileida/article/details/82961714

JAVA RSA
https://blog.csdn.net/u013314786/article/details/80324461

JAVA RSA 签名校验
https://www.cnblogs.com/demodashi/p/8458113.html

使用java-jwt库验证RSA签名
https://stackoverflow.com/questions/49693409/verify-signature-using-jwt-java-jwt

使用JWT实现统一登录
https://www.cnblogs.com/zhenghongxin/archive/2018/11/23/10006697.html

openSSL生成私钥
openssl genrsa -out private.pem 1024
openSSL生成公钥
openssl rsa -in private.pem -outform PEM -pubout -out public.pem
openSSL转换公钥PEM到PKCS8格式
https://stackoverflow.com/questions/8290435/convert-pem-traditional-private-key-to-pkcs8-private-key
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in private.pem -out private.pem.pkcs8

JAVA读取private、public key文件
https://stackoverflow.com/questions/11787571/how-to-read-pem-file-to-get-private-and-public-key
