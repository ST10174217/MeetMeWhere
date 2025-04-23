package com.example.meetmewhere2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserDAO {
    @Insert
    fun insert(userEntity: UserEntity)//No return value

    @Query("SELECT * FROM Users")
    fun getAllUsers(): List<UserEntity>//Returns all users in a list
}