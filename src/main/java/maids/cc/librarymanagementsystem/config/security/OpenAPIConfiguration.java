package maids.cc.librarymanagementsystem.config.security;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(
        title = "Library Management System",
        version = "Custom API Version",
        description = "Operations pertaining to librarian in the system",
        contact = @io.swagger.v3.oas.annotations.info.Contact(email = "jaafarrrana220@gmail.com",name="Rana Jaafar",url = "https://www.linkedin.com/in/rana-jaafar/")
))
public class OpenAPIConfiguration {

}
