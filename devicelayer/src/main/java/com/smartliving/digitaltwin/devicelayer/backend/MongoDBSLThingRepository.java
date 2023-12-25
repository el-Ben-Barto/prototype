package com.smartliving.digitaltwin.devicelayer.backend;

import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLThing;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MongoDBSLThingRepository extends ReactiveMongoRepository<SLThing, String> {
    Mono<SLThing> findById(String id);
}
