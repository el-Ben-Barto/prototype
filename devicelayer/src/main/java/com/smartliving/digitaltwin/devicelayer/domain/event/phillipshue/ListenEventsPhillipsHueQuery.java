package com.smartliving.digitaltwin.devicelayer.domain.event.phillipshue;

import com.smartliving.digitaltwin.devicelayer.backend.MongoDBEventRepository;
import com.smartliving.digitaltwin.devicelayer.domain.event.ListenEventVendorQueryOutPort;
import com.smartliving.digitaltwin.devicelayer.domain.event.ListenEventVendorQueryInPort;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLThingEvent;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ListenEventsPhillipsHueQuery implements ListenEventVendorQueryInPort {

    ListenEventVendorQueryOutPort listenEventVendorOutPort;

    MongoDBEventRepository mongoDBEventRepository;


    @PostConstruct
    public void init(){
        log.info("Start Event listener");
        listenEvents().subscribe(content -> log.info("Time: {}, content[{}] ", LocalTime.now(), content)
                , error -> log.error("Error receiving SSE: {}", error)
                , () -> log.info("Completed!!!"));
    }
    @Override
    public Flux<SLThingEvent> listenEvents() {
        return listenEventVendorOutPort.listenEvents()
                .filter(this::isNotInitMessage)
                .map(sse -> mapObject(sse.data()))
                .flatMap(slThingEvent -> mongoDBEventRepository.save(slThingEvent));
    }

    private Boolean isNotInitMessage(ServerSentEvent sse){
        return sse.data() != null;
    }

    private SLThingEvent mapObject(List<Document> input){
        return SLThingEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventChain(input)
                .timestamp(LocalDate.now())
                .build();
    }
}
