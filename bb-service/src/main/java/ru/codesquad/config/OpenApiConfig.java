package ru.codesquad.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Breeders Book Api",
                description = "Convenient and effective platform for managing breeders business and communicating with customers.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Holy-Raven",
                        email = "ek@mail.ru",
                        url = "https://github.com/Holy-Raven/breedersbook"
                )
        )
)
public class OpenApiConfig {

}