package com.smartliving.digitaltwin.servicelayer.domain.core;

import com.smartliving.digitaltwin.servicelayer.domain.data.SLInteractionRequest;
import com.smartliving.digitaltwin.servicelayer.domain.data.SLInteractionResponse;
import reactor.core.publisher.Mono;

public interface InvokeActionCommandOutPort {
    public Mono<SLInteractionResponse> invokeAction(SLInteractionRequest request);
}
