package br.com.lojasrenner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AutorizarVendaApplicationTests {

    @Test
    void contextLoads() {
        new AutorizarVendaApplication().main(new String[]{"--spring.profiles.active=test"});
    }

}
