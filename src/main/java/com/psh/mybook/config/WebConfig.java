package com.psh.mybook.config;

import com.psh.mybook.interceptor.AdminInterceptor;
import com.psh.mybook.interceptor.LoginInterceptor;
import com.psh.mybook.utill.LoginMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(new LoginMemberArgumentResolver());

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/members/join/**",
                        "/members/login",
                        "/members/logout",
                        "/books/detail/{bookId}",
                        "/books/search",
                        "/images/display",
                        "/images/getAttachList",
                        "/home"
                );

        registry.addInterceptor(new AdminInterceptor())
                .order(2)
                .addPathPatterns(
                        "/books",
                        "/books/{bookId}",
                        "/images/upload",
                        "/orders/list"
                );
    }

}
