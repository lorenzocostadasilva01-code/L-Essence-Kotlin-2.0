package com.example.l_essence_kotlin_20

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
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
fun PagamentoScreen(
    totalAmount: String,
    viewModel: MeuViewModel,
    onBack: () -> Unit,
    onNavigateToEditarPagamento: () -> Unit,
    onNavigateToPerfil: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    val backgroundColor = Color(0xFFFFFFE4)
    val cardBackgroundColor = Color(0xFFECECE3)
    val badgeBackgroundColor = Color(0xFFE2E2D6)
    val goldAccent = Color(0xFFD4AF37)
    val textColor = Color(0xFF5A5A5A)

    var idMetodoSelecionado by remember {
        mutableStateOf(viewModel.metodosPagamento.firstOrNull { it.isPadrao }?.id ?: viewModel.metodosPagamento.firstOrNull()?.id ?: 1)
    }
    var showSuccessDialog by remember { mutableStateOf(false) }

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
                    text = "Pagamento do Pedido",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                IconButton(
                    onClick = onNavigateToPerfil,
                    modifier = Modifier
                        .size(40.dp)
                        .background(goldAccent, CircleShape)
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Selecione a forma de pagamento:",
                    color = textColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )

                TextButton(onClick = onNavigateToEditarPagamento) {
                    Text(text = "Gerenciar / Editar", color = goldAccent, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (viewModel.metodosPagamento.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(cardBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Nenhum método cadastrado. Clique acima para gerenciar.", color = Color.Gray)
                }
            } else {
                viewModel.metodosPagamento.forEach { metodo ->
                    val isSelected = idMetodoSelecionado == metodo.id

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(cardBackgroundColor)
                            .border(
                                width = if (isSelected) 2.dp else 0.dp,
                                color = if (isSelected) goldAccent else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                idMetodoSelecionado = metodo.id
                                viewModel.selecionarMetodoPadrao(metodo.id)
                            }
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(badgeBackgroundColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = metodo.marca,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = if (metodo.marca == "VISA") Color(0xFF1A1F71) else Color(0xFFEB001B)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = metodo.numeroMascarado,
                                color = Color(0xFF333333),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${metodo.nomeTitular} • Val. ${metodo.validade}",
                                color = textColor,
                                fontSize = 12.sp
                            )
                        }

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selecionado",
                                tint = goldAccent,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            IconButton(onClick = onNavigateToEditarPagamento) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray, modifier = Modifier.size(20.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Total do Pedido
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(19.dp))
                        .background(cardBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Total a Pagar",
                        color = Color(0xFF8A8A80),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(19.dp))
                        .background(badgeBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = totalAmount.ifBlank { "R$ 0,00" },
                        color = Color(0xFF333333),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botão Confirmar Pagamento
            Button(
                onClick = { showSuccessDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = cardBackgroundColor)
            ) {
                Text(
                    text = "Confirmar e Finalizar Compra",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(48.dp)
                )
            },
            title = { Text("Pedido Confirmado!") },
            text = {
                Text(
                    "Seu pagamento no valor de $totalAmount foi processado com sucesso. O Chef já está preparando seu pedido no L'Essence!",
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        onPaymentSuccess()
                    }
                ) {
                    Text("Voltar ao Início")
                }
            }
        )
    }
}
