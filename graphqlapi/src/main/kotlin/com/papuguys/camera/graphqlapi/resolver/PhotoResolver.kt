package com.papuguys.camera.graphqlapi.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.papuguys.camera.graphqlapi.model.Photo
import com.papuguys.camera.graphqlapi.repository.PhotoRepository
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Component
import java.util.*

@Component
class PhotoQueryResolver(
    val photoRepository: PhotoRepository,
    private val mongoOperations: MongoOperations
) : GraphQLQueryResolver {

    fun photos(): List<Photo> {
        return photoRepository.findAll()
    }
}


@Component
class PhotoMutationResolver(val photoRepository: PhotoRepository) : GraphQLMutationResolver {

    fun newPhoto(name: String): Photo {
        val photo = Photo(name)
        photo.id = UUID.randomUUID().toString();
        photoRepository.save(photo)
        return photo
    }

    fun updatePhoto(id: String, name: String): Photo {
        val photo = photoRepository.findById(id)
        photo.ifPresent {
            it.name = name
            photoRepository.save(it)
        }
        return photo.get()
    }

    fun deletePhoto(id: String): Boolean {
        photoRepository.deleteById(id)
        return true
    }
}