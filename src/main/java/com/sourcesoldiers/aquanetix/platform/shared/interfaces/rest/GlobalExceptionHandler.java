package com.sourcesoldiers.aquanetix.platform.shared.interfaces.rest;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.stream.Collectors;

/**
 * Interface layer exception handler translating domain and validation errors
 * to HTTP problem responses. Handles:
 * - {@link IllegalArgumentException}: domain invariant violations and input validation
 * - {@link MethodArgumentNotValidException}: Resource validation failures from
 *   Jakarta Bean Validation
 * - {@link EntityNotFoundException}: JPA entities that could not be located
 * - {@link AccessDeniedException}: Spring Security authorization failures
 * - {@link CancellationException} / {@link AsyncRequestTimeoutException}: cancelled or timed-out requests
 * - {@link Exception}: catch-all safety net for any unexpected/unmapped error
 * Localizes error messages per request locale, forming the error translation boundary
 * between domain logic and HTTP clients.
 *
 * <p>Note: most application-layer errors are already translated to HTTP responses via the
 * {@code Result}/{@code ApplicationError} + {@code ResponseEntityAssembler} pipeline used by
 * command/query services. This handler is the safety net for exceptions that escape that
 * pipeline (validation, security, persistence, or genuinely unexpected failures) &mdash;
 * it plays the same role as the C# {@code GlobalExceptionHandlerMiddleware}.</p>
 *
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {

        this.messageSource = messageSource;
    }

    /**
     * Handles MethodArgumentNotValidException.
     * @param exception The {@link MethodArgumentNotValidException} exception to handle
     * @param locale The {@link Locale} locale to use for error messages
     * @return The {@link ErrorResponse} error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleException(MethodArgumentNotValidException exception, Locale locale) {
        String prefix = messageSource.getMessage("errors.found", null, locale);
        String fields = exception.getFieldErrors().stream()
                .map(fieldError -> messageSource.getMessage(fieldError, locale))
                .collect(Collectors.joining(", "));
        log.warn("Validation failed: {}", fields);
        return ErrorResponse.create(
                exception,
                HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
                prefix + " " + fields
        );
    }

    /**
     * Handles IllegalArgumentException.
     * @param exception The {@link IllegalArgumentException} exception to handle
     * @param locale The {@link Locale} locale to use for error messages
     * @return The {@link ErrorResponse} error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleException(IllegalArgumentException exception, Locale locale) {
        String messageKey = exception.getMessage() != null ? exception.getMessage() : "errors.found";
        String message = Objects.requireNonNullElse(messageSource.getMessage(messageKey,
                null, messageKey, locale), messageKey);
        log.warn("Illegal argument: {}", message);
        return ErrorResponse.create(exception, HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), message);
    }

    /**
     * Handles JPA entities that could not be located by id or unique key.
     * @param exception The {@link EntityNotFoundException} exception to handle
     * @param locale The {@link Locale} locale to use for error messages
     * @return The {@link ErrorResponse} error response
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleException(EntityNotFoundException exception, Locale locale) {
        String message = messageSource.getMessage("error.not-found.message", null,
                "The requested resource was not found.", locale);
        log.warn("Entity not found: {}", exception.getMessage());
        return ErrorResponse.create(exception, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), message);
    }

    /**
     * Handles Spring Security authorization failures.
     * @param exception The {@link AccessDeniedException} exception to handle
     * @param locale The {@link Locale} locale to use for error messages
     * @return The {@link ErrorResponse} error response
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse handleException(AccessDeniedException exception, Locale locale) {
        String message = messageSource.getMessage("error.access-denied.message", null,
                "You do not have permission to perform this action.", locale);
        log.warn("Access denied: {}", exception.getMessage());
        return ErrorResponse.create(exception, HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()), message);
    }

    /**
     * Handles cancelled or timed-out requests, mirroring the C# middleware's
     * dedicated handling of {@code OperationCanceledException}.
     * @param exception The cancellation/timeout exception to handle
     * @param locale The {@link Locale} locale to use for error messages
     * @return The {@link ErrorResponse} error response
     */
    @ExceptionHandler({CancellationException.class, AsyncRequestTimeoutException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse handleCancellation(Exception exception, Locale locale) {
        String message = messageSource.getMessage("error.operation-cancelled.message", null,
                "The operation was cancelled.", locale);
        log.warn("Request was cancelled: {}", exception.getMessage());
        return ErrorResponse.create(exception, HttpStatusCode.valueOf(HttpStatus.CONFLICT.value()), message);
    }

    /**
     * Catch-all safety net for any exception not handled above. Never leaks internal
     * exception details to the client; only logs them server-side.
     * @param exception The unexpected {@link Exception} to handle
     * @param locale The {@link Locale} locale to use for error messages
     * @return The {@link ErrorResponse} error response
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse handleUnexpectedException(Exception exception, Locale locale) {
        String message = messageSource.getMessage("error.generic.message", null,
                "An unexpected error occurred.", locale);
        log.error("An unhandled exception occurred: {}", exception.getMessage(), exception);
        return ErrorResponse.create(exception, HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), message);
    }
}