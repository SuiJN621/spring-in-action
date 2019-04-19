package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * - @SpringBootApplication注解包含了
 * 1.@SpringBootConfiguration:@Configuration功能相同
 * 2.@EnableAutoConfiguration:开启自动配置
 * 3.@ComponentScan
 */
@SpringBootApplication
public class TacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacosApplication.class, args);
    }
}
