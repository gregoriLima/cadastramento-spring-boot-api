package br.com.fsbr.cadastramento.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fsbr.cadastramento.model.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {

    // http://localhost:8080/swagger-ui.html
    @Bean
    public Docket forumApi() {
	
	return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.basePackage("br.com.fsbr.cadastramento"))
		.paths(PathSelectors.ant("/**"))
		.build()
		.ignoredParameterTypes(Usuario.class) // ignorando urls que trabalhem com a classe usuáro
		.globalOperationParameters(Arrays.asList(
                                new ParameterBuilder()
                                    .name("Authorization")
                                    .description("Header para Token JWT") 
                                    .modelRef(new ModelRef("string"))
                                    .parameterType("header") 
                                    .required(false)
                                    .build()));
    }
    
}
