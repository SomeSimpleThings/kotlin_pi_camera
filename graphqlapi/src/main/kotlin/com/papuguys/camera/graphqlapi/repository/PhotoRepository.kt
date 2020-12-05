package com.papuguys.camera.graphqlapi.repository

import com.papuguys.camera.graphqlapi.model.Photo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PhotoRepository : MongoRepository<Photo, String> {
}