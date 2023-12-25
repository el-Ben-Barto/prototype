package com.smartliving.digitaltwin.devicelayer.domain.core;

import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLThing;
import com.smartliving.digitaltwin.devicelayer.web.dto.SLThingInput;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreateThingCommandInPort {
    public Mono<SLThing> createThing(SLThingInput input);
}
