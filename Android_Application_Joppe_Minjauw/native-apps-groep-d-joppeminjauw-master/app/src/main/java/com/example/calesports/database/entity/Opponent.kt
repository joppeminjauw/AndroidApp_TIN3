package com.example.calesports.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "opponent_table")
data class Opponent(
    val acronym: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("image_url")
    val imgSrc: String?,
    val name: String?,
    @SerializedName("opponent")
    val opponent: Team?
)