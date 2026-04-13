# OISYS

OISYS 是一个基于 Spring Boot 的教学管理后端系统，包含学生管理、班级管理、课程管理、考勤管理、用户权限管理等模块。项目使用 MySQL 存储数据，MyBatis/MyBatis-Plus 进行数据访问。

## 技术栈

- Java 8
- Spring Boot 2.7.1
- Spring MVC
- MyBatis + MyBatis-Plus
- PageHelper
- MySQL
- Maven

## 项目结构

```text
oisys
├─ src/main/java/cn/njcit
│  ├─ controller        # 接口层
│  ├─ service           # 业务层
│  ├─ mapper            # 持久层接口
│  ├─ entity            # 实体类
│  ├─ dto               # DTO
│  ├─ config/filter     # 配置与过滤器
│  └─ exception/util    # 异常与通用返回
├─ src/main/resources
│  ├─ application*.properties
│  └─ mapper/*.xml      # SQL 映射
└─ pom.xml
```

## 功能模块

- 登录认证
  - 登录：`POST /login`
  - 当前用户：`GET /currentUser`
  - 退出登录：`GET /logout`
- 学生管理：`/studentManage/*`
- 班级管理：`/classManage/*`
- 课程管理：`/courseMan/*`
- 排课管理：`/courseManage/*`
- 考勤管理：`/attendance/*`
- 考勤设置：`/attendClassSetting/*`
- 后台用户管理：`/userManage/*`
- 角色管理：`/roleManage/*`
- 菜单管理：`/menuManage/*`
- 登录记录：`/userLoginRecord/*`

## 运行环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+/8.0+

## 配置说明

主配置文件位于 `src/main/resources/application.properties`，默认启用 `dev` 环境：

```properties
spring.profiles.active=dev
```

开发环境配置位于 `application-dev.properties`，支持环境变量覆盖：

```properties
server.port=8086
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3308/oisys-ren?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:123456}
userImageRoot=${USER_IMAGE_ROOT:E:/oi-sys/static/image/}
```

测试环境配置位于 `application-test.properties`，建议通过 CI/CD 或启动参数注入变量：

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `USER_IMAGE_ROOT`

## 启动方式

### 1) 使用 Maven 启动

```bash
mvn clean spring-boot:run
```

### 2) 打包后运行

```bash
mvn clean package -DskipTests
java -jar target/oisys.jar
```

服务默认端口：`8086`

## 关键接口示例

### 登录

```http
POST /login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

### 学生列表（分页）

```http
GET /studentManage/list?pageNum=1&pageSize=10
```

### 课程管理列表（分页）

```http
GET /courseManage/list?pageNum=1&pageSize=10
```

## 常见问题

1. 启动时报数据库连接失败  
   - 检查 MySQL 是否启动  
   - 检查 `DB_URL/DB_USERNAME/DB_PASSWORD` 是否正确  
   - 检查数据库端口与防火墙

2. 访问接口返回未授权  
   - 检查是否已登录并携带会话信息  
   - 检查请求路径是否命中权限保护规则

3. 图片上传或读取异常  
   - 检查 `userImageRoot` 路径是否存在、是否有读写权限

## 开发建议

- 为新接口统一返回 `ResponseResult`
- 控制器仅做参数校验与转发，复杂逻辑放在 Service
- Mapper XML 与实体字段保持一致，避免命名不匹配
- 新增模块时优先补充权限路径与菜单配置
