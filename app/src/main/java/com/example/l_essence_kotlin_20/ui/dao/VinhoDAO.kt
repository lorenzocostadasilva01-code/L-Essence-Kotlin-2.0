package com.example.l_essence_kotlin_20.ui.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.l_essence_kotlin_20.Vinho
import kotlinx.coroutines.flow.Flow

@Dao
interface VinhoDAO {

    // CardapioScreen / ListaVinhosScreen (favoritos e depois reservados no topo)
    @Query("SELECT * FROM vinho ORDER BY is_favorito DESC, is_reservado DESC, id ASC")
    fun observarTodos(): Flow<List<Vinho>>

    // DetalheVinhoScreen
    @Query("SELECT * FROM vinho WHERE id = :id")
    fun observarPorId(id: Int): Flow<Vinho?>

    @Query("SELECT * FROM vinho WHERE id = :id")
    suspend fun buscarPorId(id: Int): Vinho?

    // CadastroProdutoScreen / DialogNovoVinho
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(vinho: Vinho): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirTodos(vinhos: List<Vinho>)

    // Botão de coração
    @Query("UPDATE vinho SET is_favorito = NOT is_favorito WHERE id = :id")
    suspend fun alternarFavorito(id: Int)

    // DetalheVinhoScreen -> "Reservar Garrafa na Adega"
    @Query("UPDATE vinho SET is_reservado = NOT is_reservado WHERE id = :id")
    suspend fun alternarReservado(id: Int)

    // Para marcar como reservado ao pedir o vinho (botão "Pedir Vinho")
    @Query("UPDATE vinho SET is_reservado = :reservado WHERE id = :id")
    suspend fun definirReservado(id: Int, reservado: Boolean)

    @Query("DELETE FROM vinho WHERE id = :id")
    suspend fun removerPorId(id: Int)

    @Query("SELECT COUNT(*) FROM vinho")
    suspend fun contar(): Int
}
