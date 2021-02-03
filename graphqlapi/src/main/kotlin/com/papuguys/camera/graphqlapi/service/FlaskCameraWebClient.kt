package com.papuguys.camera.graphqlapi.service

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.http.client.reactive.ReactorClientHttpConnector

import org.springframework.http.client.reactive.ClientHttpConnector
import reactor.core.publisher.Mono

import reactor.netty.http.client.HttpClient
import java.time.Duration


@Bean
fun localApiClient(): WebClient {
    return WebClient.builder()
        .exchangeStrategies(extendedExchangeStrategies())
        .baseUrl("http://192.168.1.18:5000/cam/api/v1/photos/")
        .clientConnector(connector())
        .build()
}

@Bean
fun extendedExchangeStrategies(): ExchangeStrategies {
    return ExchangeStrategies.builder()
        .codecs { configurer ->
            configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024)
        }
        .build()
}

@Bean
fun connector(): ClientHttpConnector {
    val httpClient: HttpClient = HttpClient
        .create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 50000)
        .doOnConnected { conn ->
            conn
                .addHandlerLast(ReadTimeoutHandler(50))
                .addHandlerLast(WriteTimeoutHandler(50))
        }
    return ReactorClientHttpConnector(httpClient)
}

@Bean
fun getExternalPhoto(): Mono<ByteArray> {
    return localApiClient()
        .get()
        .accept(MediaType.IMAGE_PNG)
        .retrieve()
        .bodyToMono(ByteArray::class.java)
}