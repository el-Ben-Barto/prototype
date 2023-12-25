package com.smartliving.digitaltwin.devicelayer.backend.phillipshue;

import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto.HueDiscoveryResponse;
import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto.HueEventResponse;
import com.smartliving.digitaltwin.devicelayer.domain.event.ListenEventVendorQueryOutPort;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SSEPhillipsHueFetchThingEventsGateway implements ListenEventVendorQueryOutPort {

    WebClient webClient;

    String uri;

    String path;

    @Override
    public Flux<ServerSentEvent<List<Document>>> listenEvents() {
        ParameterizedTypeReference<ServerSentEvent<List<Document>>> type
                = new ParameterizedTypeReference<>() {
        };

        return webClient.get().uri(URI.create(uri+path)).accept(MediaType.TEXT_EVENT_STREAM)
                .header("hue-application-key", "<api-key>")
                .retrieve()
                .bodyToFlux(type);
    }
}
