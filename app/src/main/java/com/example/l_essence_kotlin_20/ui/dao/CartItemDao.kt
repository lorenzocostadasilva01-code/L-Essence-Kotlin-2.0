package com.example.l_essence_kotlin_20

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CartItemDao {

    // CarrinhoScreen (lista)
    @Query("SELECT * FROM cart_item ORDER BY id ASC")
    abstract fun observarTodos(): Flow<List<CartItem>>

    // Badge do BottomNavBar -> quantidadeTotalItensCarrinho()
    @Query("SELECT COALESCE(SUM(quantidade), 0) FROM cart_item")
    abstract fun observarQuantidadeTotal(): Flow<Int>

    // CarrinhoScreen -> calcularTotalCarrinho()
    @Query("SELECT COALESCE(SUM(preco_unitario * quantidade), 0.0) FROM cart_item")
    abstract fun observarTotal(): Flow<Double>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun inserir(item: CartItem): Long

    @Query(
        """
        UPDATE cart_item 
        SET quantidade = quantidade + :quantidade 
        WHERE produto_id = :produtoId AND tipo_produto = :tipoProduto
        """
    )
    abstract suspend fun incrementarQuantidade(
        produtoId: Int,
        tipoProduto: String,
        quantidade: Int
    ): Int

    // adicionarPratoAoCarrinho(prato, qtd) / adicionarVinhoAoCarrinho(vinho, qtd)
    @Transaction
    open suspend fun adicionarOuIncrementar(item: CartItem) {
        val linhasAtualizadas = incrementarQuantidade(
            produtoId = item.produtoId,
            tipoProduto = item.tipoProduto,
            quantidade = item.quantidade
        )
        if (linhasAtualizadas == 0) {
            inserir(item)
        }
    }

    @Query("UPDATE cart_item SET quantidade = quantidade + :delta WHERE id = :id")
    abstract suspend fun somarQuantidade(id: Int, delta: Int)

    @Query("DELETE FROM cart_item WHERE id = :id AND quantidade <= 0")
    abstract suspend fun removerSeZerado(id: Int)

    // Botões + e - do CarrinhoScreen -> alterarQuantidadeCarrinho(id, 1 ou -1)
    // Se a quantidade chegar a 0, o item é removido.
    @Transaction
    open suspend fun alterarQuantidade(id: Int, delta: Int) {
        somarQuantidade(id, delta)
        removerSeZerado(id)
    }

    // Ícone de lixeira -> removerDoCarrinho(id)
    @Query("DELETE FROM cart_item WHERE id = :id")
    abstract suspend fun removerPorId(id: Int)

    // Quando um prato/vinho é excluído do cardápio, remove o item correspondente do carrinho
    @Query("DELETE FROM cart_item WHERE produto_id = :produtoId AND tipo_produto = :tipoProduto")
    abstract suspend fun removerPorProduto(produtoId: Int, tipoProduto: String)

    // "Esvaziar" e onPaymentSuccess -> limparCarrinho()
    @Query("DELETE FROM cart_item")
    abstract suspend fun limpar()
}