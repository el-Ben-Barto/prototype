package com.smartliving.digitaltwin.devicelayer.domain.core;

import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto.HueDiscoveryResponseData;
import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto.HueInvokeActionResponse;
import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto.HueInvokeActionResponseData;
import com.smartliving.digitaltwin.devicelayer.domain.status.FetchThingStatusRequest;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.ActionAffordance;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import com.smartliving.digitaltwin.devicelayer.web.dto.InvokeActionInput;
import org.bson.Document;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface InvokeActionCommandOutPort {
    public Mono<Document> invokeLightAction(Thing thing, Document requestBody);
}
