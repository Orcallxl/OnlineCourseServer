package errortocorrect;

import errortocorrect.interceptor.LoginRequiredInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new LoginRequiredInterceptor());
        registration.addPathPatterns("/**").excludePathPatterns(
                "/user/login",
                "/user/register",
                "/user/log_out",
                "/subject/all-subject-latest",
                "/subject/all-subject-hottest",

                "/**/*.html",                //html静态资源
                "/**/*.js",                  //js静态资源
                "/**/*.css",
                "/**/*.png",
                "/**/*.jpg",
                "/**/*.jpeg"
        );
        registration.excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        WebMvcConfigurer.super.addInterceptors(registry);

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
