## 如何使用
在启动入口类上增加注解

    @EnableMonitor

### 使用编码

    Monitor.setValue(name, value);      // 单值
    Monitor.recordValue(name, value);   // 累计值 / 每分钟
    Monitor.recordTime(name, time);     // 累计时间和次数 / 每分钟

    // 输出通道，每分钟的前10秒，输出上一分钟数据
    Monitor.getWriters().add(writer);

### 使用注解


    @Timed("hello")
    public Object hello(String name) {
        return name;
    }

    # 将产生如下指标数据
    hello_Count=9
    hello_Duration=1
    
    # 未指定指标名时格式
    <class-name>.hello(..)_Count=1
    <class-name>.hello(..)_Duration=0

### 查看指标
使用Spring Boot应用启动后，可通过默认路径`/monitor`访问指标数据，可通过Spring配置定制

    common:
      monitor:
        path: /monitor # 监控数据访问默认URL

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

默认tags如下:
- endpoint 取值为`${server.address:localhost}_${server.port:8080}`

需附加tag时使用类似`--common.monitor.influx.tags.hostname=$HOSTNAME`方式注入
s