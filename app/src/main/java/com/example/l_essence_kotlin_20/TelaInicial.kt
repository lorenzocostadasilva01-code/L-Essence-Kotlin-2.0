package com.example.l_essence_kotlin_20

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun TelaInicial() {
    val navController = rememberNavController()
    val viewModel: MeuViewModel = viewModel()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = backStackEntry?.destination?.route

    // O BottomNavigation deve aparecer nas telas principais
    val mostrarBottomBar = rotaAtual in listOf(
        Rotas.LandPage,
        Rotas.Cardapio,
        Rotas.CadastroProduto,
        Rotas.Carrinho,
        Rotas.Perfil
    )

    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars,
        bottomBar = {
            if (mostrarBottomBar) {
                BottomNavBarCustom(
                    navController = navController,
                    rotaAtual = rotaAtual,
                    quantidadeCarrinho = viewModel.quantidadeTotalItensCarrinho()
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Rotas.LandPage,
            modifier = Modifier.padding(paddingValues)
        ) {
            // 1. Início / Boas-Vindas
            composable(Rotas.LandPage) {
                LandPageScreen(
                    onNavigateToPratos = { navController.navigate(Rotas.Cardapio) },
                    onNavigateToVinhos = { navController.navigate(Rotas.Cardapio) },
                    onNavigateToDetalhePrato = { pratoId ->
                        navController.navigate(Rotas.criarRotaDetalhePrato(pratoId))
                    },
                    onNavigateToPerfil = { navController.navigate(Rotas.Perfil) }
                )
            }

            // 2. Cardápio Unificado (Pratos Gourmets + Carta de Vinhos)
            composable(Rotas.Cardapio) {
                CardapioScreen(
                    viewModel = viewModel,
                    onNavigateToDetalhePrato = { pratoId ->
                        navController.navigate(Rotas.criarRotaDetalhePrato(pratoId))
                    },
                    onNavigateToDetalheVinho = { vinhoId ->
                        navController.navigate(Rotas.criarRotaDetalheVinho(vinhoId))
                    },
                    onNavigateToCadastro = { navController.navigate(Rotas.CadastroProduto) },
                    onNavigateToPerfil = { navController.navigate(Rotas.Perfil) }
                )
            }

            // 3. Tela de Cadastro de Produto com Validação
            composable(Rotas.CadastroProduto) {
                CadastroProdutoScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onSuccessNavigateToCardapio = { navController.navigate(Rotas.Cardapio) },
                    onNavigateToPerfil = { navController.navigate(Rotas.Perfil) }
                )
            }

            // 4. Detalhes do Prato
            composable(
                route = Rotas.DetalhePrato,
                arguments = listOf(navArgument("pratoId") { type = NavType.IntType })
            ) { backStack ->
                val pratoId = backStack.arguments?.getInt("pratoId") ?: -1
                DetalhePratoScreen(
                    pratoId = pratoId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // 5. Detalhes do Vinho
            composable(
                route = Rotas.DetalheVinho,
                arguments = listOf(navArgument("vinhoId") { type = NavType.IntType })
            ) { backStack ->
                val vinhoId = backStack.arguments?.getInt("vinhoId") ?: -1
                DetalheVinhoScreen(
                    vinhoId = vinhoId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // 6. Carrinho Interativo
            composable(Rotas.Carrinho) {
                CarrinhoScreen(
                    viewModel = viewModel,
                    onNavigateToPayment = { total ->
                        navController.navigate(Rotas.criarRotaPagamento(total))
                    },
                    onNavigateToPerfil = { navController.navigate(Rotas.Perfil) }
                )
            }

            // 7. Pagamento
            composable(
                route = Rotas.Pagamento,
                arguments = listOf(navArgument("total") { type = NavType.StringType })
            ) { backStack ->
                val total = backStack.arguments?.getString("total") ?: "R$ 0,00"
                PagamentoScreen(
                    totalAmount = total,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onNavigateToEditarPagamento = { navController.navigate(Rotas.EditarPagamento) },
                    onNavigateToPerfil = { navController.navigate(Rotas.Perfil) },
                    onPaymentSuccess = {
                        viewModel.limparCarrinho() // Esvazia o carrinho ao concluir o pagamento!
                        navController.navigate(Rotas.LandPage) {
                            popUpTo(Rotas.LandPage) { inclusive = true }
                        }
                    }
                )
            }

            // 8. Tela de Gerenciar/Editar Formas de Pagamento
            composable(Rotas.EditarPagamento) {
                EditarPagamentoScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onNavigateToPerfil = { navController.navigate(Rotas.Perfil) }
                )
            }

            // 9. Perfil
            composable(Rotas.Perfil) {
                PerfilScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onNavigateToPratos = { navController.navigate(Rotas.Cardapio) },
                    onNavigateToVinhos = { navController.navigate(Rotas.Cardapio) }
                )
            }
        }
    }
}

@Composable
fun BottomNavBarCustom(
    navController: NavHostController,
    rotaAtual: String?,
    quantidadeCarrinho: Int
) {
    val bottomNavBg = Color(0xFF333333)
    val goldAccent = Color(0xFFD4AF37)

    Surface(
        color = bottomNavBg,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .height(64.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Início
            IconButton(
                onClick = { navController.navigate(Rotas.LandPage) },
                modifier = Modifier.background(
                    if (rotaAtual == Rotas.LandPage) goldAccent else goldAccent.copy(alpha = 0.5f),
                    CircleShape
                )
            ) {
                Icon(Icons.Default.Home, contentDescription = "Início", tint = Color.Black)
            }

            // 2. Cardápio
            IconButton(
                onClick = { navController.navigate(Rotas.Cardapio) },
                modifier = Modifier.background(
                    if (rotaAtual == Rotas.Cardapio) goldAccent else goldAccent.copy(alpha = 0.5f),
                    CircleShape
                )
            ) {
                Icon(Icons.Default.RestaurantMenu, contentDescription = "Cardápio", tint = Color.Black)
            }

            // 3. Cadastrar Produto
            IconButton(
                onClick = { navController.navigate(Rotas.CadastroProduto) },
                modifier = Modifier.background(
                    if (rotaAtual == Rotas.CadastroProduto) goldAccent else goldAccent.copy(alpha = 0.5f),
                    CircleShape
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = "Cadastrar", tint = Color.Black)
            }

            // 4. Carrinho Interativo
            Box {
                IconButton(
                    onClick = { navController.navigate(Rotas.Carrinho) },
                    modifier = Modifier.background(
                        if (rotaAtual == Rotas.Carrinho) goldAccent else goldAccent.copy(alpha = 0.5f),
                        CircleShape
                    )
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Carrinho", tint = Color.Black)
                }

                if (quantidadeCarrinho > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(18.dp)
                            .background(Color.Red, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$quantidadeCarrinho",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // 5. Perfil
            IconButton(
                onClick = { navController.navigate(Rotas.Perfil) },
                modifier = Modifier.background(
                    if (rotaAtual == Rotas.Perfil) goldAccent else goldAccent.copy(alpha = 0.5f),
                    CircleShape
                )
            ) {
                Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.Black)
            }
        }
    }
}
