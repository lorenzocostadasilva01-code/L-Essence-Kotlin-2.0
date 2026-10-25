package com.example.l_essence_kotlin_20

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_item",
    indices = [Index(value = ["produto_id", "tipo_produto"], unique = true)]
)
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "produto_id") val produtoId: Int,
    @ColumnInfo(name = "tipo_produto") val tipoProduto: String,
    @ColumnInfo(name = "titulo") val titulo: String,
    @ColumnInfo(name = "categoria_ou_tipo") val categoriaOuTipo: String,
    @ColumnInfo(name = "quantidade") var quantidade: Int,
    @ColumnInfo(name = "preco_unitario") val precoUnitario: Double
) {
    @get:Ignore
    val precoTotal: Double
        get() = precoUnitario * quantidade
}
