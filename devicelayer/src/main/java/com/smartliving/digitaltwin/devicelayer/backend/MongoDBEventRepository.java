package com.smartliving.digitaltwin.devicelayer.backend;

import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLThingEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface MongoDBEventRepository extends ReactiveMongoRepository<SLThingEvent, String> {
}
