package campus.randolh.gestion_gimnasio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Sistema de Gestion de Gimnasio")
                        .version("1.0.0")
                        .description("API REST para la gestion de clientes y rutinas con relacion Many-to-Many.")
                        .contact(new Contact()
                                .name("Randolh - Campus")
                                .email("estuardotecun@gmail.com")));
    }
}
