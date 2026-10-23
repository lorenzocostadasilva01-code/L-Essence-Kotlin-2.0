package com.example.l_essence_kotlin_20

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MetodoPagamentoDao {

    // PagamentoScreen / EditarPagamentoScreen (padrão no topo)
    @Query("SELECT * FROM metodo_pagamento ORDER BY is_padrao DESC, id ASC")
    abstract fun observarTodos(): Flow<List<MetodoPagamento>>

    @Query("SELECT * FROM metodo_pagamento WHERE id = :id")
    abstract suspend fun buscarPorId(id: Int): MetodoPagamento?

    @Query("SELECT * FROM metodo_pagamento WHERE is_padrao = 1 LIMIT 1")
    abstract suspend fun buscarPadrao(): MetodoPagamento?

    // DialogFormularioCartao (novo cartão) -> adicionarMetodoPagamento(...)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun inserir(metodo: MetodoPagamento): Long

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
    abstract suspend fun editar(
        id: Int,
        marca: String,
        numeroMascarado: String,
        nomeTitular: String,
        validade: String
    )

    // Ícone de lixeira -> removerMetodoPagamento(id)
    @Query("DELETE FROM metodo_pagamento WHERE id = :id")
    abstract suspend fun removerPorId(id: Int)

    @Query("UPDATE metodo_pagamento SET is_padrao = 0")
    abstract suspend fun limparPadrao()

    @Query("UPDATE metodo_pagamento SET is_padrao = 1 WHERE id = :id")
    abstract suspend fun marcarComoPadrao(id: Int)

    // PagamentoScreen ao clicar num cartão -> selecionarMetodoPadrao(id)
    @Transaction
    open suspend fun selecionarPadrao(id: Int) {
        limparPadrao()
        marcarComoPadrao(id)
    }

    @Query("SELECT COUNT(*) FROM metodo_pagamento")
    abstract suspend fun contar(): Int
}