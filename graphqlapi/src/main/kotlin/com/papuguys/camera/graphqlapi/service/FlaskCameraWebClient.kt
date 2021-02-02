package com.papuguys.camera.graphqlapi.service

import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@Bean
fun localApiClient(): WebClient {
    return WebClient.builder()
        .exchangeStrategies(extendedExchangeStrategies())
        .baseUrl("http://192.168.1.18:5000/get_image")
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
fun getExternalPhoto(): Mono<ByteArray> {
    return localApiClient()
        .get()
        .accept(MediaType.IMAGE_PNG)
        .retrieve()
        .bodyToMono(ByteArray::class.java)
}