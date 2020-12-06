package com.papuguys.camera.graphqlapi.service

import com.papuguys.camera.graphqlapi.model.Photo
import com.papuguys.camera.graphqlapi.repository.PhotoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.reactive.function.client.ExchangeStrategies

import java.text.SimpleDateFormat
import org.springframework.web.reactive.function.client.WebClient
import java.util.function.Consumer


@Service
@Component
class PhotoService(
    val photoRepository: PhotoRepository,
    private val mongoOperations: MongoOperations
) {

    private val log: Logger = LoggerFactory.getLogger(PhotoService::class.java)
    private val dateFormat = SimpleDateFormat("HH:mm:ss")

    @Scheduled(fixedRate = 10000)
    fun reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(Date()))
        getPhoto("captured")
    }

    fun findAll(): List<Photo> {
        return photoRepository.findAll()
    }

    fun getPhoto(name: String = "cap"): Photo {
//        val screen = BufferedImage(100, 100, TYPE_INT_RGB)
        val timestamp = System.currentTimeMillis()
        val file = File("pic", "$name$timestamp.png")
//        ImageIO.write(screen, "jpg", file);
        val client = WebClient.builder()
            .exchangeStrategies(
                ExchangeStrategies.builder()
                    .codecs { configurer ->
                        configurer
                            .defaultCodecs()
                            .maxInMemorySize(16 * 1024 * 1024)
                    }
                    .build()
            )
            .baseUrl("http://192.168.1.18:5000/get_image")
            .build()
        val image = client
            .get()
            .accept(MediaType.IMAGE_PNG)
            .retrieve()
            .bodyToMono(ByteArray::class.java)
            .block()
        image?.let { file.writeBytes(it) }
        val photo = Photo(name, file.path)
        photo.id = UUID.randomUUID().toString()
        photo.timestamp = timestamp
        return photoRepository.save(photo)
    }

    fun update(id: String, name: String): Photo {
        val photo = photoRepository.findById(id)
        photo.ifPresent {
            it.name = name
            photoRepository.save(it)
        }
        return photo.get()
    }

    fun delete(id: String): Boolean {
        photoRepository.deleteById(id)
        return true
    }
}