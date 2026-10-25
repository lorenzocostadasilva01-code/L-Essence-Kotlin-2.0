package com.example.l_essence_kotlin_20

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // Observa o usuário em tempo real (ideal para atualizar a UI automaticamente)
    @Query("SELECT * FROM user WHERE uid = 1")
    fun observarUsuario(): Flow<User?>

    // Busca o usuário uma única vez (ideal para checagens e lógicas de ViewModel)
    @Query("SELECT * FROM user WHERE uid = 1")
    suspend fun buscarUsuario(): User?

    // Insere ou atualiza o usuário.
    // Como o uid é sempre 1 por padrão, o REPLACE fará o papel de "Update"
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvar(user: User)

    // Atualiza um campo específico, caso você queira alterar apenas o endereço, por exemplo
    @Query("UPDATE user SET endereco = :novoEndereco WHERE uid = 1")
    suspend fun atualizarEndereco(novoEndereco: String)

    // Remove o usuário do banco de dados (útil para funcionalidade de "Sair" ou "Logout")
    @Query("DELETE FROM user")
    suspend fun limparUsuario()
}