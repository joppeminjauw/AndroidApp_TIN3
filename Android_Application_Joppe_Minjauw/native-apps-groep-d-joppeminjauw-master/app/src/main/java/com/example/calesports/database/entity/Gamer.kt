package com.example.calesports.database.entity

import com.google.gson.annotations.SerializedName

class Gamer(
    private val name: Int,
    @SerializedName("image_url")
    private val imgSrc: String?
)