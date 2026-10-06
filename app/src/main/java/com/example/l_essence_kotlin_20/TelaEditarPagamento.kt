package com.example.l_essence_kotlin_20

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditarPagamentoScreen(
    viewModel: MeuViewModel,
    onBack: () -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    val backgroundColor = Color(0xFFFFFFE4)
    val cardBackgroundColor = Color(0xFFECECE3)
    val badgeBackgroundColor = Color(0xFFE2E2D6)
    val goldAccent = Color(0xFFD4AF37)
    val textColor = Color(0xFF5A5A5A)

    var showDialogNovoOuEditar by remember { mutableStateOf(false) }
    var metodoEdicaoAtual by remember { mutableStateOf<MetodoPagamento?>(null) }

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
                    text = "Gerenciar Pagamentos",
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Formas de pagamento cadastradas:",
                color = textColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (viewModel.metodosPagamento.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(cardBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum cartão cadastrado.\nClique abaixo para adicionar!",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            } else {
                viewModel.metodosPagamento.forEach { metodo ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(cardBackgroundColor)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(badgeBackgroundColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = metodo.marca,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = if (metodo.marca == "VISA") Color(0xFF1A1F71) else Color(0xFFEB001B)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = metodo.numeroMascarado,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                            Text(
                                text = "${metodo.nomeTitular} • Val. ${metodo.validade}",
                                fontSize = 12.sp,
                                color = textColor
                            )
                        }

                        Row {
                            IconButton(
                                onClick = {
                                    metodoEdicaoAtual = metodo
                                    showDialogNovoOuEditar = true
                                }
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.DarkGray)
                            }

                            IconButton(
                                onClick = { viewModel.removerMetodoPagamento(metodo.id) }
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Remover", tint = Color.DarkGray)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Adicionar Novo Cartão / Método
            Button(
                onClick = {
                    metodoEdicaoAtual = null
                    showDialogNovoOuEditar = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = cardBackgroundColor)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CreditCard, contentDescription = null, tint = goldAccent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Adicionar Nova Forma de Pagamento",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                }
            }
        }
    }

    if (showDialogNovoOuEditar) {
        DialogFormularioCartao(
            metodoExistente = metodoEdicaoAtual,
            onDismiss = { showDialogNovoOuEditar = false },
            onConfirm = { marca, numero, titular, validade ->
                if (metodoEdicaoAtual != null) {
                    viewModel.editarMetodoPagamento(
                        id = metodoEdicaoAtual!!.id,
                        marca = marca,
                        numeroCompleto = numero,
                        nomeTitular = titular,
                        validade = validade
                    )
                } else {
                    viewModel.adicionarMetodoPagamento(
                        marca = marca,
                        numeroCompleto = numero,
                        nomeTitular = titular,
                        validade = validade
                    )
                }
                showDialogNovoOuEditar = false
            }
        )
    }
}

@Composable
fun DialogFormularioCartao(
    metodoExistente: MetodoPagamento?,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String) -> Unit
) {
    var marca by remember { mutableStateOf(metodoExistente?.marca ?: "VISA") }
    var numero by remember { mutableStateOf(metodoExistente?.numeroMascarado ?: "") }
    var titular by remember { mutableStateOf(metodoExistente?.nomeTitular ?: "") }
    var validade by remember { mutableStateOf(metodoExistente?.validade ?: "") }

    var erroNumero by remember { mutableStateOf<String?>(null) }
    var erroTitular by remember { mutableStateOf<String?>(null) }
    var erroValidade by remember { mutableStateOf<String?>(null) }

    fun validar(): Boolean {
        var ok = true
        if (numero.trim().length < 8) {
            erroNumero = "Informe o número do cartão."
            ok = false
        } else {
            erroNumero = null
        }

        if (titular.trim().isBlank()) {
            erroTitular = "Informe o nome do titular."
            ok = false
        } else {
            erroTitular = null
        }

        if (validade.trim().length < 4) {
            erroValidade = "Validade inválida (ex: 12/28)."
            ok = false
        } else {
            erroValidade = null
        }
        return ok
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (metodoExistente != null) "Editar Cartão de Crédito" else "Adicionar Cartão de Crédito") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = marca == "VISA",
                        onClick = { marca = "VISA" },
                        label = { Text("VISA") }
                    )
                    FilterChip(
                        selected = marca == "MASTERCARD",
                        onClick = { marca = "MASTERCARD" },
                        label = { Text("MASTERCARD") }
                    )
                    FilterChip(
                        selected = marca == "PIX",
                        onClick = { marca = "PIX" },
                        label = { Text("PIX") }
                    )
                }

                OutlinedTextField(
                    value = numero,
                    onValueChange = { numero = it },
                    label = { Text("Número do Cartão") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = erroNumero != null,
                    supportingText = { erroNumero?.let { Text(it, color = Color.Red) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = titular,
                    onValueChange = { titular = it },
                    label = { Text("Nome do Titular impresso no Cartão") },
                    isError = erroTitular != null,
                    supportingText = { erroTitular?.let { Text(it, color = Color.Red) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = validade,
                    onValueChange = { validade = it },
                    label = { Text("Validade (MM/AA)") },
                    isError = erroValidade != null,
                    supportingText = { erroValidade?.let { Text(it, color = Color.Red) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (validar()) {
                        onConfirm(marca, numero, titular, validade)
                    }
                }
            ) {
                Text("Salvar Cartão")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
