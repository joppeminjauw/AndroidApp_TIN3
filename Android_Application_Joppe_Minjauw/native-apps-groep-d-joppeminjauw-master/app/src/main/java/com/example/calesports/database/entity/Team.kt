package com.example.calesports.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "team_table")
@TypeConverters(Converters::class)
data class Team(
    val acronym: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("image_url")
    val imgSrc: String?,
    val name: String?,
    @SerializedName("players")
    val players: List<Player>?
)