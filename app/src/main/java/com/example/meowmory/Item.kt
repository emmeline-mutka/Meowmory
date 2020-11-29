package com.example.roomintro

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "time") var time: Long
)
