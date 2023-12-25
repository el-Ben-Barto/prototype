package com.smartliving.digitaltwin.devicelayer.domain.core;

import com.smartliving.digitaltwin.devicelayer.backend.MongoDBSLThingRepository;
import com.smartliving.digitaltwin.devicelayer.backend.MongoDBThingRepository;
import com.smartliving.digitaltwin.devicelayer.domain.status.FetchThingsStatusFunction;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLThing;
import com.smartliving.digitaltwin.devicelayer.web.dto.SLThingInput;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CreateThingCommand implements CreateThingCommandInPort {
    MongoDBThingRepository thingRepository;
    MongoDBSLThingRepository sLThingRepository;
    FetchThingsStatusFunction fetchThingsStatusFunction;

    @Override
    public Mono<SLThing> createThing(SLThingInput input) {
        return thingRepository.save(input.getThingDefinition())
                .flatMap(thing -> {
                    if (input.getIsVendorProduct()) {
                        return fetchThingsStatusFunction.fetchStatus(thing.getId());
                    } else {
                        return sLThingRepository.save(createStatus(thing.getId(), input.getInitialStatus()));
                    }
                });
    }
    private SLThing createStatus(String thingId, Document status) {
        return SLThing.builder().id(thingId).status(status).timestamp(LocalDate.now()).build();
    }
}
