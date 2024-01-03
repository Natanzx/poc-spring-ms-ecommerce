package br.com.lojasrenner.controller;

import br.com.lojasrenner.service.KafkaProducerService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE),
    basePackages = {
        "br.com.lojasrenner.controller",
    })
@MockBeans({
    @MockBean(KafkaProducerService.class),
})
public class ConfigControllerContext {

}
