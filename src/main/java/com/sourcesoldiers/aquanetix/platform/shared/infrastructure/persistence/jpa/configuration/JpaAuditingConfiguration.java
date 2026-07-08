package com.sourcesoldiers.aquanetix.platform.shared.infrastructure.persistence.jpa.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Activates Spring Data JPA auditing for the whole application.
 *
 * <p>Without this configuration, {@code @CreatedDate} and {@code @LastModifiedDate}
 * fields declared in {@link com.sourcesoldiers.aquanetix.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity}
 * are never populated, even though the entity is annotated with
 * {@code @EntityListeners(AuditingEntityListener.class)}: the listener itself does
 * nothing unless JPA auditing has been explicitly enabled.</p>
 *
 * <p>This is the Java/Spring equivalent of the C# {@code AuditableEntityInterceptor}
 * registered on {@code AppDbContext.OnConfiguring} in the original implementation:
 * both guarantee that any auditable entity gets its {@code createdAt} set once on
 * insert and its {@code updatedAt} refreshed on every save.</p>
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
}
