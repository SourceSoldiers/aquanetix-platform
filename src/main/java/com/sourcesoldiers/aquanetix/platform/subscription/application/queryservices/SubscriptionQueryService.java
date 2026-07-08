package com.sourcesoldiers.aquanetix.platform.subscription.application.queryservices;

import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates.Subscription;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class SubscriptionQueryService {

    private final SubscriptionRepository repository;

    public SubscriptionQueryService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public Optional<Subscription> handle(Long id) {
        return repository.findById(id);
    }

    public List<Subscription> handleAll() {
        return repository.findAll();
    }

    public Optional<Subscription> handleByUserId(Integer userId) {
        return repository
                .findFirstByUserIdAndStatusIgnoreCaseOrderByIdDesc(userId, "Active")
                .or(() -> repository.findFirstByUserIdOrderByIdDesc(userId));
    }
}
