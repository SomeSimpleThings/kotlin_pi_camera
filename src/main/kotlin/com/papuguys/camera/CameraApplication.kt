package com.papuguys.camera

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CameraApplication

fun main(args: Array<String>) {
	runApplication<CameraApplication>(*args)
}
