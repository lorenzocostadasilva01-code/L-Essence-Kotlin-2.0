package com.example.l_essence_kotlin_20

data class MetodoPagamento(
    val id: Int,
    val marca: String, // "VISA", "MASTERCARD", "PIX"
    val numeroMascarado: String,
    val nomeTitular: String,
    val validade: String,
    var isPadrao: Boolean = false
)
package com.example.l_essence_kotlin_20

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "metodo_pagamento")
data class MetodoPagamento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "marca") val marca: String, // "VISA", "MASTERCARD", "PIX"
    @ColumnInfo(name = "numero_mascarado") val numeroMascarado: String,
    @ColumnInfo(name = "nome_titular") val nomeTitular: String,
    @ColumnInfo(name = "validade") val validade: String,
    @ColumnInfo(name = "is_padrao") var isPadrao: Boolean = false
)