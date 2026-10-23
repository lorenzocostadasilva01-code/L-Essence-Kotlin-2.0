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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
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
import java.util.Locale

@Composable
fun ListaVinhosScreen(
    viewModel: MeuViewModel,
    onNavigateToDetalheVinho: (Int) -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    val backgroundColor = Color(0xFFFFFFE4)
    val cardBackgroundColor = Color(0xFFECECE3)
    val badgeBackgroundColor = Color(0xFFE2E2D6)
    val goldAccent = Color(0xFFD4AF37)

    var showAddDialog by remember { mutableStateOf(false) }

    // Ordenação dinâmica: Favoritos/Reservados no TOPO da lista
    val vinhosOrdenados = viewModel.vinhos.sortedWith(
        compareByDescending<Vinho> { it.isFavorito }.thenByDescending { it.isReservado }
    )

    Scaffold(
        containerColor = backgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = goldAccent,
                contentColor = Color.Black,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Vinho")
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
                        text = "Carta de Vinhos",
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

            if (vinhosOrdenados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum vinho na carta.\nClique em + para adicionar!",
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
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

    if (showAddDialog) {
        DialogNovoVinho(
            onDismiss = { showAddDialog = false },
            onConfirm = { rotulo, vinicola, safra, pais, tipo, preco, notas, desc ->
                viewModel.adicionarVinho(
                    rotulo = rotulo,
                    vinicola = vinicola,
                    safra = safra,
                    paisOrigem = pais,
                    tipo = tipo,
                    precoGarrafa = preco,
                    notasDegustacao = notas,
                    descricao = desc
                )
                showAddDialog = false
            }
        )
    }
}

@Composable
fun VinhoCardItem(
    vinho: Vinho,
    cardBg: Color,
    badgeBg: Color,
    goldAccent: Color,
    onClick: () -> Unit,
    onToggleReservado: () -> Unit,
    onDelete: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(badgeBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.WineBar,
                        contentDescription = null,
                        tint = goldAccent,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${vinho.vinicola.uppercase()} • ${vinho.safra}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = goldAccent,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = vinho.rotulo,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = "${vinho.tipo} • ${vinho.paisOrigem}",
                        fontSize = 12.sp,
                        color = Color(0xFF5A5A5A)
                    )
                }

                Row {
                    IconButton(onClick = onToggleReservado) {
                        Icon(
                            imageVector = if (vinho.isFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (vinho.isFavorito) Color.Red else Color.Gray
                        )
                    }

                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remover Vinho",
                            tint = Color.DarkGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(badgeBg)
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = String.format(Locale("pt", "BR"), "R$ %.2f garrafa", vinho.precoGarrafa),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                }

                Button(
                    onClick = onAddToCart,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (vinho.isReservado) Color(0xFF2E7D32) else Color.Black
                    )
                ) {
                    if (vinho.isReservado) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Reservado", fontSize = 12.sp, color = Color.White)
                    } else {
                        Text(text = "Pedir Vinho", fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun DialogNovoVinho(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int, String, String, Double, String, String) -> Unit
) {
    var rotulo by remember { mutableStateOf("") }
    var vinicola by remember { mutableStateOf("Adega L'Essence") }
    var safraText by remember { mutableStateOf("2020") }
    var pais by remember { mutableStateOf("França") }
    var tipo by remember { mutableStateOf("Tinto Fino") }
    var precoText by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Vinho à Carta") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = rotulo,
                    onValueChange = { rotulo = it },
                    label = { Text("Rótulo / Nome do Vinho") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = vinicola,
                    onValueChange = { vinicola = it },
                    label = { Text("Vinícola") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = safraText,
                    onValueChange = { safraText = it },
                    label = { Text("Safra (Ano)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pais,
                    onValueChange = { pais = it },
                    label = { Text("País de Origem") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = precoText,
                    onValueChange = { precoText = it },
                    label = { Text("Preço da Garrafa (R$)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val safra = safraText.toIntOrNull() ?: 2020
                    val preco = precoText.replace(",", ".").toDoubleOrNull() ?: 100.0
                    if (rotulo.isNotBlank()) {
                        onConfirm(rotulo, vinicola, safra, pais, tipo, preco, notas, descricao)
                    }
                }
            ) {
                Text("Adicionar à Carta")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
