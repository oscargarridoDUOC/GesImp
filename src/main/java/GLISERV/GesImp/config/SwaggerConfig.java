package GLISERV.GesImp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(
            new Info()
            .title("Api para la gestión de imprentas")
            .version("1.1")
            .description("Con esta API se puede gestionar una imprenta desde la gestion de inventarios, solicitud de pedidos junto a su facturación y usuarios")
        );
    }

}