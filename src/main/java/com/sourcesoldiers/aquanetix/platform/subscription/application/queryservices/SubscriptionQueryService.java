package com.sourcesoldiers.aquanetix.platform.subscription.application.queryservices;

import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates.Subscription;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionQueryService {

    private final SubscriptionRepository repository;

    public SubscriptionQueryService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public Optional<Subscription> handle(Long id) {
        return repository.findById(id);
    }
}