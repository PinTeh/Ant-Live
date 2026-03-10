<h1 align="center">Ant Live</h1>
<p align="center">基于SpringBoot开发的直播平台</p>
<p align="center">
    <img src="https://img.shields.io/github/license/mashape/apistatus.svg" alt="license">
    <img style="margin: 0 4px;" src="https://img.shields.io/badge/AntLive-0.0.1-blue" alt="EuBackend">
</p>

<strong>Ant Live</strong>是一套全部开源的前后端分离 Java EE 企业级快速开发平台，基于Java常见的技术栈`SpringBoot`、`MySQL`、`MyBatisPlus`等框架，实现一个直播平台，包括用户端直播推拉流、后台管理功能，给刚入门的开发人员学习参考。

- 演示地址：暂无

## 如何运行

- 克隆代码到本地 `git clone https://github.com/PinTeh/Ant-Live.git`
- 还原数据库 /resource/sql/ant-live.sql
- 修改配置文件application.yml，主要修改参数有数据库连接、redis连接字符串，尝试启动（按理说应该是可以启动了，默认账号`admin`，密码`123123`）
- 后续根据自己需求修改配置文件application.yml，主要参数有腾讯云sms（短信服务）live（直播服务）cos（对象存储）
- 另外对接了支付宝支付服务，根据需求在AlipayConfig类中配置商户私钥、公钥、应用ID、下载根证书配置
- 如有问题，可以联系我

## 启动前端服务
- 仓库地址 https://github.com/PinTeh/AntLive-Web
- 下载后执行 `npm install` 安装依赖
- 安装完依赖后，修改utils/request.js中的baseURL，改为自己的后端服务地址
- 执行`npm run dev`启动服务
- 访问localhost:8080 （默认，如修改了端口，自己根据实际情况修改）


## 直播服务
- 本项目对接的是腾讯云的直播云服务
- 如果有能力，可以自己搭建本地直播推流服务，可以参考livego、srs、lal等开源推流直播服务

## 开发环境
- JDK：8
- Maven：3.8+
- MySQL：5.7+
- Redis：6.2+

## 主框架选型
- `SpringBoot` 2.x
- `MybatisPlus` 3.5.x
- `Druid` 1.2.x
- `Knife4j` 2.0.x
- `Vue` 3.x
- `Flv.js` x.x

## 内置功能
1. 用户管理
2. 直播管理 
3. 角色管理 
4. 菜单管理 
5. 字典管理

## 技术交流群
[![加入QQ群](https://img.shields.io/badge/QQ群-276098367-blue.svg)]()

### 系统截图

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
