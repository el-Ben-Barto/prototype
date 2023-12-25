package com.smartliving.digitaltwin.devicelayer.domain.status;

import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto.HueDiscoveryResponseData;
import org.bson.Document;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FetchVendorThingsStatusQueryOutPort {
    public Mono<Document> fetchStatus(FetchThingStatusRequest request);
}
