# Ant Live 
Ant Live 是一个直播平台。集成了一系列杂七杂八的功能😁，没有什么技术难点。本人小白一枚，实现逻辑或实现代码可能存在部分问题，希望大佬能多多指教😉。



## 如何运行

- 克隆代码到本地 `git clone https://github.com/PinTeh/Ant-Live.git`
- 还原数据库 /resource/sql/ant-live.sql
- 修改配置文件application.yml，主要修改参数有数据库连接、redis连接字符串，尝试启动（按理说应该是可以启动了，默认账号`admin`，密码`123123`）
- 后续根据自己需求修改配置文件application.yml，主要参数有腾讯云sms（短信服务）live（直播服务）cos（对象存储）
- 另外对接了支付宝支付服务，根据需求在AlipayConfig类中配置商户私钥、公钥、应用ID、下载根证书配置
- 如有问题，可以联系我

## 启动前端服务
- 仓库地址 https://github.com/PinTeh/AntLive-Pre
- 下载后执行 `npm install` 安装依赖
- 安装完依赖后，修改utils/request.js中的baseURL，改为自己的后端服务地址
- 执行`npm run serve`启动服务
- 访问localhost:8080 （默认，如修改了端口，自己根据实际情况修改）


## 直播服务
- 本项目对接的是腾讯云的直播云服务
- 如果有能力，可以自己搭建本地直播推流服务，可以参考livego、srs、lal等开源推流直播服务

## 如有问题可以联系

![qrcode](http://image.imhtb.cn/qrCode2.png?imageMogr2/thumbnail/!30p)


### Preview


- [演示地址](http://www.imhtb.cn) 已失效




### Support


- 腾讯云
   - 直播云服务
   - 鉴黄服务
   - 存储服务
   - 短信服务



- 蚂蚁金服
   - 支付宝支付提现服务



- 阿里云
   - Centos服务器





### Others


- SpringBoot
- Redis
- Mybatis Plus
- Vue
- Element UI
- Axios
- AntV
- SpringSecurity
- Jwt
- Flv.js
- Docker
- MySQL





### Snapshots


- 首页

![image.png](https://cdn.nlark.com/yuque/0/2020/png/297773/1593835018960-7345cd28-950b-48b4-88c9-c43208cf78d3.png#align=left&display=inline&height=352&margin=%5Bobject%20Object%5D&name=image.png&originHeight=902&originWidth=1913&size=251257&status=done&style=shadow&width=746)


- 直播

![image.png](https://cdn.nlark.com/yuque/0/2020/png/297773/1593835100616-1f698da7-5b6a-4636-a3a8-2b67c7730e6b.png#align=left&display=inline&height=451&margin=%5Bobject%20Object%5D&name=image.png&originHeight=902&originWidth=1893&size=70038&status=done&style=shadow&width=946.5)


- 个人中心

![image.png](https://cdn.nlark.com/yuque/0/2020/png/297773/1593835162609-984d4ab2-9fbd-4270-a462-350d174cd8de.png#align=left&display=inline&height=451&margin=%5Bobject%20Object%5D&name=image.png&originHeight=901&originWidth=1888&size=100716&status=done&style=shadow&width=944)


- 后台



![image.png](https://cdn.nlark.com/yuque/0/2020/png/297773/1593835199807-f4ed4b37-c07e-4a44-8c00-daa9630e8bb6.png#align=left&display=inline&height=455&margin=%5Bobject%20Object%5D&name=image.png&originHeight=910&originWidth=1916&size=79335&status=done&style=shadow&width=958)


- 统计



![image.png](https://cdn.nlark.com/yuque/0/2020/png/297773/1593842619713-99982461-85c7-4944-93f0-8a74e18d3791.png#align=left&display=inline&height=365&margin=%5Bobject%20Object%5D&name=image.png&originHeight=729&originWidth=1576&size=67123&status=done&style=shadow&width=788)


- 认证



![image.png](https://cdn.nlark.com/yuque/0/2020/png/297773/1593842676048-6deb047a-a6dc-4a43-8acf-085d1868a5fa.png#align=left&display=inline&height=414&margin=%5Bobject%20Object%5D&name=image.png&originHeight=668&originWidth=1204&size=49223&status=done&style=shadow&width=746)


- 监控



![image.png](https://cdn.nlark.com/yuque/0/2020/png/297773/1593842793536-a2c1d037-5ce8-4ad4-ae3d-c7f41ba475cd.png#align=left&display=inline&height=291&margin=%5Bobject%20Object%5D&name=image.png&originHeight=645&originWidth=1654&size=65855&status=done&style=shadow&width=746)

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=PinTeh/Ant-Live&type=Date)](https://star-history.com/#PinTeh/Ant-Live&Date)
