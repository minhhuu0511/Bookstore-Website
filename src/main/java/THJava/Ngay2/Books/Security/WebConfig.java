package THJava.Ngay2.Books.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cấu hình để phục vụ các tệp từ thư mục img
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:D:/Project/Java/spring/LtringUdung_Java/JavaNgay6/src/main/resources/img/");
    }
}
