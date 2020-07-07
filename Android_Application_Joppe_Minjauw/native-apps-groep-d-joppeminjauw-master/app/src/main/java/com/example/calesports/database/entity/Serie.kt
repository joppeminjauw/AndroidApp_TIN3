package com.example.calesports.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "serie_table")
data class Serie(
    val description: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String?,
    val year: Int?
)