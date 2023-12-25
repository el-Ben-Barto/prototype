package com.smartliving.digitaltwin.servicelayer.domain.core;

import com.smartliving.digitaltwin.servicelayer.backend.MongoDBSLServiceRepository;
import com.smartliving.digitaltwin.servicelayer.domain.data.SLService;
import com.smartliving.digitaltwin.servicelayer.web.dto.SLServiceInput;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateSLServiceCommand implements CreateSLServiceCommandInPort {

    MongoDBSLServiceRepository repository;

    @Override
    public Mono<SLService> createSLService(SLServiceInput input) {
        SLService serviceDefinition = input.getServiceDefinition();
        String serviceId = "urn:dev:service"+ UUID.randomUUID() +"-"+serviceDefinition.getReadableName().replace(" ", "-");
        SLService slService = SLService.builder()
                .serviceId(serviceId)
                .readableName(serviceDefinition.getReadableName())
                .owner(serviceDefinition.getOwner())
                .active(serviceDefinition.getActive())
                .allowedUsers(serviceDefinition.getAllowedUsers())
                .shortDescription(serviceDefinition.getShortDescription())
                .longDescription(serviceDefinition.getLongDescription())
                .registeredInteractions(serviceDefinition.getRegisteredInteractions())
                .creationTime(LocalDate.now())
                .build();

        return repository.save(slService);
    }
}
