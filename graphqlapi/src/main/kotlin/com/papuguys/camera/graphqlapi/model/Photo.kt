package com.papuguys.camera.graphqlapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "photo")
data class Photo(val name: String) {

    @Id
    val id: String = ""
}