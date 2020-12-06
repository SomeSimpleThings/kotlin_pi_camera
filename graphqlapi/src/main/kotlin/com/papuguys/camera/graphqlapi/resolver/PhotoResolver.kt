package com.papuguys.camera.graphqlapi.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.papuguys.camera.graphqlapi.model.Photo
import com.papuguys.camera.graphqlapi.service.PhotoService
import org.springframework.stereotype.Component

@Component
class PhotoQueryResolver(
    val photoService: PhotoService,
) : GraphQLQueryResolver {

    fun photos(): List<Photo> {
        return photoService.findAll()
    }
}

@Component
class PhotoMutationResolver(val photoService: PhotoService) : GraphQLMutationResolver {

    fun newPhoto(name: String): Photo {
        return photoService.createPhoto(name)
    }

    fun updatePhoto(id: String, name: String): Photo {
        return photoService.update(id, name)
    }

    fun deletePhoto(id: String): Boolean {
        return photoService.delete(id)
    }
}