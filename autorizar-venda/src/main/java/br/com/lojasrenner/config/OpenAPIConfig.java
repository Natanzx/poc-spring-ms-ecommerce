package br.com.lojasrenner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${server.address}")
    private String host;

    @Value("${server.port}")
    private Integer port;

    @Value("${server.ssl.enabled}")
    private Boolean isHttps;

    @Value("${spring.profiles.active}")
    private String activeProfile;


    @Bean
    public OpenAPI openAPIDefinition() {

        Info info = new Info()
                .title("API - Autorizar Venda")
                .version("1.0.0")
                .contact(descriptionContact())
                .description("Esta API reproduz a funcionalidade de Autorizar Venda.").termsOfService("http://www.termsofservice.url")
                .license(descriptionLicense());

        return new OpenAPI().info(info).servers(List.of(getServer()));
    }

    private Contact descriptionContact() {
        return new Contact()
                .name("Natanael Dias Silva")
                .email("natanzx@gmail.com")
                .url("https://github.com/Natanzx");
    }

    private License descriptionLicense() {
        return new License()
                .name("License")
                .url("http://www.license.url");
    }

    private Server getServer() {
        Server devServer = new Server();
        devServer.setUrl(String.format("%s://%s:%d", isHttps ? "https" : "http", host, port));
        devServer.setDescription("Server URL in " + activeProfile + " environment");
        return devServer;
    }
}