package com.smartliving.digitaltwin.devicelayer.domain.core;

import com.smartliving.digitaltwin.devicelayer.backend.MongoDBThingRepository;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FetchThingsQuery implements FetchThingsQueryInPort {

    MongoDBThingRepository repository;

    @Override
    public Flux<Thing> fetchAllThings() {
        return repository.findAll();
    }

    @Override
    public Mono<Thing> fetchThing(String thingId) {
        return repository.findById(thingId);
    }
}
