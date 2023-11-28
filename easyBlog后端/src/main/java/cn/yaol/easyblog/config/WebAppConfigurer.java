package cn.yaol.easyblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author yao
 * @Description 用于解决跨域的问题
 * @Date 2023/8/31 22:48
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE","OPTIONS")
                .maxAge(3600)
                .allowedHeaders("*");
    }

    // 用户头像 前面为请求路径 后面为映射到硬盘中的位置
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/cover/**").addResourceLocations("file:E:\\IdeaProject\\easyblog\\src\\main\\resources\\images\\cover\\");
        // 新增content文件夹的配置
        registry.addResourceHandler("/images/content/**").addResourceLocations("file:E:\\IdeaProject\\easyblog\\src\\main\\resources\\images\\content\\");
        // 新增avatar文件夹位置
        registry.addResourceHandler("/images/avatar/**").addResourceLocations("file:E:\\IdeaProject\\easyblog\\src\\main\\resources\\images\\avatar\\");
    }
}
