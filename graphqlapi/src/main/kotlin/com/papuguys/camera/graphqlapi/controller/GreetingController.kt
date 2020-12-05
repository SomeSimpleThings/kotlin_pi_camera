package com.papuguys.camera.graphqlapi.controller

import com.papuguys.camera.graphqlapi.model.Greeting
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("/api")
class GreetingController {

    val counter: AtomicLong = AtomicLong();

    @RequestMapping(value = ["/greeting"], method = [(RequestMethod.GET)])
    fun getHelloWordMessage(): ResponseEntity<String> =
        ResponseEntity.ok("Hello World")

    @RequestMapping(value = ["/greeting/{name}"], method = [(RequestMethod.GET)])
    fun getHelloWordMessageWithName(
        @PathVariable("name") name: String
    ): ResponseEntity<Any> =
        if (name != "Stupid") {
            ResponseEntity.ok(
                Greeting(
                    id = counter.incrementAndGet(),
                    content = name
                )
            )
        } else {
            ResponseEntity.badRequest().body("I am Stupid")
        }
}