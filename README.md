yunpian-java-sdk
================================
[云片](https://www.yunpian.com/) SDK

## 快速开始

- 添加Maven依赖

```xml
<dependency>
    <groupId>com.yunpian.sdk</groupId>
    <artifactId>yunpian-java-sdk</artifactId>
    <version>1.2.2</version>
</dependency>
```
**注**: master是最新稳定版，可以本地直接构建使用。我们尽快上传到[Maven](http://search.maven.org/#search%7Cga%7C1%7Cyunpian-java-sdk)

- 使用YunpianClient

```java
//初始化client,apikey作为所有请求的默认值(可以为空)
YunpianClient clnt = new YunpianClient("apikey").init();

//修改账户信息API
Map<String, String> param = clnt.newParam(3);
//param.put(APIKEY,"apikey"); 优先级高于构造器apikey
param.put(YunpianClient.EMERGENCY_CONTACT, "yunpian");
param.put(YunpianClient.EMERGENCY_MOBILE, "11111111111");
param.put(YunpianClient.ALARM_BALANCE, "10");
Result<User> r = clnt.user().set(param);

//账户 clnt.user().* 签名 clnt.sign().* 模版 clnt.tpl().* 短信 clnt.sms().* 语音 clnt.voice().* 流量 clnt.flow().* 隐私通话 clnt.call().*

//最后释放client
clnt.close() 
```
**注**: v1.2开始使用YunpianClient，做了重新设计，改进性能、扩展性、便利性等。兼容v1.1.*版本，YunpianRestClient暂时保留,请尽快升级。

## 配置说明 (默认配置就行)

- 默认配置文件 src/main/resources/yunpian.properties
- 自定义配置方式
	- 构造器方式，如`new YunpianClient(String apikey, String file)`
	- 系统属性，如`-Dyp.apikey=apikey -Dyp.file=配置文件路径`
- apikey的优先级 接口级 > 默认值(YunpianConf.getApikey())

## 源码说明 yunpian-java-sdk
- 工程使用maven构造，jdk1.7 or higher
- 开发API可参考单元测试 test/com.yunpian.sdk.api
- 不推荐使用标注@Deprecated类

## 联系我们
[云片支持 QQ](https://static.meiqia.com/dist/standalone.html?eid=30951&groupid=0d20ab23ab4702939552b3f81978012f&metadata={"name":"github"})

SDK开源QQ群

<img src="doc/sdk_qq.jpeg" width="15%" alt="SDK开源QQ群"/>

## 文档链接
- [api文档](https://www.yunpian.com/api2.0/guide.html)

