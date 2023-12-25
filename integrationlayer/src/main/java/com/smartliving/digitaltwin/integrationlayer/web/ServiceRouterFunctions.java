package com.smartliving.digitaltwin.integrationlayer.web;

import com.smartliving.digitaltwin.integrationlayer.domain.core.FetchIntegrationQueryInPort;
import com.smartliving.digitaltwin.integrationlayer.domain.core.InvokeActionCommandInPort;
import com.smartliving.digitaltwin.integrationlayer.domain.core.InvokeActionResponse;
import com.smartliving.digitaltwin.integrationlayer.domain.wot.core.Integration;
import com.smartliving.digitaltwin.integrationlayer.web.dto.InvokeActionInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@AllArgsConstructor
public class ServiceRouterFunctions {
    InvokeActionCommandInPort invokeActionCommandInPort;

    FetchIntegrationQueryInPort fetchAllSLServices;

    @Bean
    @RouterOperation(operation = @Operation(operationId = "invokeAction", requestBody = @RequestBody(content = @Content(
            schema=@Schema(implementation = InvokeActionInput.class))),  summary = "invoke thing action"))
    RouterFunction<ServerResponse> invokeAction(){
        return RouterFunctions.route(PUT("/v1/integration/action").and(RequestPredicates.contentType(MediaType.APPLICATION_JSON))
                , request -> ok().body(
                        request.bodyToMono(InvokeActionInput.class)
                                .flatMap(body -> invokeActionCommandInPort.invokeAction(body))
                        , InvokeActionResponse.class));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getIntegrationDescription", summary = "get integration description based on wot description"))
    RouterFunction<ServerResponse> getThing(){
        return RouterFunctions.route(GET("/v1/integration").and(queryParam("id", t-> true))
                , request -> ok().body(fetchAllSLServices.fetchIntegration(request.queryParam("id").get()), Integration.class));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllIntegrationDescription", summary = "get all integration description based on wot description"))
    RouterFunction<ServerResponse> getAllThings(){
        return RouterFunctions.route(GET("/v1/integration/all")
                , request -> ok().body(fetchAllSLServices.fetchAllIntegrations(), Integration.class));
    }
}
