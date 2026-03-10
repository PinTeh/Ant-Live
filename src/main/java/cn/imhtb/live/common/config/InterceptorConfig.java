package cn.imhtb.live.common.config;

import cn.imhtb.live.common.interceptor.CollectInterceptor;
import cn.imhtb.live.common.interceptor.TokenInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 认证拦截器配置
 *
 * @author PinTeh
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    @Bean
    @ConditionalOnBean(TokenInterceptor.class)
    public CollectInterceptor collectInterceptor() {
        return new CollectInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(collectInterceptor())
                .addPathPatterns("/**");
    }

}
