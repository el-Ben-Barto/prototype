package com.smartliving.digitaltwin.servicelayer.domain.core;

import com.smartliving.digitaltwin.servicelayer.domain.data.SLService;
import com.smartliving.digitaltwin.servicelayer.web.dto.SLServiceInput;
import reactor.core.publisher.Mono;

public interface CreateSLServiceCommandInPort {
    public Mono<SLService> createSLService(SLServiceInput input);
}
