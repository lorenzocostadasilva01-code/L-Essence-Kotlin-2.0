package com.example.l_essence_kotlin_20.ui.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.l_essence_kotlin_20.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {

    // 1. Observar todos os itens do carrinho (CarrinhoScreen)
    @Query("SELECT * FROM cart_item ORDER BY id ASC")
    fun observarTodos(): Flow<List<CartItem>>

    // 2. Observar a quantidade total de itens (Badge do BottomNavBar)
    @Query("SELECT COALESCE(SUM(quantidade), 0) FROM cart_item")
    fun observarQuantidadeTotal(): Flow<Int>

    // 3. Observar o valor total do carrinho
    @Query("SELECT COALESCE(SUM(preco_unitario * quantidade), 0.0) FROM cart_item")
    fun observarTotal(): Flow<Double>

    // 4. Inserir um novo item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(item: CartItem): Long

    // 5. Atualizar a quantidade de um item existente (usado ao adicionar mais de algo que já está no carrinho)
    @Query(
        """
        UPDATE cart_item 
        SET quantidade = quantidade + :quantidadeAdicional 
        WHERE produto_id = :produtoId AND tipo_produto = :tipoProduto
        """
    )
    suspend fun incrementarQuantidadeProduto(
        produtoId: Int,
        tipoProduto: String,
        quantidadeAdicional: Int
    ): Int

    @Transaction
    suspend fun adicionarOuIncrementar(item: CartItem) {
        val rowsUpdated = incrementarQuantidadeProduto(
            produtoId = item.produtoId,
            tipoProduto = item.tipoProduto,
            quantidadeAdicional = item.quantidade
        )
        if (rowsUpdated == 0) {
            inserir(item)
        }
    }

    // 6. Alterar a quantidade por ID (Botões + e - no carrinho)
    @Query("UPDATE cart_item SET quantidade = quantidade + :delta WHERE id = :id")
    suspend fun alterarQuantidadePorId(id: Int, delta: Int)

    // 7. Remover item se a quantidade for menor ou igual a zero
    @Query("DELETE FROM cart_item WHERE id = :id AND quantidade <= 0")
    suspend fun removerSeZerado(id: Int)

    // 8. Remover um item específico pelo ID (Ícone de lixeira)
    @Query("DELETE FROM cart_item WHERE id = :id")
    suspend fun removerPorId(id: Int)

    // 9. Remover item pelo Produto (caso o produto seja excluído do app)
    @Query("DELETE FROM cart_item WHERE produto_id = :produtoId AND tipo_produto = :tipoProduto")
    suspend fun removerPorProduto(produtoId: Int, tipoProduto: String)

    // 10. Limpar todo o carrinho (Esvaziar ou após pagamento)
    @Query("DELETE FROM cart_item")
    suspend fun limpar()
}
