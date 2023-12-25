package com.smartliving.digitaltwin.devicelayer.backend;

import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MongoDBThingRepository extends ReactiveMongoRepository<Thing, String> {
    Mono<Thing> findById(String id);
}
