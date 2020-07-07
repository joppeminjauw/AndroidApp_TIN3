package com.example.calesports.database.entity

import com.google.gson.annotations.SerializedName

class Player(
    @SerializedName("first_name")
    val firstname: String?,
    @SerializedName("image_url")
    val imgSrc: String?,
    val id: Int?,
    @SerializedName("last_name")
    val lastname: String?,
    val name: String?,
    val role: String?
)