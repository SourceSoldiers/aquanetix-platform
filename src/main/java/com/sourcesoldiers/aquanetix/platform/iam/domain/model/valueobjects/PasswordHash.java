package com.sourcesoldiers.aquanetix.platform.iam.domain.model.valueobjects;
/**
 * Value Object that represents the encoded password stored by IAM.
 * The aggregate never stores the raw password.
 */
public record PasswordHash(String value) {
    public PasswordHash {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be null or blank");
        }
    }

    public static PasswordHash of(String value) {
        return new PasswordHash(value);
    }
}