package per.cby.frame.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 * 
 * @author chenboyang
 *
 */
@EnableSwagger2
@ConditionalOnWebApplication
@Configuration("__SWAGGER_CONFIG__")
@ConditionalOnClass(name = "springfox.documentation.spring.web.plugins.Docket")
public class SwaggerConfig {

    /**
     * 获取swagger构建器
     * 
     * @return swagger构建器
     */
    @Bean
    @ConditionalOnMissingBean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
                .build();
    }

    /**
     * 获取应用程序接口信息
     * 
     * @return 应用程序接口信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("swagger 构建 RESTful API")
                // 创建人
                .contact(new Contact("chenboyang", "", ""))
                // 版本号
                .version("1.0")
                // 描述
                .description("API 描述").build();
    }

}
