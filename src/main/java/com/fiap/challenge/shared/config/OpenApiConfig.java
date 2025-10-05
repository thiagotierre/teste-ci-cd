package com.fiap.challenge.shared.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OpenApiConfig {
    private static final String APP_JSON = "application/json";
    private static final String SCHEMA_ERROR_DTO = "#/components/schemas/ErrorDTO";
    private static final String MESSAGE = "message";

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme bearerScheme =
            new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
            .info(
                new Info()
                    .title("Challenge FIAP")
                    .version("v1")
                    .description("Documentação da API"))
            .components(new Components().addSecuritySchemes("bearerAuth", bearerScheme))
            .addSecurityItem(securityRequirement);
    }

    @Bean
    public OpenApiCustomizer errorSchemaCustomizer() {
        return openApi ->
            openApi
                .getComponents()
                .addSchemas(
                    "ErrorDTO",
                    new Schema<>()
                        .type("object")
                        .addProperty("code", new Schema<>().type("string"))
                        .addProperty(MESSAGE, new Schema<>().type("string")));
    }

    @Bean
    public OpenApiCustomizer globalResponses() {
        ApiResponse unauthorized =
            new ApiResponse()
                .description("Não autenticado")
                .content(
                    new Content()
                        .addMediaType(
                            APP_JSON,
                            new MediaType()
                                .schema(new Schema<>().$ref(SCHEMA_ERROR_DTO))
                                .example(
                                    Map.of(
                                        "code", "UNAUTHORIZED", MESSAGE,
                                        "Você precisa se autenticar para acessar este recurso."))));

        ApiResponse forbidden =
            new ApiResponse()
                .description("Acesso negado")
                .content(
                    new Content()
                        .addMediaType(
                            APP_JSON,
                            new MediaType()
                                .schema(new Schema<>().$ref(SCHEMA_ERROR_DTO))
                                .example(
                                    Map.of(
                                        "code", "FORBIDDEN", MESSAGE,
                                        "Você não tem permissão para acessar este recurso."))));

        ApiResponse badRequest =
            new ApiResponse()
                .description("Requisição inválida")
                .content(
                    new Content()
                        .addMediaType(
                            APP_JSON,
                            new MediaType()
                                .schema(new Schema<>().$ref(SCHEMA_ERROR_DTO))
                                .example(
                                    Map.of(
                                        "code", "BAD_REQUEST", MESSAGE,
                                        "Parâmetros inválidos. Verifique os dados da requisição e tente novamente."))));

        ApiResponse notFound =
            new ApiResponse()
                .description("Não encontrado")
                .content(
                    new Content()
                        .addMediaType(
                            APP_JSON,
                            new MediaType()
                                .schema(new Schema<>().$ref(SCHEMA_ERROR_DTO))
                                .example(
                                    Map.of(
                                        "code", "NOT_FOUND", MESSAGE, "Recurso não encontrado."))));

        return openApi ->
            openApi
                .getPaths()
                .values()
                .forEach(
                    pathItem ->
                        pathItem
                            .readOperations()
                            .forEach(
                                op -> {
                                    op.getResponses().addApiResponse("400", badRequest);
                                    op.getResponses().addApiResponse("401", unauthorized);
                                    op.getResponses().addApiResponse("403", forbidden);
                                    op.getResponses().addApiResponse("404", notFound);
                                }));
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
            .group("autenticação")
            .pathsToMatch("/auth/**")
            .addOpenApiCustomizer(errorSchemaCustomizer())
            .addOpenApiCustomizer(globalResponses())
            .build();
    }

    @Bean
    public GroupedOpenApi workOrderApi() {
        return GroupedOpenApi.builder()
            .group("ordem de serviço")
            .pathsToMatch("/work-orders/**")
            .addOpenApiCustomizer(errorSchemaCustomizer())
            .addOpenApiCustomizer(globalResponses())
            .build();
    }

    @Bean
    public GroupedOpenApi customerApi() {
        return GroupedOpenApi.builder()
            .group("cliente")
            .pathsToMatch("/customers/**")
            .addOpenApiCustomizer(errorSchemaCustomizer())
            .addOpenApiCustomizer(globalResponses())
            .build();
    }

    @Bean
    public GroupedOpenApi partsApi() {
        return GroupedOpenApi.builder()
            .group("peça")
            .pathsToMatch("/parts/**")
            .addOpenApiCustomizer(errorSchemaCustomizer())
            .addOpenApiCustomizer(globalResponses())
            .build();
    }

    @Bean
    public GroupedOpenApi servicesApi() {
        return GroupedOpenApi.builder()
            .group("serviço")
            .pathsToMatch("/services/**")
            .addOpenApiCustomizer(errorSchemaCustomizer())
            .addOpenApiCustomizer(globalResponses())
            .build();
    }

    @Bean
    public GroupedOpenApi usersApi() {
        return GroupedOpenApi.builder()
            .group("usuário")
            .pathsToMatch("/users/**")
            .addOpenApiCustomizer(errorSchemaCustomizer())
            .addOpenApiCustomizer(globalResponses())
            .build();
    }

    @Bean
    public GroupedOpenApi vehicleApi() {
        return GroupedOpenApi.builder()
            .group("veiculo")
            .pathsToMatch("/vehicles/**")
            .addOpenApiCustomizer(errorSchemaCustomizer())
            .addOpenApiCustomizer(globalResponses())
            .build();
    }
}
