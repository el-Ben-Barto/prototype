package com.smartliving.digitaltwin.integrationlayer.backend;

import com.smartliving.digitaltwin.integrationlayer.domain.wot.core.Integration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MongoDBIntegrationRepository extends ReactiveMongoRepository<Integration, String> {
    Mono<Integration> findById(String id);
}
