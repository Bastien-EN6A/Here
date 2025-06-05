package com.ironmind.here.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profs")
data class Prof(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "nom") val name: String?,
    @ColumnInfo(name = "prenom") val prenom: String?
)
