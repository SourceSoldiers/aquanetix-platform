package com.sourcesoldiers.aquanetix.platform.shared.interfaces.rest;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class OpenApiParityConfiguration {

    @Bean
    public OpenApiCustomizer cSharpSwaggerParityCustomizer() {
        var expectedResponses = cSharpResponseParity();

        return openApi -> {
            openApi.setSecurity(List.of(new SecurityRequirement().addList("Bearer", new ArrayList<>())));
            openApi.setTags(List.of(
                    new Tag().name("Alerts"),
                    new Tag().name("Authentication"),
                    new Tag().name("Destinations"),
                    new Tag().name("Devices"),
                    new Tag().name("QualityAnalysis"),
                    new Tag().name("Subscriptions"),
                    new Tag().name("WaterBatches")
            ));

            if (openApi.getComponents() != null) {
                openApi.getComponents().addSecuritySchemes("Bearer", new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .description("Ingresa tu token JWT (sin escribir 'Bearer')."));
            }

            if (openApi.getPaths() != null) {
                openApi.getPaths().forEach((path, pathItem) -> {
                    pathItem.readOperationsMap().forEach((method, operation) -> {
                        if (path.startsWith("/api/v1/subscriptions")) {
                            operation.setOperationId(null);
                        }

                        stripSpringErrorResponseContent(operation);

                        var expected = expectedResponses.get(method.name() + " " + path);
                        if (expected != null) {
                            operation.setResponses(toApiResponses(expected));
                        }
                    });
                });
            }

            if (openApi.getComponents() != null && openApi.getComponents().getSchemas() != null) {
                var schemas = openApi.getComponents().getSchemas();
                schemas.remove("ContentDisposition");
                schemas.remove("DefaultHttpStatusCode");
                schemas.remove("ErrorResponse");
                schemas.remove("HttpHeaders");
                schemas.remove("HttpMethod");
                schemas.remove("HttpRange");
                schemas.remove("HttpStatus");
                schemas.remove("HttpStatusCode");
                schemas.remove("MediaType");
                schemas.remove("ProblemDetail");
                schemas.remove("PlanResource");
                schemas.remove("SubscriptionResource");
            }
        };
    }

    private static void stripSpringErrorResponseContent(Operation operation) {
        operation.getResponses().forEach((code, response) -> {
            if (response.getContent() == null) {
                return;
            }
            var hasOnlyErrorResponseContent = response.getContent().values().stream()
                    .allMatch(mediaType -> mediaType.getSchema() != null
                            && "#/components/schemas/ErrorResponse".equals(mediaType.getSchema().get$ref()));
            if (hasOnlyErrorResponseContent) {
                response.setContent(null);
            }
        });
    }

    private static ApiResponses toApiResponses(String[] specs) {
        var responses = new ApiResponses();

        for (var spec : specs) {
            var parts = spec.split(":", 2);
            var code = parts[0];
            var schemaName = parts.length > 1 ? parts[1] : "";
            var response = new ApiResponse();

            if (!schemaName.isBlank()) {
                response.setContent(jsonContent(schemaName));
            }

            responses.addApiResponse(code, response);
        }

        return responses;
    }

    private static Content jsonContent(String schemaName) {
        var mediaType = new MediaType();

        if (schemaName.startsWith("array:")) {
            var itemSchema = new Schema<>().$ref("#/components/schemas/" + schemaName.substring("array:".length()));
            mediaType.setSchema(new Schema<>().type("array").items(itemSchema));
        } else {
            mediaType.setSchema(new Schema<>().$ref("#/components/schemas/" + schemaName));
        }

        return new Content().addMediaType("application/json", mediaType);
    }

    private static Map<String, String[]> cSharpResponseParity() {
        var responses = new HashMap<String, String[]>();

        responses.put("GET /api/v1/alerts", new String[]{"200:array:AlertResource"});
        responses.put("POST /api/v1/alerts", new String[]{"201:AlertResource", "400:"});
        responses.put("GET /api/v1/alerts/{alertId}", new String[]{"200:AlertResource", "404:"});
        responses.put("PUT /api/v1/alerts/{alertId}", new String[]{"200:AlertResource", "404:"});
        responses.put("GET /api/v1/alerts/device/{deviceId}", new String[]{"200:array:AlertResource"});
        responses.put("GET /api/v1/alerts/status/{status}", new String[]{"200:array:AlertResource"});

        responses.put("POST /api/v1/authentication/sign-in", new String[]{"200:AuthenticatedUserResource", "401:"});
        responses.put("POST /api/v1/authentication/sign-up", new String[]{"200:AuthenticatedUserResource", "400:"});

        responses.put("GET /api/v1/destinations", new String[]{"200:array:DestinationResource"});
        responses.put("POST /api/v1/destinations", new String[]{"201:DestinationResource", "409:"});
        responses.put("GET /api/v1/destinations/{destinationId}", new String[]{"200:DestinationResource", "404:"});
        responses.put("DELETE /api/v1/destinations/{destinationId}", new String[]{"204:", "404:", "409:"});

        responses.put("GET /api/v1/devices", new String[]{"200:array:DeviceResource"});
        responses.put("POST /api/v1/devices", new String[]{"201:DeviceResource", "400:"});
        responses.put("GET /api/v1/devices/{deviceId}", new String[]{"200:DeviceResource", "404:"});
        responses.put("PUT /api/v1/devices/{deviceId}", new String[]{"200:DeviceResource", "404:"});
        responses.put("DELETE /api/v1/devices/{deviceId}", new String[]{"204:", "404:"});
        responses.put("GET /api/v1/devices/{deviceId}/thresholds", new String[]{"200:array:ThresholdResource"});
        responses.put("POST /api/v1/devices/{deviceId}/thresholds", new String[]{"201:ThresholdResource", "404:"});

        responses.put("GET /api/v1/quality-analysis", new String[]{"200:array:QualityAnalysisResource"});
        responses.put("POST /api/v1/quality-analysis", new String[]{"201:QualityAnalysisResource", "400:"});
        responses.put("GET /api/v1/quality-analysis/{id}", new String[]{"200:QualityAnalysisResource", "404:"});

        responses.put("GET /api/v1/subscriptions", new String[]{"200:"});
        responses.put("POST /api/v1/subscriptions", new String[]{"200:"});
        responses.put("GET /api/v1/subscriptions/{id}", new String[]{"200:"});
        responses.put("PUT /api/v1/subscriptions/{id}/cancel", new String[]{"200:"});
        responses.put("PUT /api/v1/subscriptions/{id}/plan", new String[]{"200:"});
        responses.put("PUT /api/v1/subscriptions/{id}/renew", new String[]{"200:"});
        responses.put("GET /api/v1/subscriptions/by-user/{userId}", new String[]{"200:"});
        responses.put("GET /api/v1/subscriptions/plans", new String[]{"200:"});

        responses.put("GET /api/v1/water-batches", new String[]{"200:array:WaterBatchResource"});
        responses.put("POST /api/v1/water-batches", new String[]{"201:WaterBatchResource", "400:"});
        responses.put("GET /api/v1/water-batches/{waterBatchId}", new String[]{"200:WaterBatchResource", "404:"});
        responses.put("PUT /api/v1/water-batches/{waterBatchId}", new String[]{"200:WaterBatchResource", "404:"});
        responses.put("DELETE /api/v1/water-batches/{waterBatchId}", new String[]{"204:", "404:"});

        return responses;
    }
}
