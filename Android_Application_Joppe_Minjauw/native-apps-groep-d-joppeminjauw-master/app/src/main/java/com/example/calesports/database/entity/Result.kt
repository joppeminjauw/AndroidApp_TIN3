package com.example.calesports.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "result_table")
data class Result(
    val score: Int?,
    @SerializedName("team_id")
    val teamId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Long?
)