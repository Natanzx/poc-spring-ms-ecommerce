package br.com.lojasrenner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ProcessarAutorizacaoVendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessarAutorizacaoVendaApplication.class, args);
	}

}
