package com.example.roomintro

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDao {

    @Insert
    fun insert(item: Item)

    @Delete
    fun delete(item: Item)

    @Query("SELECT * FROM item")
    fun getAll() : List<Item>

}