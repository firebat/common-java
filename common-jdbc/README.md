### Configuration

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
    
    // Create @ShardKey aspect handler
    @Bean
    public ShardKeyAspect shardKeyAspect() {
        return new ShardKeyAspect();
    }

### Usage

    @GetMapping("order_log")
    @JsonBody
    @ShardKey("slave")  // Use annotation
    public Object orderLog(@RequestParam String orderNo) {
    
        // or ShardKeyStore.set("slave");
    }
