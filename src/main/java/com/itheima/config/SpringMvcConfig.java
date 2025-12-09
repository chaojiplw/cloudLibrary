package com.itheima.config;

import com.itheima.interceptor.ResourcesInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
@ComponentScan("com.itheima.controller")
@EnableWebMvc
@PropertySource("classpath:ignoreUrl.properties")
public class SpringMvcConfig implements WebMvcConfigurer {
    
    @Value("#{'${ignoreUrl}'.split(',')}") 
    private List<String> ignoreUrl;
    
    @Bean
    public ResourcesInterceptor resourcesInterceptor(){
        return new ResourcesInterceptor(ignoreUrl); 
    }
    
    // 配置静态资源访问
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
    }
    
    // 配置视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }
    
    // 配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(resourcesInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/login", "/user/login", "/css/**", "/js/**", "/img/**", "/fonts/**");
    }
    
    // 配置视图控制器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/login").setViewName("admin/login");
        registry.addViewController("/").setViewName("redirect:/admin/login");
    }
}