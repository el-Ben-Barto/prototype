package com.smartliving.digitaltwin.servicelayer.domain.core;

import com.smartliving.digitaltwin.servicelayer.backend.MongoDBSLServiceRepository;
import com.smartliving.digitaltwin.servicelayer.domain.data.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class InvokeActionCommand implements InvokeActionCommandInPort {

    MongoDBSLServiceRepository repository;

    InvokeActionCommandOutPort performSLThingsCommandOutPort;

    InvokeActionCommandOutPort performSLIntegrationCommandOutPort;

    public InvokeActionCommand(MongoDBSLServiceRepository repository,
                               @Qualifier(value = "invoke-action-thing") InvokeActionCommandOutPort performSLThingsCommandOutPort,
                               @Qualifier(value = "invoke-action-integration") InvokeActionCommandOutPort performSLIntegrationCommandOutPort) {
        this.repository = repository;
        this.performSLThingsCommandOutPort = performSLThingsCommandOutPort;
        this.performSLIntegrationCommandOutPort = performSLIntegrationCommandOutPort;
    }

    @Override
    public Flux<SLInteractionResponse> performSLServiceById(String serviceId) {
        return repository.findByServiceId(serviceId)
                .filter(SLService::getActive)
                .flatMapMany(slService -> Flux.fromStream(slService.getRegisteredInteractions().stream())
                        .flatMap(interaction -> {
                            if (interaction.getType().name().equalsIgnoreCase(SLInteractionType.thing.name())) {
                                return performSLThingsCommandOutPort
                                        .invokeAction(
                                                mapObject(interaction)
                                        );
                            } else {
                                return performSLIntegrationCommandOutPort
                                        .invokeAction(
                                                mapObject(interaction)
                                        );
                            }
                        }));
    }

    private SLInteractionRequest mapObject(SLInteraction slInteraction){
        return SLInteractionRequest.builder()
                        .id(slInteraction.getUrn())
                        .action(slInteraction.getInteraction().getAction())
                        .parameters(slInteraction.getInteraction().getParameters())
                        .build();
    }

}
