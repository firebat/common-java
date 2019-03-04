### 部署到仓库

    mvn deploy \
     -Dreleases.repo=http://<nexus-server>/nexus/content/repositories/releases \
     -Dsnapshots.repo=http://<nexus-server>/nexus/content/repositories/snapshots

### common-api
- 接口定义
- JSON处理工具

### common-core
- 监控工具
- health check

### common-jdbc
数据访问支持包

### common-lang
- 编解码处理
- 序列号生成
- 命名线程和线程池
- 字典树
- 金额运算
- 精简日期

### common-rpc
- Rest接口访问

### common-web
- 接口标准化
- i18n支持
