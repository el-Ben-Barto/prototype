package com.smartliving.digitaltwin.servicelayer.backend;

import com.smartliving.digitaltwin.servicelayer.domain.data.SLService;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MongoDBSLServiceRepository extends ReactiveMongoRepository<SLService, String> {
    Flux<SLService> findByReadableName(String readableName);
    Mono<SLService> findByServiceId(String serviceId);
    Flux<SLService> findByActive(Boolean active);
}
