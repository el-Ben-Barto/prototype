package com.smartliving.digitaltwin.devicelayer.domain.status;

import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLThing;
import reactor.core.publisher.Mono;

public interface FetchThingsStatusQueryInPort {
    public Mono<SLThing> fetchStatus(String thingId);
}
