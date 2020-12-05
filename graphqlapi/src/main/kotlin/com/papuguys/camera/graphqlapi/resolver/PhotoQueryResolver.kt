package com.papuguys.camera.graphqlapi.resolver

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.papuguys.camera.graphqlapi.model.Photo
import com.papuguys.camera.graphqlapi.repository.PhotoRepository
import org.springframework.data.mongodb.core.MongoOperations

class PhotoQueryResolver(
    val snackRepository: PhotoRepository,
    private val mongoOperations: MongoOperations
) : GraphQLQueryResolver {

    fun photos(): List<Photo> {
        return snackRepository.findAll()
    }
}