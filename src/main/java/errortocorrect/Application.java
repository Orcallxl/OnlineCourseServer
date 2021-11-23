package errortocorrect;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@SpringBootApplication
@EnableSwagger2
@EnableJpaAuditing
public class Application {
    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
        };
    }
}
