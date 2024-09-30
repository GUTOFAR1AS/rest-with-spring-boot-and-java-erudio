package br.com.erudio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // Ativando Content Negotiation via Query Parameter
        configurer
            .favorParameter(true) // Ativa o uso de query parameter
            .parameterName("mediaType") // Define o nome do parâmetro (pode ser 'format' ou 'mediaType')
            .ignoreAcceptHeader(true) // Ignora o cabeçalho 'Accept' e prioriza o query parameter
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(org.springframework.http.MediaType.APPLICATION_JSON) // Tipo de conteúdo padrão
            .mediaType("json", org.springframework.http.MediaType.APPLICATION_JSON) // Mapeia 'json' para JSON
            .mediaType("xml", org.springframework.http.MediaType.APPLICATION_XML); // Mapeia 'xml' para XML
    }
}
