package com.smartliving.digitaltwin.integrationlayer.backend.weather;

import com.smartliving.digitaltwin.integrationlayer.domain.core.InvokeActionCommandOutPort;
import com.smartliving.digitaltwin.integrationlayer.domain.wot.core.Integration;
import com.smartliving.digitaltwin.integrationlayer.domain.wot.extension.SupportedActions;
import com.smartliving.digitaltwin.integrationlayer.domain.wot.hypermedia.Form;
import com.smartliving.digitaltwin.integrationlayer.domain.wot.security.SecurityScheme;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestOpenWeatherMapWeatherForecastActionGateway implements InvokeActionCommandOutPort {

    WebClient webClient;

    @Override
    public Mono<Document> invokeAction(Integration integration, Document requestBody) {
        Form form = integration.getActions().get(SupportedActions.weather.name()).getForms().get(0);
        String stringUri = form.getHref();
        SecurityScheme securityScheme = integration.getSecurityDefinitions().get(integration.getSecurity().get(0).name());
        String appid = securityScheme.getName();

        MultiValueMap<String,String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("lat", requestBody.get("lat").toString());
        queryParams.add("lon", requestBody.get("lon").toString());
        queryParams.add(appid, "<api-key>");

        URI uri = UriComponentsBuilder.fromUri(URI.create(stringUri))
                .queryParams(queryParams)
                .build().toUri();

        return webClient.get().uri(uri)
                .retrieve()
                .bodyToMono(Document.class);
    }
}
