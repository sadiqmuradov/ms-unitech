package az.mpay.unitech.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        name = "Authorization"
)
public class OpenApiConfig {

    @Value("${springdoc.hostname}")
    private String hostname;

    @Bean
    public OpenAPI openAPI() {

        List<Server> servers = new ArrayList<>();

        if (StringUtils.isNoneBlank(hostname)) {
            servers.add(new Server().url(hostname));
        }

        return new OpenAPI().info(
                new Info().title("MPAY SIMA transfer API")
                          .description("MPAY SIMA transfer service")
                          .version("1.0")
                          .license(new License().name("Apache 2.0").url("http://springdoc.org"))
        )
                            .servers(servers);
    }
}
