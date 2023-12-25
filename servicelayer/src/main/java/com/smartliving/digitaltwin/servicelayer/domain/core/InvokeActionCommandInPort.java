package com.smartliving.digitaltwin.servicelayer.domain.core;

import com.smartliving.digitaltwin.servicelayer.domain.data.SLInteractionResponse;
import reactor.core.publisher.Flux;

public interface InvokeActionCommandInPort {
    public Flux<SLInteractionResponse> performSLServiceById(String serviceId);
}
