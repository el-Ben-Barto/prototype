package com.smartliving.digitaltwin.devicelayer.domain.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartliving.digitaltwin.devicelayer.backend.MongoDBSLThingRepository;
import com.smartliving.digitaltwin.devicelayer.backend.MongoDBThingRepository;
import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto.HueDiscoveryResponseData;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLDigitalTwinClassification;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLThing;
import com.smartliving.digitaltwin.devicelayer.domain.wot.hypermedia.Form;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecuritySchemaType;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class FetchThingsStatusQuery implements FetchThingsStatusQueryInPort {

    FetchThingsStatusFunction fetchThingsStatusFunction;

    @Override
    public Mono<SLThing> fetchStatus(String thingId) {
        return fetchThingsStatusFunction.fetchStatus(thingId);
    }
}
