package com.smartliving.digitaltwin.servicelayer.web;

import com.smartliving.digitaltwin.servicelayer.domain.data.SLInteractionResponse;
import com.smartliving.digitaltwin.servicelayer.domain.core.CreateSLServiceCommandInPort;
import com.smartliving.digitaltwin.servicelayer.domain.core.FetchSLServiceQueryInPort;
import com.smartliving.digitaltwin.servicelayer.domain.data.SLService;
import com.smartliving.digitaltwin.servicelayer.domain.core.InvokeActionCommandInPort;
import com.smartliving.digitaltwin.servicelayer.web.dto.SLServiceInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@AllArgsConstructor
public class ServiceRouterFunctions {

    FetchSLServiceQueryInPort fetchSLServiceQueryInPort;

    CreateSLServiceCommandInPort createSLServiceCommandInPort;

    InvokeActionCommandInPort performSLServiceCommandInPort;

    @Bean
    @RouterOperation(operation = @Operation(operationId = "createService",requestBody = @RequestBody(content = @Content(
            schema=@Schema(implementation = SLServiceInput.class))), summary = "create new registered service"))
    RouterFunction<ServerResponse> createService(){
        return RouterFunctions.route(POST("/v1/service/create")
                , request -> ok().body(
                    request.bodyToMono(SLServiceInput.class)
                            .flatMap(body-> createSLServiceCommandInPort.createSLService(body))
                , SLService.class));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "invokeService", summary = "perform registered service"))
    RouterFunction<ServerResponse> performService(){
        return RouterFunctions.route(POST("/v1/service/action").and(queryParam("id", t-> true))
                , request -> ok().body(performSLServiceCommandInPort.performSLServiceById(request.queryParam("id").get()), SLInteractionResponse.class));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllServices", summary = "get all registered services"))
    RouterFunction<ServerResponse> getAllSLServices(){
     return RouterFunctions.route(GET("/v1/service/all")
             , request -> ok().body(fetchSLServiceQueryInPort.fetchAllSLServices(), SLService.class));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAllServicesByStatus", summary = "get all registered services by activation status"))
    RouterFunction<ServerResponse> getAllSLServicesByStatus(){
        return RouterFunctions.route(GET("/v1/service/all/status").and(queryParam("status", t-> true))
                , request -> ok().body(fetchSLServiceQueryInPort.fetchAllActiveSLServices(Boolean.valueOf(request.queryParam("status").get())), SLService.class));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getServiceByName", summary = "get a registered service by name"))
    RouterFunction<ServerResponse> getSLServiceByName(){
        return RouterFunctions.route(GET("/v1/service/all/name").and(queryParam("name", t-> true))
                , request -> ok().body(fetchSLServiceQueryInPort.fetchSLServicesByReadableName(request.queryParam("name").get()), SLService.class));
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getServiceById", summary = "get a registered service by id"))
    RouterFunction<ServerResponse> getSLServiceById(){
        return RouterFunctions.route(GET("/v1/service/id").and(queryParam("id", t-> true))
                , request -> ok().body(fetchSLServiceQueryInPort.fetchSLServicesById(request.queryParam("id").get()), SLService.class));
    }
}
