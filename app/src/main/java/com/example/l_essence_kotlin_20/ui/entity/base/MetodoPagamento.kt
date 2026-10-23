package com.example.l_essence_kotlin_20

data class MetodoPagamento(
    val id: Int,
    val marca: String, // "VISA", "MASTERCARD", "PIX"
    val numeroMascarado: String,
    val nomeTitular: String,
    val validade: String,
    var isPadrao: Boolean = false
)
