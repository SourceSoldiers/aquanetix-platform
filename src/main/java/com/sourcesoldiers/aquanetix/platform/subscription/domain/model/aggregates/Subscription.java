package com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates;

import jakarta.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer userId;

    private String plan;

    private String status;

    protected Subscription() {
    }

    public Subscription(Integer userId, String plan, String status) {
        this.userId = userId;
        this.plan = plan;
        this.status = status;
    }

    public void cancel() {
        this.status = "Cancelled";
    }

    public void renew() {
        this.status = "Active";
    }

    public Long getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getPlan() {
        return plan;
    }

    public String getStatus() {
        return status;
    }
}