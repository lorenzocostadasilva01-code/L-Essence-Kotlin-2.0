package com.example.l_essence_kotlin_20

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "vinho")
data class Vinho(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "rotulo") val rotulo: String,
    @ColumnInfo(name = "vinicola") val vinicola: String,
    @ColumnInfo(name = "safra") val safra: Int,
    @ColumnInfo(name = "pais_origem") val paisOrigem: String,
    @ColumnInfo(name = "tipo") val tipo: String, // ex: "Tinto", "Branco", "Espumante"
    @ColumnInfo(name = "preco_garrafa") val precoGarrafa: Double,
    @ColumnInfo(name = "teor_alcoolico") val teorAlcoolico: String = "13.5%",
    @ColumnInfo(name = "notas_degustacao") val notasDegustacao: String,
    @ColumnInfo(name = "avaliacao") val avaliacao: Double = 4.8,
    @ColumnInfo(name = "descricao") val descricao: String,
    @ColumnInfo(name = "is_reservado") var isReservado: Boolean = false,
    @ColumnInfo(name = "is_favorito") var isFavorito: Boolean = false
)