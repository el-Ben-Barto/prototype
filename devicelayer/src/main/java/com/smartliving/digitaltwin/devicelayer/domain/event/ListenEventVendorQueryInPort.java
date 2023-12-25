package com.smartliving.digitaltwin.devicelayer.domain.event;

import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLThingEvent;
import reactor.core.publisher.Flux;

public interface ListenEventVendorQueryInPort {
    public Flux<SLThingEvent> listenEvents();
}
