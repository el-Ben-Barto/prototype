package com.smartliving.digitaltwin.devicelayer.domain.core;

import com.smartliving.digitaltwin.devicelayer.web.dto.InvokeActionInput;
import reactor.core.publisher.Mono;

public interface InvokeActionCommandInPort {
    Mono<InvokeActionResponse> invokeAction(InvokeActionInput input);
}
