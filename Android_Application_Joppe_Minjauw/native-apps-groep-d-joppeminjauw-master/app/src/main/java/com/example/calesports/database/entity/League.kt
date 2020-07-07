package com.example.calesports.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "league_table")
data class League(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("image_url")
    val imgSrc: String?,
    val name: String?
)