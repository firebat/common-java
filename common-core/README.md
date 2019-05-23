## Health Check

    common:
        health:
            url: "/healthcheck.html" # 检测地址
            file: <local-file>       # 本地文件, 存在时检测返回200 否则404


## 探针

    common:
        server:
            env: "/system/env.html"
