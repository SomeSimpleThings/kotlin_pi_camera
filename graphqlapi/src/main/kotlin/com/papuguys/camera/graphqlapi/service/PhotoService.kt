package com.papuguys.camera.graphqlapi.service

import com.papuguys.camera.graphqlapi.model.Photo
import com.papuguys.camera.graphqlapi.repository.PhotoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import org.springframework.scheduling.annotation.Scheduled

import java.text.SimpleDateFormat


@Service
@Component
class PhotoService(
    val photoRepository: PhotoRepository,
    private val mongoOperations: MongoOperations
) {

    private val log: Logger = LoggerFactory.getLogger(PhotoService::class.java)
    private val dateFormat = SimpleDateFormat("HH:mm:ss")

    @Scheduled(fixedRate = 5000)
    fun reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(Date()))
        createPhoto("captured")
    }

    fun findAll(): List<Photo> {
        return photoRepository.findAll()
    }

    fun createPhoto(name: String): Photo {
        val screen = BufferedImage(100, 100, TYPE_INT_RGB)
        val timestamp = System.currentTimeMillis()
        val file = File("pic", "$name$timestamp.jpg")
        ImageIO.write(screen, "jpg", file);
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