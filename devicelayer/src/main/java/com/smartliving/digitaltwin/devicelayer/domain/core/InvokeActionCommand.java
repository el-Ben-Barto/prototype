package com.smartliving.digitaltwin.devicelayer.domain.core;

import com.smartliving.digitaltwin.devicelayer.backend.MongoDBSLThingRepository;
import com.smartliving.digitaltwin.devicelayer.backend.MongoDBThingRepository;
import com.smartliving.digitaltwin.devicelayer.domain.status.FetchThingsStatusFunction;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.ActionAffordance;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.DataSchema;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Type;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLThing;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SupportedActions;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SupportedVendors;
import com.smartliving.digitaltwin.devicelayer.web.dto.InvokeActionInput;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InvokeActionCommand implements InvokeActionCommandInPort {

    //TODO: improve vendor-action mapping
    //TODO: add supported functions here and the corresponding handling of actions

    MongoDBThingRepository thingRepository;

    MongoDBSLThingRepository sLThingRepository;

    public InvokeActionCommand(MongoDBThingRepository thingRepository,
                               MongoDBSLThingRepository sLThingRepository,
                               @Qualifier(value = "phillips-hue-light-action") InvokeActionCommandOutPort invokePhillipsHueLight,
                               @Qualifier(value = "ikea-light-action") InvokeActionCommandOutPort invokeIkeaLight,
                               FetchThingsStatusFunction fetchThingsStatusFunction) {
        this.thingRepository = thingRepository;
        this.sLThingRepository = sLThingRepository;
        this.invokePhillipsHueLight = invokePhillipsHueLight;
        this.invokeIkeaLight = invokeIkeaLight;
        this.fetchThingsStatusFunction = fetchThingsStatusFunction;
    }
    InvokeActionCommandOutPort invokePhillipsHueLight;
    InvokeActionCommandOutPort invokeIkeaLight;

    FetchThingsStatusFunction fetchThingsStatusFunction;

    @Override
    public Mono<InvokeActionResponse> invokeAction(InvokeActionInput input) {
        return thingRepository.findById(input.getId())
                .filter(thing -> isThingCapableOfHandlingAction(thing, input.getAction()))
                .flatMap(thing -> {
                    String action = input.getAction();
                    if (action.equalsIgnoreCase(SupportedActions.light.name())
                            || action.equalsIgnoreCase(SupportedActions.color.name())) {
                        if (thing.getVendor().getId().equalsIgnoreCase(SupportedVendors.Phillips_Hue.name())) {
                            Document requestBody = null;
                            if (actionRequiresBody(thing.getActions().get(action))) {
                                requestBody = constructRequestBody(thing.getActions().get(action), input.getParameters());
                            }
                            return invokePhillipsHueLight.invokeLightAction(thing, requestBody)
                                    .map(res-> {
                                        System.out.println(res);
                                        return res;
                                    });
                        } if (thing.getVendor().getId().equalsIgnoreCase(SupportedVendors.Ikea.name())){
                            Document requestBody = null;
                            if (actionRequiresBody(thing.getActions().get(action))) {
                                requestBody = constructRequestBody(thing.getActions().get(action), input.getParameters());
                            }
                            return invokeIkeaLight.invokeLightAction(thing, requestBody)
                                    .map(res-> {
                                        System.out.println(res);
                                        return res;
                                    });
                        }
                        // TODO: Add vendor specific implementation
                        else {
                            return Mono.empty();
                        }
                    }
                    if (action.equalsIgnoreCase(SupportedActions.runtime.name())
                            || action.equalsIgnoreCase(SupportedActions.cost.name())
                            || action.equalsIgnoreCase(SupportedActions.status.name())
                            || action.equalsIgnoreCase(SupportedActions.timetable.name())
                    ) {
                        return fetchThingsStatusFunction.fetchStatus(input.getId())
                                .map(SLThing::getStatus)
                                .map(res-> {
                                    System.out.println(res);
                                    return res;
                                });
                    }
                    return Mono.empty();
                }).map(status -> InvokeActionResponse.builder()
                        .id(input.getId()).status(status).build());
    }

    private Boolean isThingCapableOfHandlingAction(Thing thing, String action) {
        return thing.getActions().containsKey(action);
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