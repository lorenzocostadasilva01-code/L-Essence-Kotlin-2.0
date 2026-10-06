package com.example.l_essence_kotlin_20

object Rotas {
    const val LandPage = "land_page"
    const val Cardapio = "cardapio"
    const val CadastroProduto = "cadastro_produto"
    const val Carrinho = "carrinho"
    const val Perfil = "perfil"
    const val DetalhePrato = "detalhe_prato/{pratoId}"
    const val DetalheVinho = "detalhe_vinho/{vinhoId}"
    const val Pagamento = "pagamento/{total}"
    const val EditarPagamento = "editar_pagamento"

    fun criarRotaDetalhePrato(id: Int) = "detalhe_prato/$id"
    fun criarRotaDetalheVinho(id: Int) = "detalhe_vinho/$id"
    fun criarRotaPagamento(total: String) = "pagamento/${java.net.URLEncoder.encode(total, "UTF-8")}"
}
