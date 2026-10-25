package com.example.l_essence_kotlin_20.ui.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.l_essence_kotlin_20.MetodoPagamento
import kotlinx.coroutines.flow.Flow

@Dao
interface MetodoPagamentoDAO {

    // PagamentoScreen / EditarPagamentoScreen (padrão no topo)
    @Query("SELECT * FROM metodo_pagamento ORDER BY is_padrao DESC, id ASC")
    fun observarTodos(): Flow<List<MetodoPagamento>>

    @Query("SELECT * FROM metodo_pagamento WHERE id = :id")
    suspend fun buscarPorId(id: Int): MetodoPagamento?

    @Query("SELECT * FROM metodo_pagamento WHERE is_padrao = 1 LIMIT 1")
    suspend fun buscarPadrao(): MetodoPagamento?

    // DialogFormularioCartao (novo cartão) -> adicionarMetodoPagamento(...)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(metodo: MetodoPagamento): Long

    // DialogFormularioCartao (edição) -> editarMetodoPagamento(...)
    @Query(
        """
        UPDATE metodo_pagamento 
        SET marca = :marca, 
            numero_mascarado = :numeroMascarado, 
            nome_titular = :nomeTitular, 
            validade = :validade 
        WHERE id = :id
        """
    )
    suspend fun editar(
        id: Int,
        marca: String,
        numeroMascarado: String,
        nomeTitular: String,
        validade: String
    )

    // Ícone de lixeira -> removerMetodoPagamento(id)
    @Query("DELETE FROM metodo_pagamento WHERE id = :id")
    suspend fun removerPorId(id: Int)

    @Query("UPDATE metodo_pagamento SET is_padrao = 0")
    suspend fun limparPadrao()

    @Query("UPDATE metodo_pagamento SET is_padrao = 1 WHERE id = :id")
    suspend fun marcarComoPadrao(id: Int)

    // PagamentoScreen ao clicar num cartão -> selecionarMetodoPadrao(id)
    @Transaction
    suspend fun selecionarPadrao(id: Int) {
        limparPadrao()
        marcarComoPadrao(id)
    }

    @Query("SELECT COUNT(*) FROM metodo_pagamento")
    suspend fun contar(): Int
}
