package com.psh.mybook.config;

import com.psh.mybook.utill.LoginMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(new LoginMemberArgumentResolver());

    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .order(1)
//                .addPathPatterns()
//                .excludePathPatterns();
//
//        registry.addInterceptor(new AdminInterceptor())
//                .order(2)
//                .addPathPatterns()
//                .excludePathPatterns();
//    }
}
