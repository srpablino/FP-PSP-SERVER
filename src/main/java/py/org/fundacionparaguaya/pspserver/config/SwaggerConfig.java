package py.org.fundacionparaguaya.pspserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by rodrigovillalba on 8/28/17.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String securitySchemaOAuth2 = "oauth2schema";
    // one of the scopres stored in oauth_client_details
    public static final String authorizationScopeGlobal = "bar";
    public static final String authorizationScopeGlobalDesc ="accessEverything";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("py.org.fundacionparaguaya.pspserver"))
                .paths(regex("/api.*"))
                .build();
    }

    // This is not working.
    private OAuth securitySchema() {
        AuthorizationScope authorizationScope = new AuthorizationScope(SwaggerConfig.authorizationScopeGlobal, SwaggerConfig.authorizationScopeGlobal);
        LoginEndpoint loginEndpoint = new LoginEndpoint("http://localhost:8080/oauth/token");
        GrantType grantType = new ImplicitGrant(loginEndpoint, "access_token");
        return new OAuth(SwaggerConfig.securitySchemaOAuth2, newArrayList(authorizationScope), newArrayList(grantType));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex("/api.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobalDesc);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference(SwaggerConfig.securitySchemaOAuth2, authorizationScopes));
    }


}