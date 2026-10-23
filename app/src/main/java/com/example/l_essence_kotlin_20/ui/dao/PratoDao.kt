package com.example.l_essence_kotlin_20

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PratoDao {

    // CardapioScreen / ListaPratosScreen (favoritos no topo, igual ao sortedByDescending atual)
    @Query("SELECT * FROM prato ORDER BY is_favorito DESC, id ASC")
    fun observarTodos(): Flow<List<Prato>>

    // DetalhePratoScreen
    @Query("SELECT * FROM prato WHERE id = :id")
    fun observarPorId(id: Int): Flow<Prato?>

    @Query("SELECT * FROM prato WHERE id = :id")
    suspend fun buscarPorId(id: Int): Prato?

    // CadastroProdutoScreen / DialogNovoPrato
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(prato: Prato): Long

    // Carga inicial (os dados que antes ficavam em memória)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirTodos(pratos: List<Prato>)

    // Botão de coração
    @Query("UPDATE prato SET is_favorito = NOT is_favorito WHERE id = :id")
    suspend fun alternarFavorito(id: Int)

    // Botão de lixeira
    @Query("DELETE FROM prato WHERE id = :id")
    suspend fun removerPorId(id: Int)

    @Query("SELECT COUNT(*) FROM prato")
    suspend fun contar(): Int
}