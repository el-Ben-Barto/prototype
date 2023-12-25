package com.smartliving.digitaltwin.servicelayer.domain.core;

import com.smartliving.digitaltwin.servicelayer.backend.MongoDBSLServiceRepository;
import com.smartliving.digitaltwin.servicelayer.domain.data.SLService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FetchSLServiceQuery implements FetchSLServiceQueryInPort {

    MongoDBSLServiceRepository repository;

    @Override
    public Flux<SLService> fetchAllSLServices() {
        return repository.findAll();
    }

    @Override
    public Flux<SLService> fetchAllActiveSLServices(Boolean activationState) {
        return repository.findByActive(activationState);
    }

    @Override
    public Flux<SLService> fetchSLServicesByReadableName(String name) {
        return repository.findByReadableName(name);
    }

    @Override
    public Mono<SLService> fetchSLServicesById(String id) {
        return repository.findByServiceId(id);
    }
}
