package com.smartliving.digitaltwin.servicelayer.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.smartliving.digitaltwin.servicelayer.backend.RestDeviceLayerPerformActionGateway;
import com.smartliving.digitaltwin.servicelayer.backend.RestIntegrationLayerPerformActionGateway;
import com.smartliving.digitaltwin.servicelayer.domain.core.InvokeActionCommandOutPort;
import io.netty.handler.logging.LogLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BeanConfiguration {

    String dbConnectionString;

    String database;

    public BeanConfiguration(@Value("${mongodb.connection}") String dbConnectionString
            , @Value("${mongodb.database}") String database) {
        this.dbConnectionString = dbConnectionString;
        this.database = database;
    }
    @Bean
    public WebClient webClient(){
        HttpClient httpClient = HttpClient
                .create()
                .wiretap("reactor.netty.http.client.HttpClient",
                        LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL);

        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
    }
    @Bean
    public MongoClient mongoClient() {
        ConnectionString conString = new ConnectionString(dbConnectionString);

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(conString)
                .serverApi(serverApi)
                .build();

        return MongoClients.create(clientSettings);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient(), database);
    }


    @Bean(name = "invoke-action-thing")
    InvokeActionCommandOutPort performSLThingsCommandOutPort(@Value("${web.devicelayer.uri}") String uri, @Value("${web.devicelayer.performaction}") String path) {
        return new RestDeviceLayerPerformActionGateway(webClient(), uri, path);
    }

    @Bean(name = "invoke-action-integration")
    InvokeActionCommandOutPort performSLIntegrationCommandOutPort(@Value("${web.integrationlayer.uri}") String uri, @Value("${web.integrationlayer.performaction}") String path) {
        return new RestIntegrationLayerPerformActionGateway(webClient(), uri, path);
    }


}
