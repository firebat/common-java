### 部署到仓库

    # deploy to your repository
    mvn source:jar deploy \
     -Dreleases.repo=http://<your-nexus-server>/nexus/content/repositories/releases \
     -Dsnapshots.repo=http://<your-nexus-server>/nexus/content/repositories/snapshots


    # add dependencies to your pom.xml
    <dependency>
        <groupId>common</groupId>
        <artifactId>common-dependencies</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>

### common-api
- 接口定义
- JSON处理工具

### common-core
- 监控工具
- health check

### common-jdbc
- 多数据源切换
- Mybatis支持包

### common-lang
- 编解码处理
- 序列号生成
- 命名线程和线程池
- 字典树
- 金额运算
- 精简日期

### common-rpc
- Rpc配置化
- Rest便捷封装 (http / https)

### common-security
- Authorization Token访问

### common-web
- @JsonBody 接口标准化
- 服务器探针
- i18n支持
