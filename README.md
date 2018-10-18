# FastJson-JdbcRowSetImpl
JNDI加载RMI方式，附漏洞环境 &amp; 利用Exp。

## 环境构建

```
root@Bearcat:/# wget "https://github.com/iBearcat/FastJson-JdbcRowSetImpl/raw/master/FastJson_Vul.war" -P path.... & cd  ${path}/opt/apache-tomcat-8.5.24/bin & ./startup.sh"
```

![20181019](https://github.com/iBearcat/FastJson-JdbcRowSetImpl/blob/master/images/1.jpg?raw=true)

访问漏洞环境 http://localhost:8888/FastJson_Vul

![20181019](https://github.com/iBearcat/FastJson-JdbcRowSetImpl/blob/master/images/2.jpg?raw=true)
