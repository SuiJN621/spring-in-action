package tacos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tacos.controller.general_api.HomeController;

/**
 * @author Sui
 * @date 2019.04.16 13:58
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 只提供视图解析简单功能的controller可以通过如下方式注册到dispatcher servlet
     * @see HomeController
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/user/login").setViewName("login");
        registry.addViewController("/login").setViewName("login");
    }
}
