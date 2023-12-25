package com.smartliving.digitaltwin.devicelayer.web;

import com.smartliving.digitaltwin.devicelayer.domain.core.CreateThingCommandInPort;
import com.smartliving.digitaltwin.devicelayer.domain.core.FetchThingsQueryInPort;
import com.smartliving.digitaltwin.devicelayer.domain.core.InvokeActionCommandInPort;
import com.smartliving.digitaltwin.devicelayer.domain.core.InvokeActionResponse;
import com.smartliving.digitaltwin.devicelayer.domain.status.FetchThingsStatusQueryInPort;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLThing;
import com.smartliving.digitaltwin.devicelayer.web.dto.InvokeActionInput;
import com.smartliving.digitaltwin.devicelayer.web.dto.SLThingInput;
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

    FetchThingsQueryInPort fetchAllSLServices;

    FetchThingsStatusQueryInPort fetchVendorThingsStatusQueryInPort;

    CreateThingCommandInPort createThingCommandInPort;

    InvokeActionCommandInPort invokeActionCommandInPort;

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllThingsDescription", summary = "get all things description based on wot description"))
    RouterFunction<ServerResponse> getAllThings(){
        return RouterFunctions.route(GET("/v1/thing/all")
                , request -> ok().body(fetchAllSLServices.fetchAllThings(), Thing.class));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getThingDescription", summary = "get things description based on wot description"))
    RouterFunction<ServerResponse> getThing(){
        return RouterFunctions.route(GET("/v1/thing").and(queryParam("id", t-> true))
                , request -> ok().body(fetchAllSLServices.fetchThing(request.queryParam("id").get()), Thing.class));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getThingStatus", summary = "get things status based on wot description"))
    RouterFunction<ServerResponse> getThingStatus(){
        return RouterFunctions.route(GET("/v1/thing/status").and(queryParam("id", t-> true))
                , request -> ok().body(fetchVendorThingsStatusQueryInPort.fetchStatus(request.queryParam("id").get()), SLThing.class));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "registerThing",requestBody = @RequestBody(content = @Content(
            schema=@Schema(implementation = SLThingInput.class))), summary = "create new registered thing. Actions are defined according to WOT thing description referring ActionAffordance & PropertyAffordance"))
    RouterFunction<ServerResponse> registerThing(){
        return RouterFunctions.route(POST("/v1/thing/create")
                , request -> ok().body(
                        request.bodyToMono(SLThingInput.class)
                                .flatMap(body-> createThingCommandInPort.createThing(body))
                        , SLThing.class));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "invokeAction", requestBody = @RequestBody(content = @Content(
            schema=@Schema(implementation = InvokeActionInput.class))),  summary = "invoke thing action"))
    RouterFunction<ServerResponse> invokeAction(){
        return RouterFunctions.route(PUT("/v1/thing/action").and(RequestPredicates.contentType(MediaType.APPLICATION_JSON))
                , request -> ok().body(
                        request.bodyToMono(InvokeActionInput.class)
                                .flatMap(body -> invokeActionCommandInPort.invokeAction(body))
                        , InvokeActionResponse.class));
    }


}
