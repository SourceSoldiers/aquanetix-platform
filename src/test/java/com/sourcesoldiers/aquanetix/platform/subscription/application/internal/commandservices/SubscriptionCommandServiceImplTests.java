package com.sourcesoldiers.aquanetix.platform.subscription.application.internal.commandservices;

import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates.Subscription;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.ChangePlanCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.CreateSubscriptionCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.repositories.SubscriptionRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SubscriptionCommandServiceImplTests {

    @Test
    void creatingAnActiveSubscriptionCancelsThePreviousActiveSubscription() {
        var repository = mock(SubscriptionRepository.class);
        var previous = new Subscription(7, "Basic", "Active");
        when(repository.findAllByUserIdOrderByIdDesc(7)).thenReturn(List.of(previous));
        var service = new SubscriptionCommandServiceImpl(repository);

        var result = service.handle(
                new CreateSubscriptionCommand(7, "Smart City", "Active"));

        assertTrue(result.isPresent());
        assertFalse(previous.isActive());
        assertEquals("Smart City", result.orElseThrow().getPlan());
        verify(repository).save(result.orElseThrow());
    }

    @Test
    void changingPlanKeepsOnlyTheSelectedSubscriptionActive() {
        var repository = mock(SubscriptionRepository.class);
        var selected = new Subscription(7, "Basic", "Cancelled");
        var duplicateActive = new Subscription(7, "Smart City", "Active");
        setId(selected, 10L);
        setId(duplicateActive, 11L);
        when(repository.findById(10L)).thenReturn(Optional.of(selected));
        when(repository.findAllByUserIdOrderByIdDesc(7))
                .thenReturn(List.of(duplicateActive, selected));
        var service = new SubscriptionCommandServiceImpl(repository);

        var result = service.handle(new ChangePlanCommand(10L, "Industrial"));

        assertTrue(result.isPresent());
        assertTrue(selected.isActive());
        assertFalse(duplicateActive.isActive());
        assertEquals("Industrial", selected.getPlan());
        verify(repository).save(selected);
    }

    private static void setId(Subscription subscription, Long id) {
        try {
            var field = Subscription.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(subscription, id);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError(exception);
        }
    }
}
