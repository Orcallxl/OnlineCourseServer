package errortocorrect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by orca on 2019/3/31.
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket config(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("errortocorrect.controller"))
                .build();
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("在线课程系统API文档")
                .contact(new Contact("Dave","url","hnllxl"))
                .build();
    }
}
