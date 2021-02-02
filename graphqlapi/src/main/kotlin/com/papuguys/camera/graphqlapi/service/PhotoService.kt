package com.papuguys.camera.graphqlapi.service

import com.papuguys.camera.graphqlapi.model.Photo
import com.papuguys.camera.graphqlapi.repository.PhotoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.io.File
import java.util.*
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

    @Scheduled(fixedRate = 10000)
    fun takePhotoSheduled() {
        photoRepository.findFirstByPathNull()?.let {
            log.info("Taking photo {}", dateFormat.format(Date()))
            val file = File("pic", "${it.name}${it.timestamp}.png")
            getExternalPhoto().subscribe { bytes ->
                file.writeBytes(bytes)
                it.path = file.path
                photoRepository.save(it)
            }
        }
    }

    fun findAll(): List<Photo> {
        return photoRepository.findAllByPathIsNotNull()
    }

    fun find(id: String): Optional<Photo> {
        return photoRepository.findById(id)
    }

    fun create(name: String = "cap"): Photo {
        return Photo(name, null).let {
            it.id = UUID.randomUUID().toString()
            it.timestamp = System.currentTimeMillis()
            photoRepository.save(it)
        }
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