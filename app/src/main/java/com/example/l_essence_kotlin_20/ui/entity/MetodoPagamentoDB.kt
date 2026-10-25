package com.example.l_essence_kotlin_20

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "metodo_pagamento")
data class MetodoPagamento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "marca") val marca: String,
    @ColumnInfo(name = "numero_mascarado") val numeroMascarado: String,
    @ColumnInfo(name = "nome_titular") val nomeTitular: String,
    @ColumnInfo(name = "validade") val validade: String,
    @ColumnInfo(name = "is_padrao") var isPadrao: Boolean = false
)
