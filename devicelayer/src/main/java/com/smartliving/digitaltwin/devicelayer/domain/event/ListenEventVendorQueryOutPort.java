package com.smartliving.digitaltwin.devicelayer.domain.event;

import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto.HueDiscoveryResponse;
import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto.HueEventResponse;
import org.bson.Document;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ListenEventVendorQueryOutPort {
    public Flux<ServerSentEvent<List<Document>>> listenEvents();
}
