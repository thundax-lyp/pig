# Bacon

`Bacon` 是一个基于 Java 17、Spring Boot 3.5、Spring Cloud 2025 和 Spring Cloud Alibaba 的多模块后端工程，支持微服务部署与单体聚合运行两种模式。仓库包含认证授权、网关、系统管理、代码生成、定时任务、监控以及一组可复用的公共基础模块。

## 技术栈

- Java 17
- Maven
- Spring Boot 3.5.11
- Spring Cloud 2025.0.1
- Spring Cloud Alibaba 2025.0.0.0
- Spring Authorization Server
- MyBatis-Plus
- Redis
- Nacos
- JetCache
- Docker Compose

## 仓库结构

```text
bacon
├── bacon-auth                    # 认证授权服务
├── bacon-boot                    # 单体模式启动入口
├── bacon-common                  # 公共基础模块
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
├── bacon-gateway                 # Spring Cloud Gateway 网关
├── bacon-register                # 注册中心与配置中心
├── bacon-upms                    # 用户、角色、菜单、字典等系统管理模块
│   ├── bacon-upms-api
│   ├── bacon-upms-biz
│   ├── bacon-upms-client
│   └── bacon-upms-contract
├── bacon-visual                  # 可视化与运维相关模块
│   ├── bacon-codegen
│   ├── bacon-codegen-contract
│   ├── bacon-monitor
│   ├── bacon-quartz
│   └── bacon-quartz-contract
├── db                            # 数据库初始化脚本与 MySQL 镜像构建目录
├── docker-compose.yml            # 本地容器编排
└── pom.xml                       # 根聚合 POM
```

## 模块说明

### 核心服务

- `bacon-register`：注册中心与配置中心，提供服务发现和统一配置管理。
- `bacon-gateway`：统一入口网关，处理路由、鉴权透传和网关层逻辑。
- `bacon-auth`：认证授权服务，负责登录、令牌签发和认证流程扩展。
- `bacon-upms-biz`：系统管理业务服务，提供用户、角色、菜单、部门、字典等能力。
- `bacon-monitor`：服务监控相关能力。
- `bacon-codegen`：代码生成服务。
- `bacon-quartz`：定时任务服务。

### 公共模块

- `bacon-common-*`：封装安全、日志、数据源、MyBatis、Swagger、对象存储、XSS、防腐层等公共能力。
- `bacon-upms-contract` / `bacon-upms-api` / `bacon-upms-client`：系统管理对外契约、接口定义与客户端调用封装。
- `bacon-codegen-contract` / `bacon-quartz-contract`：视觉模块的契约层定义。

### 单体模式

- `bacon-boot`：聚合主要业务模块，适合本地开发、联调和快速验证。

## 环境要求

- JDK 17
- Maven 3.9+ 或兼容版本
- MySQL 8.x
- Redis 6.x 或更高版本
- Docker / Docker Compose（可选，用于快速启动依赖和服务）

## 数据库初始化

初始化脚本位于 [db](db)：

- [bacon.sql](db/bacon.sql)：业务数据库初始化脚本
- [bacon_config.sql](db/bacon_config.sql)：配置中心数据库初始化脚本

常用数据库名：

- `bacon`
- `bacon_config`

## 本地开发

### 1. 编译与校验

```bash
mvn clean verify
```

只做快速测试：

```bash
mvn test
```

只执行代码格式化：

```bash
mvn spring-javaformat:apply
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

例如启动认证服务：

```bash
mvn -pl bacon-auth -am spring-boot:run
```

## Docker Compose

项目根目录提供了 [docker-compose.yml](docker-compose.yml)，可用于快速拉起本地联调环境：

```bash
docker compose up
```

默认编排的主要服务包括：

- `bacon-mysql`
- `bacon-redis`
- `bacon-register`
- `bacon-gateway`
- `bacon-auth`
- `bacon-upms`
- `bacon-monitor`
- `bacon-codegen`
- `bacon-quartz`

默认暴露的主要端口：

- `33306`：MySQL
- `36379`：Redis
- `8848`：Nacos
- `9848`：Nacos gRPC
- `9999`：Gateway
- `5001`：Monitor

## 运行模式

### 微服务模式

推荐按依赖关系启动：

1. `bacon-register`
2. `bacon-gateway`
3. `bacon-auth`
4. `bacon-upms-biz`
5. `bacon-monitor`
6. `bacon-codegen`
7. `bacon-quartz`

### 单体模式

使用 `bacon-boot` 聚合主要能力，适合本地开发和快速验证，不需要分别启动多个业务服务。

## 配置说明

- 各模块配置文件位于各自的 `src/main/resources` 目录。
- 数据库与配置中心初始化 SQL 位于 [db](db)。
- Docker 相关配置分布在模块目录下的 `Dockerfile` 以及根目录 [docker-compose.yml](docker-compose.yml)。

## 代码规范

- 使用 Java 17
- 使用 Spring Java Format 统一格式
- 提交前建议执行：

```bash
mvn spring-javaformat:apply
```

## 提交流程建议

- 提交信息推荐使用 `type(scope): imperative summary`
- 示例：`fix(gateway): handle missing tenant header`
- Pull Request 默认目标分支为 `dev`

## 许可证

项目许可证见 [LICENSE](LICENSE)。
