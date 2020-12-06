package com.papuguys.camera.graphqlapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CameraApplication

fun main(args: Array<String>) {
    runApplication<CameraApplication>(*args)
}
