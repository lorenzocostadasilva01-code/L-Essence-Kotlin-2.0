package com.example.l_essence_kotlin_20

data class Vinho(
    val id: Int,
    val rotulo: String,
    val vinicola: String,
    val safra: Int,
    val paisOrigem: String,
    val tipo: String, // ex: "Tinto", "Branco", "Espumante"
    val precoGarrafa: Double,
    val teorAlcoolico: String = "13.5%",
    val notasDegustacao: String,
    val avaliacao: Double = 4.8,
    val descricao: String,
    var isReservado: Boolean = false,
    var isFavorito: Boolean = false
)
