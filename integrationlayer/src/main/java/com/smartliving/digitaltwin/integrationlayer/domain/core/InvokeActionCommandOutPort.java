package com.smartliving.digitaltwin.integrationlayer.domain.core;

import com.smartliving.digitaltwin.integrationlayer.domain.wot.core.Integration;
import org.bson.Document;
import reactor.core.publisher.Mono;

public interface InvokeActionCommandOutPort {
    public Mono<Document> invokeAction(Integration integration, Document requestBody);
}
