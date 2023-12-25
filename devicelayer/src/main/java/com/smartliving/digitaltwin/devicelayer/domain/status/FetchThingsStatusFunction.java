package com.smartliving.digitaltwin.devicelayer.domain.status;

import com.smartliving.digitaltwin.devicelayer.backend.MongoDBSLThingRepository;
import com.smartliving.digitaltwin.devicelayer.backend.MongoDBThingRepository;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLDigitalTwinClassification;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLThing;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SupportedVendors;
import com.smartliving.digitaltwin.devicelayer.domain.wot.hypermedia.Form;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecuritySchemaType;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

@Service
public class FetchThingsStatusFunction {

    private static final String ACTION_TYPE_STATUS = "status";

    public FetchThingsStatusFunction(@Qualifier(value = "phillips-hue-status") FetchVendorThingsStatusQueryOutPort fetchPhillipsStatus,
                                     @Qualifier(value = "ikea-status") FetchVendorThingsStatusQueryOutPort fetchIkeaStatus,
                                     MongoDBThingRepository thingRepository,
                                     MongoDBSLThingRepository slThingRepository) {
        this.fetchPhillipsStatus = fetchPhillipsStatus;
        this.fetchIkeaStatus = fetchIkeaStatus;
        this.thingRepository = thingRepository;
        this.slThingRepository = slThingRepository;
    }

    FetchVendorThingsStatusQueryOutPort fetchPhillipsStatus;

    FetchVendorThingsStatusQueryOutPort fetchIkeaStatus;

    MongoDBThingRepository thingRepository;
    MongoDBSLThingRepository slThingRepository;

    public Mono<SLThing> fetchStatus(String thingId) {
        return thingRepository.findById(thingId)
                .filter(this::isConnectableDTClassification)
                .map(this::mapObject)
                .flatMap(request-> {
                    if(request.getVendorId().equals(SupportedVendors.Phillips_Hue.name())){
                     return fetchPhillipsStatus.fetchStatus(request);
                    }
                    if(request.getVendorId().equals(SupportedVendors.Ikea.name())){
                        return fetchIkeaStatus.fetchStatus(request);
                    } else {
                        return Mono.empty();
                    }
                })
                .flatMap(response-> slThingRepository.save(createStatus(thingId, response)))
                .switchIfEmpty(Mono.defer(()-> slThingRepository.findById(thingId)));
    }
    private Boolean isConnectableDTClassification(Thing thing){
        return !(thing.getClassification().equals(SLDigitalTwinClassification.Digital_Model_Virtual)
                ||thing.getClassification().equals(SLDigitalTwinClassification.Digital_Model));
    }
    private SLThing createStatus(String thingId, Document status) {
        return SLThing.builder().id(thingId).status(status).timestamp(LocalDate.now()).build();
    }
    private FetchThingStatusRequest mapObject(Thing thing){
        String vendorId = thing.getVendor().getId();
        Form form = thing.getActions()
                .get(ACTION_TYPE_STATUS)
                .getForms()
                .get(0);
        Map<String, SecurityScheme> securityDefinitions = thing.getSecurityDefinitions();
        SecuritySchemaType securitySchemaType = thing.getSecurity().get(0);

        return FetchThingStatusRequest.builder()
                .vendorId(vendorId)
                .securitySchemaType(securitySchemaType)
                .securityDefinitions(securityDefinitions)
                .form(form)
                .build();
    }
}
