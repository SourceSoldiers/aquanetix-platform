package com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates;

import jakarta.persistence.*;

@Entity
@Table(name = "service_designs")
public class ServiceDesign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String routeName;
    private String origin;
    private String destination;
    private String status;

    protected ServiceDesign() {}

    public ServiceDesign(String routeName, String origin, String destination, String status) {
        this.routeName = routeName;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getRouteName() { return routeName; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getStatus() { return status; }
}