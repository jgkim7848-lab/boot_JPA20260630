package com.example.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    String uploadPath = "file:///C:\\web_260315_kim\\_myProject\\java\\_fileUpload\\";
    //이 경로는 파일 입출력용이라는 뜻. file::과 ///의 의미....경로 \\ 왜 이거 2개씩 붙었는가.

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(uploadPath);  //내가 upload 라고 쓰면  위의 경로로 연결해달라고 한것.
    }

}
