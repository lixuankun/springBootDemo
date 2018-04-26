//package com.config;
//
//import com.Interceptor.LoginInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * Created by lxk on 2018/4/3.
// */
//@Configuration
//public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
//    @Autowired
//    LoginInterceptor loginInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
//        super.addInterceptors(registry);
//    }
//}
