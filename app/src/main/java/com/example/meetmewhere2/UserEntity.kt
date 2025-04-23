package com.example.meetmewhere2

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var userName: String,
    var password: String

)
