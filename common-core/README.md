## Monitor
使用说明

    Monitor.setValue(name, value);      // 单值
    Monitor.recordValue(name, value);   // 累计值 / 每分钟
    Monitor.recordTime(name, time);     // 累计时间和次数 / 每分钟

    // 输出通道，每分钟的前10秒，输出上一分钟数据
    Monitor.getWriters().add(writer);
    

Spring配置

    common:
      monitor:
        path: /monitor # 监控数据访问默认URL


使用Spring Boot应用启动后，可通过默认路径`/monitor`访问指标数据

### influxdb
添加依赖

    <dependency>
        <groupId>org.influxdb</groupId>
        <artifactId>influxdb-java</artifactId>
    </dependency>
    
并添加配置

    common:
      monitor
        influx:
          url: http://<influxdb-url>
          database:
          username:
          password:
          tags: 
            <name>: <value>
          proxy:
            type: HTTP | SOCKS
            host:
            port:
