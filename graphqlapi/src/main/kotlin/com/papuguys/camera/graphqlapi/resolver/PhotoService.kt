package com.papuguys.camera.graphqlapi.resolver

import com.papuguys.camera.graphqlapi.model.Photo
import com.papuguys.camera.graphqlapi.repository.PhotoRepository
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
class PhotoService(
    val photoRepository: PhotoRepository,
    private val mongoOperations: MongoOperations
) {
    fun findAll(): List<Photo> {
        return photoRepository.findAll()
    }

    fun createPhoto(name: String): Photo {
        val photo = Photo(name, File("kek").path)
        photo.id = UUID.randomUUID().toString();
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