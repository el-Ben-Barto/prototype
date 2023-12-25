package com.smartliving.digitaltwin.devicelayer.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.smartliving.digitaltwin.devicelayer.backend.ikea.RestIkeaDirigeraFetchThingStatusGateway;
import com.smartliving.digitaltwin.devicelayer.backend.ikea.RestIkeaDirigeraInvokeLightActionGateway;
import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.RestPhillipsHueFetchThingStatusGateway;
import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.RestPhillipsHueInvokeLightActionGateway;
import com.smartliving.digitaltwin.devicelayer.backend.phillipshue.SSEPhillipsHueFetchThingEventsGateway;
import com.smartliving.digitaltwin.devicelayer.domain.core.InvokeActionCommandOutPort;
import com.smartliving.digitaltwin.devicelayer.domain.event.ListenEventVendorQueryOutPort;
import com.smartliving.digitaltwin.devicelayer.domain.status.FetchVendorThingsStatusQueryOutPort;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.AccessLevel;
import lombok.SneakyThrows;
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
    @SneakyThrows
    public WebClient webClient(){
        // TODO: For production usage adapt SslContext to include server certification validation and remove InsecureTrustManagerFactory.Instance

        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient
                .create()
                .secure(t-> t.sslContext(sslContext))
                .wiretap("reactor.netty.http.client.HttpClient",
                        LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL);

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return webClient;
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

    @Bean(name = "phillips-hue-status")
    FetchVendorThingsStatusQueryOutPort fetchPhillisHueQueryOutPort() {
        return new RestPhillipsHueFetchThingStatusGateway(webClient());
    }

    @Bean(name = "ikea-status")
    FetchVendorThingsStatusQueryOutPort fetchIkeaQueryOutPort() {
        return new RestIkeaDirigeraFetchThingStatusGateway(webClient());
    }

    @Bean
    ListenEventVendorQueryOutPort listenEventVendorQueryOutPort(@Value("${vendor.hue.baseurl}") String uri, @Value("${vendor.hue.event}") String path) {
        return new SSEPhillipsHueFetchThingEventsGateway(webClient(), uri, path);
    }

    @Bean(name = "phillips-hue-light-action")
    InvokeActionCommandOutPort invokeRestPhillipsHueInvokeLightActionGateway(){
        return new RestPhillipsHueInvokeLightActionGateway(webClient());
    }

    @Bean(name = "ikea-light-action")
    InvokeActionCommandOutPort invokeRestIkeaDirigeraInvokeLightGateway(){
        return new RestIkeaDirigeraInvokeLightActionGateway(webClient());
    }

}
