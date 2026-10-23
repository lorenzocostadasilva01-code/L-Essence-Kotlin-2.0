package com.example.l_essence_kotlin_20

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardapioScreen(
    viewModel: MeuViewModel,
    onNavigateToDetalhePrato: (Int) -> Unit,
    onNavigateToDetalheVinho: (Int) -> Unit,
    onNavigateToCadastro: () -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    val backgroundColor = Color(0xFFFFFFE4)
    val cardBackgroundColor = Color(0xFFECECE3)
    val badgeBackgroundColor = Color(0xFFE2E2D6)
    val goldAccent = Color(0xFFD4AF37)

    var abaSelecionada by remember { mutableStateOf(0) } // 0 = Pratos Gourmets, 1 = Vinhos Finos

    // Ordenação dinâmica: Favoritos ficam no TOPO
    val pratosOrdenados = viewModel.pratos.sortedByDescending { it.isFavorito }
    val vinhosOrdenados = viewModel.vinhos.sortedWith(
        compareByDescending<Vinho> { it.isFavorito }.thenByDescending { it.isReservado }
    )

    Scaffold(
        containerColor = backgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCadastro,
                containerColor = goldAccent,
                contentColor = Color.Black,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Cadastrar Novo Item")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(padding)
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .border(1.dp, goldAccent.copy(alpha = 0.6f), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "♢", color = goldAccent, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Cardápio L'Essence",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                }

                IconButton(
                    onClick = onNavigateToPerfil,
                    modifier = Modifier
                        .size(40.dp)
                        .background(goldAccent, CircleShape)
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tab Selector: Pratos vs Vinhos
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(cardBackgroundColor)
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (abaSelecionada == 0) goldAccent else Color.Transparent)
                        .clickable { abaSelecionada = 0 },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.RestaurantMenu,
                            contentDescription = null,
                            tint = if (abaSelecionada == 0) Color.Black else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Pratos Gourmets",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (abaSelecionada == 0) Color.Black else Color.DarkGray
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (abaSelecionada == 1) goldAccent else Color.Transparent)
                        .clickable { abaSelecionada = 1 },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.WineBar,
                            contentDescription = null,
                            tint = if (abaSelecionada == 1) Color.Black else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Carta de Vinhos",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (abaSelecionada == 1) Color.Black else Color.DarkGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (abaSelecionada == 0) {
                // Conteúdo da Aba 0: Pratos (Ordenados com Favoritos no Topo)
                if (pratosOrdenados.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "Nenhum prato no menu.\nClique em + para cadastrar!", color = Color.Gray, fontSize = 15.sp)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(pratosOrdenados, key = { it.id }) { prato ->
                            PratoCardItem(
                                prato = prato,
                                cardBg = cardBackgroundColor,
                                badgeBg = badgeBackgroundColor,
                                goldAccent = goldAccent,
                                onClick = { onNavigateToDetalhePrato(prato.id) },
                                onToggleFavorite = { viewModel.toggleFavoritoPrato(prato.id) },
                                onDelete = { viewModel.removerPrato(prato.id) },
                                onAddToCart = { viewModel.adicionarPratoAoCarrinho(prato) }
                            )
                        }
                    }
                }
            } else {
                // Conteúdo da Aba 1: Vinhos (Ordenados com Favoritos no Topo)
                if (vinhosOrdenados.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "Nenhum vinho na carta.\nClique em + para cadastrar!", color = Color.Gray, fontSize = 15.sp)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(vinhosOrdenados, key = { it.id }) { vinho ->
                            VinhoCardItem(
                                vinho = vinho,
                                cardBg = cardBackgroundColor,
                                badgeBg = badgeBackgroundColor,
                                goldAccent = goldAccent,
                                onClick = { onNavigateToDetalheVinho(vinho.id) },
                                onToggleReservado = { viewModel.toggleFavoritoVinho(vinho.id) },
                                onDelete = { viewModel.removerVinho(vinho.id) },
                                onAddToCart = { viewModel.adicionarVinhoAoCarrinho(vinho) }
                            )
                        }
                    }
                }
            }
        }
    }
}
