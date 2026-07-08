package com.sourcesoldiers.aquanetix.platform.subscription.domain.repositories;

import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserId(Integer userId);
}
