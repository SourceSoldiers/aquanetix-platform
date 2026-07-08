package com.sourcesoldiers.aquanetix.platform.subscription.domain.repositories;

import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByUserIdOrderByIdDesc(Integer userId);

    Optional<Subscription> findFirstByUserIdAndStatusIgnoreCaseOrderByIdDesc(
            Integer userId,
            String status);

    Optional<Subscription> findFirstByUserIdOrderByIdDesc(Integer userId);
}
