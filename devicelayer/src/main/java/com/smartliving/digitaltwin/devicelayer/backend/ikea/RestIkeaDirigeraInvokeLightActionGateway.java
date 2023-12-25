package com.smartliving.digitaltwin.devicelayer.backend.ikea;

import com.smartliving.digitaltwin.devicelayer.domain.core.InvokeActionCommandOutPort;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SupportedActions;
import com.smartliving.digitaltwin.devicelayer.domain.wot.hypermedia.Form;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecurityScheme;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestIkeaDirigeraInvokeLightActionGateway implements InvokeActionCommandOutPort {

    WebClient webClient;

    @Override
    public Mono<Document> invokeLightAction(Thing thing, Document requestBody) {
        Form form = thing.getActions().get(SupportedActions.light.name()).getForms().get(0);
        String uri = form.getHref();
        MediaType mediaType = MediaType.parseMediaType(form.getContentType());
        SecurityScheme securityScheme = thing.getSecurityDefinitions().get(thing.getSecurity().get(0).name());
        String header = securityScheme.getName();
        List<Document> body = new ArrayList<>();
        body.add(requestBody);
        return webClient.patch().uri(URI.create(uri)).contentType(mediaType)
                .header(header, "Bearer <bearer token> ")
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .toBodilessEntity()
                .map(res-> new Document().append("status",res.getStatusCode()));
    }
}
