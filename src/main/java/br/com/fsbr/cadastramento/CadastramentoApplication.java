package br.com.fsbr.cadastramento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSpringDataWebSupport // para paginação das consultas
@EnableCaching // para cache das consultas
@EnableSwagger2 // para documentação da API
public class CadastramentoApplication{

	public static void main(String[] args) {	
		
		SpringApplication.run(CadastramentoApplication.class, args);
		
	}

	
}
