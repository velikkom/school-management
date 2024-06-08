package com.project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * * *!!! The info parameter takes an @Info object containing the title and version of the API.
 * The security parameter determines the security requirements required to access the API.
 *  *  !!!  The name parameter specifies the name of the security scheme. type parameter, security scheme
 *  *  * Specifies the * type and specifies the HTTP security scheme using the SecuritySchemeType.HTTP value.
 *  *  * * !!! http://localhost:8080/swagger-ui/index.html --> SWAGGER API access address
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "StudentManagement API", version = "1.0.0"),security = @SecurityRequirement(name = "Bearer"))
@SecurityScheme(name = "Bearer", type = SecuritySchemeType.HTTP, scheme = "Bearer")
public class OpenAPIConfig
{


}
/**
 * !!! info parametresi, API'nin başlığını ve sürümünü içeren bir @Info nesnesi alır.
 *  *  security parametresi, API'ye erişmek için gereken güvenlik gereksinimlerini belirler.
 *  * !!!  name parametresi, güvenlik şemasının adını belirtir. type parametresi, güvenlik şemasının
 *  *  türünü belirtir ve SecuritySchemeType.HTTP değeri kullanılarak HTTP güvenlik şemasını belirtir.
 *  *
 *  *  !!! http://localhost:8080/swagger-ui/index.html   --> SWAGGER API erisim adresi
 */

