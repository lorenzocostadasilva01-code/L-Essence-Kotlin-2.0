package com.example.l_essence_kotlin_20

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VinhoDao {

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

    // Botão de coração (na lista, o parâmetro se chama onToggleReservado, mas chama toggleFavoritoVinho)
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