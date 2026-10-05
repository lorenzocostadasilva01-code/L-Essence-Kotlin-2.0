package com.example.l_essence_kotlin_20

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
@Composable
fun AbaHome(viewModel: MeuViewModel) {

    var contador by remember { mutableStateOf(0) }

    Column() {
        Text("Home - ${ viewModel.contador}")
        Button(onClick = { viewModel.add()}) {
            Text("ADD")
        }
    }
}
@Composable
fun AbaPerfil(viewModel: MeuViewModel) {
    Text("PERFIL")
}