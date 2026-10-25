package com.example.l_essence_kotlin_20

import java.net.URLEncoder

object Rotas {
    const val LandPage = "land_page"
    const val Cardapio = "cardapio"
    const val CadastroProduto = "cadastro_produto"
    const val Carrinho = "carrinho"
    const val Perfil = "perfil"

    // Rotas com parâmetros
    const val DetalhePrato = "detalhe_prato/{pratoId}"
    const val DetalheVinho = "detalhe_vinho/{vinhoId}"
    const val Pagamento = "pagamento/{total}"
    const val EditarPagamento = "editar_pagamento"

    // Funções auxiliares para gerar as rotas com os argumentos preenchidos
    fun criarRotaDetalhePrato(id: Int) = "detalhe_prato/$id"
    fun criarRotaDetalheVinho(id: Int) = "detalhe_vinho/$id"

    // O URLEncoder previne crashs ao passar strings com espaços ou símbolos (ex: "R$ 150,00")
    fun criarRotaPagamento(total: String): String {
        val totalCodificado = URLEncoder.encode(total, "UTF-8")
        return "pagamento/$totalCodificado"
    }
}