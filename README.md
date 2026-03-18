# Bacon

`Bacon` 是一个基于 Spring Boot 3.5、Spring Cloud 2025 和 Spring Authorization Server 的后端多模块工程，支持微服务部署和单体部署两种运行方式。

## 技术栈

- Java 17
- Spring Boot 3.5.11
- Spring Cloud 2025.0.1
- Spring Cloud Alibaba 2025.0.0.0
- Spring Authorization Server 1.5.2
- MyBatis-Plus 3.5.15
- Redis
- Nacos
- JetCache 2.7.7

## 工程结构

```text
bacon
├── bacon-boot                # 单体启动入口
├── bacon-auth                # 认证授权服务
├── bacon-gateway             # 网关服务
├── bacon-register            # Nacos 注册与配置中心
├── bacon-upms                # 用户、角色、菜单、字典等系统管理能力
│   ├── bacon-upms-contract   # DTO / VO / 契约对象
│   ├── bacon-upms-api        # 对外服务接口
│   ├── bacon-upms-client     # Feign 客户端
│   └── bacon-upms-biz        # 业务实现
├── bacon-common              # 公共基础模块
│   ├── bacon-common-bom
│   ├── bacon-common-core
│   ├── bacon-common-datasource
│   ├── bacon-common-excel
│   ├── bacon-common-feign
│   ├── bacon-common-log
│   ├── bacon-common-mybatis
│   ├── bacon-common-oss
│   ├── bacon-common-seata
│   ├── bacon-common-security
│   ├── bacon-common-swagger
│   ├── bacon-common-websocket
│   └── bacon-common-xss
├── bacon-visual              # 可视化能力
│   ├── bacon-codegen
│   ├── bacon-codegen-contract
│   ├── bacon-monitor
│   ├── bacon-quartz
│   └── bacon-quartz-contract
├── db                        # 初始化 SQL 与数据库镜像构建文件
├── docker-compose.yml        # 本地容器编排
└── pom.xml                   # 根聚合 POM
```

## 运行模式

### 微服务模式

核心服务包括：

- `bacon-register`
- `bacon-gateway`
- `bacon-auth`
- `bacon-upms`
- `bacon-monitor`
- `bacon-codegen`
- `bacon-quartz`

### 单体模式

使用 `bacon-boot` 聚合认证、系统管理、代码生成、定时任务等模块，适合本地开发和快速联调。

## 环境要求

- JDK 17
- Maven 3.9+ 或兼容版本
- MySQL 8.x
- Redis 6.x 或更高版本

## 数据库初始化

数据库脚本位于 [db](/Volumes/storage/workspace/sample/pig/db)：

- [bacon.sql](/Volumes/storage/workspace/sample/pig/db/bacon.sql)：业务库初始化脚本
- [bacon_config.sql](/Volumes/storage/workspace/sample/pig/db/bacon_config.sql)：Nacos 配置库初始化脚本

默认数据库名：

- `bacon`
- `bacon_config`

## 本地开发

### 1. 编译工程

```bash
mvn clean verify
```

只做编译校验时可以使用：

```bash
mvn -q -DskipTests compile
```

### 2. 启动单体模式

```bash
mvn -Pboot -pl bacon-boot -am spring-boot:run
```

### 3. 启动单个微服务

例如启动网关：

```bash
mvn -pl bacon-gateway -am spring-boot:run
```

## Docker 启动

工程提供了本地容器编排文件 [docker-compose.yml](/Volumes/storage/workspace/sample/pig/docker-compose.yml)。

启动：

```bash
docker compose up
```

默认包含的主要容器：

- `bacon-mysql`
- `bacon-redis`
- `bacon-register`
- `bacon-gateway`
- `bacon-auth`
- `bacon-upms`
- `bacon-monitor`
- `bacon-codegen`
- `bacon-quartz`

## 关键配置

### 开发配置

单体开发配置位于：

- [application-dev.yml](/Volumes/storage/workspace/sample/pig/bacon-boot/src/main/resources/application-dev.yml)

当前默认使用：

- Jasypt 根密码：`bacon`
- JetCache + Redis 远程缓存
- Jackson 作为主 JSON 序列化方案

### Nacos 配置

Nacos 服务配置初始化内容在：

- [bacon_config.sql](/Volumes/storage/workspace/sample/pig/db/bacon_config.sql)

## 代码规范

- 使用 Java 17
- 提交前建议执行：

```bash
mvn spring-javaformat:apply
```

- Java 包命名当前采用 `com.github.thundax.bacon.*`
- Maven `groupId` 当前采用 `com.github.thundax`

## 测试

快速执行测试：

```bash
mvn test
```

完整验证：

```bash
mvn clean verify
```

## 提交建议

- 推荐提交信息格式：`type(scope): summary`
- 例如：`fix(gateway): handle missing tenant header`
- 默认 PR 目标分支：`dev`
