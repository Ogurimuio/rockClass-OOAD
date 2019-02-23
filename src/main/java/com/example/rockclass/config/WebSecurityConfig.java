package com.example.rockclass.config;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/*
安全配置类
这里设置了禁止访问所有地址
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    private String url="/user/login";

    @Bean
    public LoginFilter loginFilter()throws Exception{
        return new LoginFilter(url,authenticationManager());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义登录认证
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }



    /**
     * 权限配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // 关闭 csrf 与 session

                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //解决不能加载iframe问题
                .and().headers().frameOptions().disable()
                // 添加权限控制
                .and().authorizeRequests()
                .antMatchers("/css/**", "/js/**","/images/**", "**/favicon.ico","/icon/**", "/*.html","/page/*.html").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/websocket").permitAll()
                .antMatchers(HttpMethod.GET,"/user/password").permitAll()
                .anyRequest().authenticated()
                // 添加 JWT 过滤器，对用户进行权限认证
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
                //.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}
