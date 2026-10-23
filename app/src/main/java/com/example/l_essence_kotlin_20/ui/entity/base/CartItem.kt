package com.example.l_essence_kotlin_20

data class CartItem(
    val id: Int,
    val titulo: String,
    val categoriaOuTipo: String,
    var quantidade: Int,
    val precoUnitario: Double
) {
    val precoTotal: Double
        get() = precoUnitario * quantidade
}
