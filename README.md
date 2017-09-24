yunpian-java-sdk
================================
[云片](https://www.yunpian.com/) SDK

## 快速开始

- 添加Maven依赖

```xml
<dependency>
    <groupId>com.yunpian.sdk</groupId>
    <artifactId>yunpian-java-sdk</artifactId>
    <version>1.2.5</version>
</dependency>
```
**注**: 可以在[Maven](http://search.maven.org/#search%7Cga%7C1%7Cyunpian-java-sdk)获取

- 使用YunpianClient

```java
//初始化clnt,使用单例方式
YunpianClient clnt = new YunpianClient("apikey").init();

//发送短信API
Map<String, String> param = clnt.newParam(2);
param.put(YunpianClient.MOBILE, "18616020***");
param.put(YunpianClient.TEXT, "【云片网】您的验证码是1234");
Result<SmsSingleSend> r = clnt.sms().single_send(param);
//获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()

//账户:clnt.user().* 签名:clnt.sign().* 模版:clnt.tpl().* 短信:clnt.sms().* 语音:clnt.voice().* 流量:clnt.flow().* 隐私通话:clnt.call().*

//释放clnt
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
- YunpianClient使用单例方式，不要每次new和close
- 不推荐使用标注@Deprecated类
- 分支说明: master是发布版本,develop是待发布的分支(开源贡献可以pull request到develop)

## 联系我们
[云片支持 QQ](https://static.meiqia.com/dist/standalone.html?eid=30951&groupid=0d20ab23ab4702939552b3f81978012f&metadata={"name":"github"})

SDK开源QQ群

<img src="doc/sdk_qq.jpeg" width="15%" alt="SDK开源QQ群"/>

## 文档链接
- [api文档](https://www.yunpian.com/api2.0/guide.html)

