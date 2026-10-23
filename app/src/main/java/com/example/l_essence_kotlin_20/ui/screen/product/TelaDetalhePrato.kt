package com.example.l_essence_kotlin_20

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Star
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
fun DetalhePratoScreen(
    pratoId: Int,
    viewModel: MeuViewModel,
    onBack: () -> Unit
) {
    val prato = viewModel.obterPratoPorId(pratoId)

    val backgroundColor = Color(0xFFFFFFE4)
    val cardBackgroundColor = Color(0xFFECECE3)
    val badgeBackgroundColor = Color(0xFFE2E2D6)
    val goldAccent = Color(0xFFD4AF37)
    val textColor = Color(0xFF5A5A5A)

    var quantidade by remember { mutableStateOf(1) }
    var mensagemFeedback by remember { mutableStateOf<String?>(null) }

    if (prato == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Prato não encontrado.", color = Color.Red, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onBack) {
                    Text(text = "Voltar")
                }
            }
        }
        return
    }

    val precoCalculado = prato.precoBase * quantidade
    val precoFormatado = String.format(Locale("pt", "BR"), "R$ %.2f", precoCalculado)

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botão de Voltar Funcional
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(42.dp)
                        .background(cardBackgroundColor, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color(0xFF333333)
                    )
                }

                Text(
                    text = "Detalhes do Prato",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                IconButton(
                    onClick = { viewModel.toggleFavoritoPrato(prato.id) },
                    modifier = Modifier
                        .size(42.dp)
                        .background(cardBackgroundColor, CircleShape)
                ) {
                    Icon(
                        imageVector = if (prato.isFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (prato.isFavorito) Color.Red else Color.Gray
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hero Card do Prato
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(cardBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(badgeBackgroundColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.RestaurantMenu,
                            contentDescription = null,
                            tint = goldAccent,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = goldAccent, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${prato.avaliacao} • Criado por ${prato.chef}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Informações do Prato
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = prato.categoria.uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = goldAccent,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = prato.nome,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = prato.descricao,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sugestão de Harmonização com Vinho (Diferencial da Aula)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(cardBackgroundColor)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(badgeBackgroundColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.WineBar, contentDescription = null, tint = goldAccent)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Sugestão de Harmonização do Sommelier",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = goldAccent
                        )
                        Text(
                            text = prato.harmonizacaoVinho,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Ingredientes Selecionados
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Ingredientes Especiais:", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = textColor)
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(cardBackgroundColor)
                        .padding(14.dp)
                ) {
                    Text(text = prato.ingredientes, fontSize = 13.sp, color = Color(0xFF4A4A4A))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Seletor de Quantidade
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Quantidade de porções:", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = textColor)

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(badgeBackgroundColor)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = { if (quantidade > 1) quantidade-- },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Diminuir", tint = Color.Black)
                    }

                    Text(text = "$quantidade", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)

                    IconButton(
                        onClick = { quantidade++ },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Aumentar", tint = Color.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botão Adicionar ao Pedido
            Button(
                onClick = {
                    viewModel.adicionarPratoAoCarrinho(prato, quantidade)
                    mensagemFeedback = "Prato adicionado ao seu pedido com sucesso!"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = cardBackgroundColor)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Adicionar ao Pedido",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = precoFormatado,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = goldAccent
                    )
                }
            }

            mensagemFeedback?.let { msg ->
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = msg, color = Color(0xFF2E7D32), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
