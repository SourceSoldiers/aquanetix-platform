package com.sourcesoldiers.aquanetix.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class AquanetixPlatformApplication {

    public static void main(String[] args) {
        configureRenderDatabaseUrl();
        SpringApplication.run(AquanetixPlatformApplication.class, args);
    }

    private static void configureRenderDatabaseUrl() {
        var databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null || databaseUrl.isBlank()) {
            return;
        }

        if (hasDatasourceConfiguration()) {
            return;
        }

        var databaseUri = URI.create(databaseUrl);
        var databaseName = databaseUri.getPath().replaceFirst("^/", "");
        var port = databaseUri.getPort() == -1 ? 5432 : databaseUri.getPort();

        System.setProperty(
                "spring.datasource.url",
                "jdbc:postgresql://%s:%d/%s".formatted(databaseUri.getHost(), port, databaseName)
        );

        var userInfo = databaseUri.getUserInfo();
        if (userInfo != null) {
            var credentials = userInfo.split(":", 2);
            System.setProperty("spring.datasource.username", decode(credentials[0]));
            if (credentials.length > 1) {
                System.setProperty("spring.datasource.password", decode(credentials[1]));
            }
        }
    }

    private static boolean hasDatasourceConfiguration() {
        return hasValue(System.getProperty("spring.datasource.url"))
                || hasValue(System.getenv("SPRING_DATASOURCE_URL"))
                || hasValue(System.getenv("JDBC_DATABASE_URL"))
                || hasValue(System.getenv("DB_HOST"));
    }

    private static boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
