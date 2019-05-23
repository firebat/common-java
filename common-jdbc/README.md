## 多数据源
在应用入口类增加注解启用Shard功能

    @EnableShard

### 配置数据源

    // Configure ShardDataSource instance
    @Bean("dataSource")
    public DataSource dataSource(HikariConfig masterConfig, HikariConfig slaveConfig) {
        final DataSource master = new HikariDataSource(masterConfig);
        final DataSource slave = new HikariDataSource(slaveConfig);

        ShardDataSource dataSource = new ShardDataSource();
        dataSource.setDefaultTargetDataSource(master);
        dataSource.setTargetDataSources(ImmutableMap.of(
                "slave", slave
        ));

        return dataSource;
    }


### 标记
确认使用

    @GetMapping("order_log")
    @JsonBody
    @ShardKey("slave")
    public Object orderLog(@RequestParam String orderNo) {
    
        // or ShardKeyStore.set("slave");
    }
