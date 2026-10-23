package com.example.l_essence_kotlin_20

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun observarPorId(uid: Int = 1): Flow<User?>

    @Query("SELECT * FROM user WHERE uid = :uid")
    suspend fun buscarPorId(uid: Int = 1): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvar(user: User)

    // Usado pelo DialogEditarPerfil -> viewModel.atualizarPerfil(...)
    @Query(
        """
        UPDATE user 
        SET nome = :nome, email = :email, endereco = :endereco, telefone = :telefone 
        WHERE uid = :uid
        """
    )
    suspend fun atualizarPerfil(
        nome: String,
        email: String,
        endereco: String,
        telefone: String,
        uid: Int = 1
    )

    @Query("SELECT COUNT(*) FROM user")
    suspend fun contar(): Int
}