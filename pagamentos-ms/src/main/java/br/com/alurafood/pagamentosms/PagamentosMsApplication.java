package br.com.alurafood.pagamentosms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PagamentosMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagamentosMsApplication.class, args);
	}

}
