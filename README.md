yunpian-java-sdk
================================
云片网推荐SDK（Java实现）

## 快速开始

- 添加Maven依赖

```
<dependency>
	<groupId>com.yunpian.sdk</groupId>
    <artifactId>yunpian-java-sdk</artifactId>
    <version>1.2.0</version>
</dependency>
```
**注**: [查找最新版](http://search.maven.org/#search%7Cga%7C1%7Cyunpian-java-sdk)

- 使用YunpianClient

```java
//初始化client,apikey作为所有请求的默认值(可以为空)
YunpianClient clnt = new YunpianClient("apikey").init();

//修改账户信息API
Map<String, String> param = clnt.newParam(3);
//param.put(APIKEY,"apikey"); 优先级高于构造器apikey
param.put(EMERGENCY_CONTACT, "dzh");
param.put(EMERGENCY_MOBILE, "11111111111");
param.put(ALARM_BALANCE, "10");
Result<UserInfo> r = clnt.user().set(param);

//账户 clnt.user().* 签名 clnt.sign().* 模版 clnt.tpl().* 短信 clnt.sms().* 语音 clnt.voice().* 流量 clnt.flow().* 隐私通话 clnt.call().*

//最后释放client
client.close() 
```
**注**: v1.2.0不兼容以前的sdk版本(但升级十分简单)，我们做了重新设计，改进性能、扩展性、便利性。。。

## 配置说明 (默认配置就行)

- 默认配置文件 src/main/resources/yunpian.properties
- 自定义配置方式
	- 构造器方式，如`new YunpianClient(String apikey, String file)`
	- 系统属性，如`-Dyp.apikey=apikey -Dyp.file=配置文件路径`
- apikey的优先级 接口级 > 默认值(YunpianConf.getApikey())

## 源码说明 yunpian-java-sdk
- 工程使用maven构造，jdk1.7 or higher
- 开发可参考单元测试 test/com.yunpian.sdk.api
- 不推荐使用标注@Deprecated类

## 联系我们
[技术支持 QQ](https://static.meiqia.com/dist/standalone.html?eid=30951&groupid=0d20ab23ab4702939552b3f81978012f&metadata={"name":"github"})

## 文档链接
- sdk说明
- [api文档](https://www.yunpian.com/api2.0/guide.html)

