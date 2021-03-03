package br.com.jkassner.popula_resultados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages={"br.com.jkassner.*"})
@ComponentScan(basePackages={"br.com.jkassner.*"})
@EntityScan("br.com.jkassner.*")
public class PopulaResultadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PopulaResultadosApplication.class, args);
	}

}
