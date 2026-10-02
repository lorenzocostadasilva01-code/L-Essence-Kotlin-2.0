package com.example.l_essence_kotlin_20

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.l_essence_kotlin_20.RotasAbas.AbaHome
import com.example.l_essence_kotlin_20.RotasAbas.AbaPerfil
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TelaInicial() {
    val navInterno = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "L'Essence",
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navInterno.navigate(RotasAbas.AbaHome) {
                            popUpTo(RotasAbas.AbaHome) { inclusive = true }
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Perfil") },
                    selected = false,
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navInterno.navigate(RotasAbas.AbaPerfil) {
                            popUpTo(RotasAbas.AbaHome)
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("L'Essence") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menu lateral")
                        }
                    }
                )
            },
            bottomBar = {
                BottomBar(navInterno = navInterno)
            }
        ) { innerPadding ->
            NavHost(
                navController = navInterno,
                startDestination = RotasAbas.AbaHome,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(RotasAbas.AbaHome) {}
                composable(RotasAbas.AbaPerfil) {}
            }
        }
    }
}

@Composable
fun BottomBar(navInterno: NavHostController) {
    val backStackEntry by navInterno.currentBackStackEntryAsState()

}