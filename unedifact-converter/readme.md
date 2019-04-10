# 使用说明

+ 功能:
    + 当第一个参数为0时，XML -> DB
    + 当第一个参数为1时, DB -> XML
    
+ 参数
    + 公共参数
        + 第一个参数: 0或1
        + 第二个参数: EDI_FILE_NAME 
        + 第三个参数: XML_FILE_NAME
    + EDI -> XML
        + 第四个参数(必填): EDIFACT报文的版本，如d97a, d07a(BMW用)， 目前系统只支持这两个版本
    + 可选参数
        + 第五个参数: 用于xml->db进的control reference的值输入
       
+ 日志:
    + 存储位置取决于系统的环境变量(LOG_PATH)
    + 文件名(edi.log)
    + 有压缩机制（每一个文件）
    + 不自动清除
    
+ 示例     

    java -jar JAR_NAME ACTION EDI_FILE_NAME XML_FILE_NAME EDIFACT_VERSION CONTROL_REF
   
    ~~~
    java -jar converter-1.1.6.1.jar 0 /Users/tju/Desktop/testing/desadv/BULK.190313121436.21900010.txt a.xml d07a abc
    ~~~
   
+ 环境
   + JDK8,下载地址: [https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html]
