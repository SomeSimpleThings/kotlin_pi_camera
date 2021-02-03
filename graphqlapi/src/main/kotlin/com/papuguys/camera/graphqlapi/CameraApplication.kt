package com.papuguys.camera.graphqlapi

import com.papuguys.camera.raspcamera.RaspberryCameraApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CameraApplication

fun main(args: Array<String>) {
//    RaspberryCameraApi.NativeLibInstaller.install()
    runApplication<CameraApplication>(*args)
}
