### Rest RPC
标准Rest接口调用封装，支持软DNS和接口校验

    # application.yml

    rpc:
      dns:
        dev.example.com:
         - 127.0.0.1

      service:
        demo-service:
          url: 'http://dev.example.com:7001'
          proxy:
            type: SOCKS
            host: xxx
            port: 1234

通过继承`RestClient`实现对应的`GET/POST`请求

    @Service
    @ConditionalOnProperty("rpc.service.demo-service.url")
    public DemoClient extends RestClient / SecurityRestClient {
    
        private static TypeReference<User> TYPE_USER = ...;
        
        public DemoClient() {
            super("demo-service");
        }

        public User getUserById(String id) {
            return getService().get('api/user/', TYPE_USER, 'id', id);
        }
    }