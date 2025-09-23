package br.com.fiap.qhealth.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI qhealthOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("QHealth - Atendimento Finalizado API")
                        .description("Documentação da API de Atendimentos Finalizados no QHealth")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("7ADJT - Equipe QHealth")
                                .email("")
                                .url(""))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório GitHub - QHealth")
                        .url("https://github.com/larissahsantossilva/7ADJT-QHealth-atendimento-finalizado-service"));
    }
}
