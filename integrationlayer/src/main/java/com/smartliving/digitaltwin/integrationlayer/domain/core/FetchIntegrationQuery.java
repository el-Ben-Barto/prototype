package com.smartliving.digitaltwin.integrationlayer.domain.core;

import com.smartliving.digitaltwin.integrationlayer.backend.MongoDBIntegrationRepository;
import com.smartliving.digitaltwin.integrationlayer.domain.wot.core.Integration;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FetchIntegrationQuery implements FetchIntegrationQueryInPort {

    MongoDBIntegrationRepository repository;

    @Override
    public Flux<Integration> fetchAllIntegrations() {
        return repository.findAll();
    }

    @Override
    public Mono<Integration> fetchIntegration(String id) {
        return repository.findById(id);
    }
}
