 package com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects;

import java.util.regex.Pattern;

public record DevicesApiKey(String value) {
    private static final int MAX_LENGTH = 256;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Za-z0-9._:-]+$");
    private static final String NOT_BLANK_MESSAGE_KEY = "device.source.error.devicesApiKey.notBlank";
    private static final String SIZE_MESSAGE_KEY = "device.source.error.devicesApiKey.size";
    private static final String PATTERN_MESSAGE_KEY = "device.source.error.devicesApiKey.pattern";

    public DevicesApiKey {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
    }
}
