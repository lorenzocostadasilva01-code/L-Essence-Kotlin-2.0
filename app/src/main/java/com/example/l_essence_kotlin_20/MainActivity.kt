package com.example.l_essence_kotlin_20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.l_essence_kotlin_20.ui.theme.LEssenceKotlin20Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permite que o app ocupe a tela inteira (atrás da status bar e navigation bar)
        enableEdgeToEdge()

        setContent {
            LEssenceKotlin20Theme {
                // Ponto de entrada do seu App, que gerencia as rotas e a barra inferior
                TelaInicial()
            }
        }
    }
}