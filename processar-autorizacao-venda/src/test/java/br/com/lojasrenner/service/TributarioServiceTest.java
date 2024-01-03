package br.com.lojasrenner.service;

import br.com.lojasrenner.client.tributario.APITributario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TributarioServiceTest {

    @InjectMocks
    private TributarioService service;

    @Mock
    private APITributario apiTributario;

    @Test
    void deveConsultarApiTributariaComSucesso() {
        final String sku = randomNumeric(9);

        service.getBySKU(sku);

        verify(apiTributario).getBySKU(sku);
    }
}