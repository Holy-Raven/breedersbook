package ru.codesquad.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Statistics Api",
                description = "View statistics service for the BreedersBook application.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Holy-Raven",
                        email = "breedersbook@yandex.ru",
                        url = "https://github.com/Holy-Raven/breedersbook"
                )
        )
)
public class OpenApiConfig {

}