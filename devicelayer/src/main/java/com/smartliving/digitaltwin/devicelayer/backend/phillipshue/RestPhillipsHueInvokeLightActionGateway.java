package com.smartliving.digitaltwin.devicelayer.backend.phillipshue;

import com.smartliving.digitaltwin.devicelayer.domain.core.InvokeActionCommandOutPort;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SupportedActions;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import com.smartliving.digitaltwin.devicelayer.domain.wot.hypermedia.Form;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecurityScheme;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestPhillipsHueInvokeLightActionGateway implements InvokeActionCommandOutPort {

    WebClient webClient;

    @Override
    public Mono<Document> invokeLightAction(Thing thing, Document requestBody) {
        Form form = thing.getActions().get(SupportedActions.light.name()).getForms().get(0);
        String uri = form.getHref();
        MediaType mediaType = MediaType.parseMediaType(form.getContentType());
        SecurityScheme securityScheme = thing.getSecurityDefinitions().get(thing.getSecurity().get(0).name());
        String header = securityScheme.getName();
        return webClient.put().uri(URI.create(uri)).contentType(mediaType)
                .header(header, "<api-key>")
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(Document.class);
    }
}
