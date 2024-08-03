package com.alexeyp.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: Long?,
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("status")
    val status: String,
    val creationDate: Long? = null
)

/*  "id": 7270216,
        "name": "Vrund Pilla",
        "email": "pilla_vrund@marks.example",
        "gender": "male",
        "status": "inactive"*/