package com.example.calesports.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tournament_table")
data class Tournament(
    @SerializedName("begin_at")
    val beginAt: String?,
    @SerializedName("end_at")
    val endAt: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String?,
    val slug: String?
)