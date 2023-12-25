package com.smartliving.digitaltwin.integrationlayer.domain.core;

import com.smartliving.digitaltwin.integrationlayer.web.dto.InvokeActionInput;
import reactor.core.publisher.Mono;

public interface InvokeActionCommandInPort {
    Mono<InvokeActionResponse> invokeAction(InvokeActionInput input);
}
