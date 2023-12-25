package com.smartliving.digitaltwin.servicelayer.domain.core;

import com.smartliving.digitaltwin.servicelayer.domain.data.SLService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FetchSLServiceQueryInPort {
    public Flux<SLService> fetchAllSLServices();
    public Flux<SLService> fetchAllActiveSLServices(Boolean activationState);
    public Flux<SLService> fetchSLServicesByReadableName(String name);
    public Mono<SLService> fetchSLServicesById(String id);
}
