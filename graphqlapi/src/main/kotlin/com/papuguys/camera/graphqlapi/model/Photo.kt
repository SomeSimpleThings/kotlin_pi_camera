package com.papuguys.camera.graphqlapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "photo")
data class Photo(var name: String, var path: String?) {

    @Id
    var id: String = ""
    var timestamp: Long = 0
}