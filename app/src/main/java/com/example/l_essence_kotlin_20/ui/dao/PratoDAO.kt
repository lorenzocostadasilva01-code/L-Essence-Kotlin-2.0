package com.example.l_essence_kotlin_20.ui.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.l_essence_kotlin_20.Prato
import kotlinx.coroutines.flow.Flow

@Dao
interface PratoDAO {

    // CardapioScreen / ListaPratosScreen (favoritos no topo)
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
