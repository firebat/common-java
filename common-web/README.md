## 如何使用
在启动入口类上增加注解

    @EnableJsonBody

应用将获得以下功能

## 接口规范
提供 @JsonBody 替换 @ResponseBody 标签，以自动实现HTTP接口规范化

    {
        "code": 0,  // 默认 成功时返回 0， 失败时返回 -1，可自定义
        "message": "通知消息",
        "data": "成功时的返回数据"
    }

如:

    @RequestMapping("/user/{name}")
    @JsonBody
    public Object get(@PathVariable String name) {
        return userService.getUser(name);
    }

将返回结果

    {
        "code": 0,
        "message": null,
        "data": {
            "name": "alice",
            ...
        }
    }


### 静态资源访问
由于`@JsonBodyConfiguration`间接继承了`@WebMvcConfigurationSupport`，使用时会禁用SpringBoot的一些自动配置，需要自己调整

    @SpringBootApplication
    @EnableJsonBody
    public class Application implements WebMvcConfigurer {

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**").addResourceLocations("classpath:/public/");
        }
    }
