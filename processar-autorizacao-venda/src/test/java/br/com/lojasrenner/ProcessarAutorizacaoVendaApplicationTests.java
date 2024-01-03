package br.com.lojasrenner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProcessarAutorizacaoVendaApplicationTests {

	@Test
	void contextLoads() {
		new ProcessarAutorizacaoVendaApplication().main(new String[]{"--spring.profiles.active=test"});
	}

}
