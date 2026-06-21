package com.sourcesoldiers.aquanetix.platform.servicedesign.application.queryservices;

import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates.ServiceDesign;
import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.queries.GetAllServiceDesignsQuery;

import java.util.List;

public interface ServiceDesignQueryService {
    List<ServiceDesign> handle(GetAllServiceDesignsQuery query);
}