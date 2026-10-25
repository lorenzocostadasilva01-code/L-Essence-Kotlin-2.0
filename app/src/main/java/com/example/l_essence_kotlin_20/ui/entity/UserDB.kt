package com.example.l_essence_kotlin_20

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val uid: Int = 1,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "telefone") val telefone: String,
    @ColumnInfo(name = "endereco") val endereco: String
)
