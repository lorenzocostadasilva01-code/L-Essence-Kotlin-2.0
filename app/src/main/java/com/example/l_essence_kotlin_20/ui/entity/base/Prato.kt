package com.example.l_essence_kotlin_20

data class Prato(
    val id: Int,
    val nome: String,
    val chef: String = "Chef Jean-Luc",
    val categoria: String, // ex: "Pratos Principais", "Entradas", "Sobremesas"
    val precoBase: Double,
    val ingredientes: String,
    val harmonizacaoVinho: String,
    val tempoPreparoMinutos: Int = 25,
    val avaliacao: Double = 4.9,
    val descricao: String,
    val disponivel: Boolean = true,
    var isFavorito: Boolean = false
)
