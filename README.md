# spring-in-action
### ch1 
- Spring Boot构建于Spring之上，使Spring更加简单易用, 包括依赖关系管理，自动配置和运行期监控。

### ch2 开发web应用
- Spring提供了一个名为Spring MVC的强大Web框架，可用于为Spring应用程序开发Web前端。
- Spring MVC是基于注解的，可以使用@RequestMapping，@GetMapping和@PostMapping等注解声明方法来请求处理。
- 大多数请求处理方法通过返回视图的逻辑名称让请求转发（伴随所有数据）到该视图来结束处理。
- Spring MVC通过Java Bean Validation API和其实现（如Hibernate Validator）支持验证。
- 视图控制器可用于处理不需要模型数据或内部处理的GET请求。
- 除了Thymeleaf之外，Spring还支持各种模版，包括FreeMarker，Groovy模板和Mustache。

### ch3 处理数据
- Spring的JdbcTemplate大大简化了JDBC的使用。
- 当需要获取数据库生成的ID的值时，可以同时使用PreparedStatementCreator和KeyHolder。
- 为了方便执行数据插入，可以使用SimpleJdbcInsert。
- Spring Data JPA使JPA持久化操作简单到只需要编写持久层接口。

### ch4 保护应用
- Spring Security 自动配置是开始使用安全性的好方法，但是大多数应用程序都需要显式自定义配置，以满足其独特的安全性需求。
- 用户详细信息可以存储在关系数据库，LDAP，或者自定义的实现。
- Spring Security默认支持防御CSRF攻击。
- 经过验证的用户的信息可以通过SecurityContext对象(从SecurityContextHolder.getcontext()返回)获得，或者使用@AuthenticationPrincipal注入控制器。

### ch5 处理配置
- 可以使用@ConfigurationProperties注解类，以支持从属性源文件获得配置属性值。
- 可以在命令行参数、环境变量、JVM系统属性、属性文件或YAML文件等选项中设置配置属性。
- 配置属性可用于覆盖自动配置设置的属性，包括指定数据库URL和日志级别等功能。
- Spring配置文件可以与属性源一起使用，根据激活的配置文件有条件地设置配置属性。

### ch6 构建REST服务
- REST服务接口可以使用Spring MVC创建，其中的控制器遵循与以浏览器为目标的控制器相同的编程模型。
- 控制器中处理请求的方法可以使用@ResponseBody进行注解，也可以返回ResponseEntity对象，从而绕过模型视图解析器，直接向响应体写入或从响应体获取数据。
- @RestController注释简化了REST控制器的创建，不需要对处理请求的方法使用@ResponseBody注解。
- Spring HATEOAS支持在Spring MVC控制器返回数据中添加资源的超链接。
- Spring Data REST可以自动为Spring Repository创建REST服务接口。

### ch7 连接REST服务
- 客户端可以使用RestTemplate对REST api发出HTTP请求。
- Traverson允许客户端使用包含在响应中的超链接来导航API。





