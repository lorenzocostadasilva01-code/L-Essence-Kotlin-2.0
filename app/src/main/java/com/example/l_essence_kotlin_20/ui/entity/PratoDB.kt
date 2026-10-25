package com.example.l_essence_kotlin_20

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prato")
data class Prato(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "chef") val chef: String = "Chef Jean-Luc",
    @ColumnInfo(name = "categoria") val categoria: String,
    @ColumnInfo(name = "preco_base") val precoBase: Double,
    @ColumnInfo(name = "ingredientes") val ingredientes: String,
    @ColumnInfo(name = "harmonizacao_vinho") val harmonizacaoVinho: String,
    @ColumnInfo(name = "tempo_preparo_minutos") val tempoPreparoMinutos: Int = 25,
    @ColumnInfo(name = "avaliacao") val avaliacao: Double = 4.9,
    @ColumnInfo(name = "descricao") val descricao: String,
    @ColumnInfo(name = "disponivel") val disponivel: Boolean = true,
    @ColumnInfo(name = "is_favorito") var isFavorito: Boolean = false
)
