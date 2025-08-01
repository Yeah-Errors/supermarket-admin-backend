package org.yaojiu.supermarket.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yaojiu.supermarket.interceptor.PermissionInterceptor;

@Configuration(proxyBeanMethods = false)
public class InterceptorConfigure implements WebMvcConfigurer {

    public static final String[] INTERCEPTOR_EXCLUDE_PATHS = new String[]{
            "/",
            "/user/login",
            "/user/logout",
            "/user/reg"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermissionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(INTERCEPTOR_EXCLUDE_PATHS);
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }
}
