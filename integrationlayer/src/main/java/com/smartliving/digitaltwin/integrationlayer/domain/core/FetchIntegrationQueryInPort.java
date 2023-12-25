package com.smartliving.digitaltwin.integrationlayer.domain.core;

import com.smartliving.digitaltwin.integrationlayer.domain.wot.core.Integration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FetchIntegrationQueryInPort {
    public Flux<Integration> fetchAllIntegrations();
    public Mono<Integration> fetchIntegration(String Id);
}
