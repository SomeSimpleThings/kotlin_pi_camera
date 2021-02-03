package com.papuguys.camera.graphqlapi.controller

import com.papuguys.camera.graphqlapi.model.Greeting
import com.papuguys.camera.graphqlapi.service.PhotoService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong

import java.io.IOException

import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.ServletContext

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.io.File


@RestController
@RequestMapping("/imageupload")
@Component
class ImageUploadController(val photoService: PhotoService) {

    @Autowired
    private val servletContext: ServletContext? = null

    val counter: AtomicLong = AtomicLong();

    @GetMapping(value = ["/{id}"], produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    @Throws(IOException::class)
    fun getImageWithMediaType(@PathVariable id: String): ResponseEntity<UrlResource> {
        val uri = UrlResource(File("pic/${id}.png").toURI())
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""
                        + uri.filename + "\""
            )
            .body(uri);
    }

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