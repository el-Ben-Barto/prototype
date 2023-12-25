package com.smartliving.digitaltwin.integrationlayer.domain.core;

import com.smartliving.digitaltwin.integrationlayer.backend.MongoDBIntegrationRepository;
import com.smartliving.digitaltwin.integrationlayer.domain.wot.core.ActionAffordance;
import com.smartliving.digitaltwin.integrationlayer.domain.wot.core.DataSchema;
import com.smartliving.digitaltwin.integrationlayer.domain.wot.core.Integration;
import com.smartliving.digitaltwin.integrationlayer.domain.wot.core.Type;
import com.smartliving.digitaltwin.integrationlayer.web.dto.InvokeActionInput;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvokeActionCommand implements InvokeActionCommandInPort {

    //TODO: improve vendor-action mapping
    //TODO: add supported functions here and the corresponding handling of actions

    MongoDBIntegrationRepository integrationRepository;

    InvokeActionCommandOutPort invokeActionCommandOutPort;

    @Override
    public Mono<InvokeActionResponse> invokeAction(InvokeActionInput input) {
        return integrationRepository.findById(input.getId())
                .filter(integration -> isThingCapableOfHandlingAction(integration, input.getAction()))
                .flatMap(integration -> {
                    String action = input.getAction();
                    Document requestBody = null;
                    if (actionRequiresBody(integration.getActions().get(action))) {
                        requestBody = constructRequestBody(integration.getActions().get(action), input.getParameters());
                    }
                    return invokeActionCommandOutPort.invokeAction(integration, requestBody);
                }).map(status -> InvokeActionResponse.builder()
                        .id(input.getId()).status(status).build());
    }

    private Boolean isThingCapableOfHandlingAction(Integration integration, String action) {
        return integration.getActions().containsKey(action);
    }

    private Boolean actionRequiresBody(ActionAffordance actionAffordance) {
        return actionAffordance.getInput() != null;
    }

    private Document constructRequestBody(ActionAffordance actionAffordance, Document parameter) {
        return concatDocument(actionAffordance.getInput(), parameter);
    }

    private Document concatDocument(DataSchema input, Document parameter) {
        return concatProperties(input.getProperties(), parameter);
    }

    private Document concatProperties(Map<String, DataSchema> data, Document parameter) {
        Document document = new Document();
        List<Document> documentList = data.entrySet().stream().map(e -> {
            if (e.getValue().getType().equalsIgnoreCase(Type.object.name())) {
                document.append(e.getKey(), concatProperties(e.getValue().getProperties(), parameter));
                return document;
            } else {
                if (parameter.get(e.getKey()) != null) {
                    document.append(e.getKey(), parameter.get(e.getKey()));
                    return document;
                } else {
                    return document.append(e.getKey(), null);
                }
            }
        }).collect(Collectors.toList());
        return document;
    }

}