package com.example.l_essence_kotlin_20

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun DetalheVinhoScreen(
    vinhoId: Int,
    viewModel: MeuViewModel,
    onBack: () -> Unit
) {
    val vinho = viewModel.obterVinhoPorId(vinhoId)

    val backgroundColor = Color(0xFFFFFFE4)
    val cardBackgroundColor = Color(0xFFECECE3)
    val badgeBackgroundColor = Color(0xFFE2E2D6)
    val goldAccent = Color(0xFFD4AF37)
    val textColor = Color(0xFF5A5A5A)

    var quantidadeGarrafas by remember { mutableStateOf(1) }
    var mensagemFeedback by remember { mutableStateOf<String?>(null) }

    if (vinho == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Vinho não encontrado.", color = Color.Red, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onBack) {
                    Text(text = "Voltar")
                }
            }
        }
        return
    }

    val precoCalculado = vinho.precoGarrafa * quantidadeGarrafas
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
                    text = "Ficha do Vinho",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.size(42.dp))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cartão de Apresentação da Garrafa
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(cardBackgroundColor)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(badgeBackgroundColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WineBar,
                            contentDescription = null,
                            tint = goldAccent,
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = vinho.rotulo,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF333333)
                    )

                    Text(
                        text = "${vinho.vinicola} • Safra ${vinho.safra}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = goldAccent
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = goldAccent, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${vinho.avaliacao} • Origem: ${vinho.paisOrigem}", fontSize = 13.sp, color = textColor)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = vinho.descricao,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Notas de Degustação & Detalhes Técnicos
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(cardBackgroundColor)
                    .padding(18.dp)
            ) {
                Text(
                    text = "Notas de Degustação do Sommelier",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(badgeBackgroundColor)
                        .padding(12.dp)
                ) {
                    Text(text = vinho.notasDegustacao, fontSize = 13.sp, color = Color(0xFF4A4A4A))
                }

                Spacer(modifier = Modifier.height(12.dp))

                ItemFichaRow(label = "Tipo de Vinho:", valor = vinho.tipo, badgeBg = badgeBackgroundColor, textColor = textColor)
                Spacer(modifier = Modifier.height(6.dp))
                ItemFichaRow(label = "Teor Alcoólico:", valor = vinho.teorAlcoolico, badgeBg = badgeBackgroundColor, textColor = textColor)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Seletor de Garrafas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Quantidade de Garrafas:", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = textColor)

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(badgeBackgroundColor)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = { if (quantidadeGarrafas > 1) quantidadeGarrafas-- },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Diminuir", tint = Color.Black)
                    }

                    Text(text = "$quantidadeGarrafas", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)

                    IconButton(
                        onClick = { quantidadeGarrafas++ },
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
                    viewModel.adicionarVinhoAoCarrinho(vinho, quantidadeGarrafas)
                    mensagemFeedback = "Vinho adicionado ao seu pedido com sucesso!"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
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

            Spacer(modifier = Modifier.height(12.dp))

            // Botão de Reservar Garrafa na Adega
            Button(
                onClick = { viewModel.toggleReservadoVinho(vinho.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (vinho.isReservado) Color(0xFF2E7D32) else Color.Black
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (vinho.isReservado) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Garrafa Reservada na Adega ✓", fontSize = 15.sp, color = Color.White)
                    } else {
                        Text(text = "Reservar Garrafa na Adega", fontSize = 15.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemFichaRow(label: String, valor: String, badgeBg: Color, textColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(badgeBg)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(text = valor, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = textColor)
        }
    }
}
