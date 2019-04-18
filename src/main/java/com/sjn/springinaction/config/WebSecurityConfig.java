package com.sjn.springinaction.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Sui
 * @date 2019.04.17 16:12
 */
@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //private final DataSource dataSource;
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(DataSource dataSource, @Qualifier("userServiceImpl") UserDetailsService
            userDetailsService) {
        //this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置http请求拦截规则, 拦截器等
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //获取ExpressionInterceptUrlRegistry, 用来配置具体规则
                .authorizeRequests()
                .antMatchers("/design", "/orders")
                //access([Spring EL])方法支持更灵活配置
                //配置权限要求, 不能以ROLE_开头
                .hasRole("USER")
                .antMatchers("/", "/**").permitAll()
                .and()
                //自定义登录路径, 授权路径, 用户名/密码参数
                .formLogin().loginPage("/user/login").loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/design")
                //默认登出后返回登录页面
                .and().logout().logoutSuccessUrl("/");
    }

    /**
     * 配置授权认证方法, 创建AuthenticationManager对象
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                //设置密码加密
                .passwordEncoder(bCryptPasswordEncoder());

        /**
         * LDAP配置, 略
         */

        /**
         * jdbc验证配置, 自定义授权sql等
         */
        /*auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                "select username, password, enabled from Users " +
                        "where username=?")
                .authoritiesByUsernameQuery(
                        "select username, authority from UserAuthorities " +
                                "where username=?")
                .passwordEncoder(new BCryptPasswordEncoder());*/
        /**
         * in memeory用户配置, 通常用来测试
         */
        /*auth.inMemoryAuthentication()
                .withUser("user")
                .password("user")
                .authorities("ROLE_USER")
                .and()
                .withUser("admin")
                .password("admin")
                .authorities("ROLE_USER");*/
    }
}
