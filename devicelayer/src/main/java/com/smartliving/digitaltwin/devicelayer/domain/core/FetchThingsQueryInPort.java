package com.smartliving.digitaltwin.devicelayer.domain.core;

import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FetchThingsQueryInPort {
    public Flux<Thing> fetchAllThings();
    public Mono<Thing> fetchThing(String thingId);
}
