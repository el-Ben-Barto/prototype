package com.smartliving.digitaltwin.devicelayer.backend.ikea;

import com.smartliving.digitaltwin.devicelayer.domain.status.FetchThingStatusRequest;
import com.smartliving.digitaltwin.devicelayer.domain.status.FetchVendorThingsStatusQueryOutPort;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecurityScheme;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestIkeaDirigeraFetchThingStatusGateway implements FetchVendorThingsStatusQueryOutPort {
    WebClient webClient;

    @Override
    public Mono<Document> fetchStatus(FetchThingStatusRequest request) {
        String uri = request.getForm().getHref();
        MediaType mediaType = MediaType.parseMediaType(request.getForm().getContentType());
        SecurityScheme securityScheme = request.getSecurityDefinitions().get(request.getSecuritySchemaType().name());
        String header = securityScheme.getName();

        return webClient.get().uri(URI.create(uri)).accept(mediaType)
                .header(header, "Bearer <bearer token>")
                .retrieve()
                .bodyToMono(Document.class);
    }
}
