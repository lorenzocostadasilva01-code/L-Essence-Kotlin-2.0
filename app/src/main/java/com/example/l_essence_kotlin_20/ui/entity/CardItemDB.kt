package com.example.l_essence_kotlin_20

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Ignore
import androidx.room3.PrimaryKey

@Entity(tableName = "cart_item")
data class CartItem(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "titulo") val titulo: String,
    @ColumnInfo(name = "categoria_ou_tipo") val categoriaOuTipo: String,
    @ColumnInfo(name = "quantidade") var quantidade: Int,
    @ColumnInfo(name = "preco_unitario") val precoUnitario: Double
) {
    @get:Ignore
    val precoTotal: Double
        get() = precoUnitario * quantidade
}