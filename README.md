# OISYS

OISYS 是一个前后端分离的教务/考勤管理系统项目，包含 Spring Boot 后端与 Vue2 前端。

## 项目结构

```text
oisys/
├─ src/                  # 后端源码（Spring Boot）
├─ frontend/             # 前端源码（Vue2 + Element UI）
├─ oisys-ren.sql         # 数据库初始化脚本
└─ pom.xml               # 后端 Maven 配置
```

## 技术栈

### 后端
- Java 8
- Spring Boot 2.7.1
- MyBatis / MyBatis-Plus
- MySQL
- Maven

### 前端
- Vue 2
- Vue Router
- Vuex
- Element UI
- Axios

## 环境要求

- JDK 1.8+
- Maven 3.6+
- Node.js 16+（建议）
- npm 8+（建议）
- MySQL 5.7/8.0

## 快速开始

### 1. 初始化数据库

将 `oisys-ren.sql` 导入到 MySQL，创建项目所需表结构与基础数据。

### 2. 配置后端

修改后端配置文件中的数据库连接信息：

- `src/main/resources/application.properties`
- `src/main/resources/application-dev.properties`
- `src/main/resources/application-test.properties`

### 3. 启动后端

在仓库根目录执行：

```bash
mvn spring-boot:run
```

默认启动后端服务（端口以配置文件为准）。

### 4. 启动前端

进入前端目录执行：

```bash
cd frontend
npm install
npm run serve
```

前端开发服务启动后，按终端提示访问页面地址。

## 构建部署

### 后端打包

```bash
mvn clean package
```

### 前端构建

```bash
cd frontend
npm run build
```

构建产物位于 `frontend/dist`。
