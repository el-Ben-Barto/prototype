package com.smartliving.digitaltwin.servicelayer.backend;

import com.smartliving.digitaltwin.servicelayer.domain.core.InvokeActionCommandOutPort;
import com.smartliving.digitaltwin.servicelayer.domain.data.SLInteractionResponse;
import com.smartliving.digitaltwin.servicelayer.domain.data.SLInteractionRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestIntegrationLayerPerformActionGateway implements InvokeActionCommandOutPort {

    WebClient webClient;

    String uri;

    String path;

    @Override
    public Mono<SLInteractionResponse> invokeAction(SLInteractionRequest request) {
        return webClient.put().uri(URI.create(uri+path)).accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(SLInteractionResponse.class)
                .doOnError(throwable -> Mono.just(SLInteractionResponse.builder().id(request.getId()).status(new Document().append("error", throwable.getCause())).build()));
    }
}
