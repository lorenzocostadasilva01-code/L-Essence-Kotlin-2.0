package com.example.l_essence_kotlin_20

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private enum class TipoProdutoCadastro {
    PRATO,
    VINHO
}

@Composable
fun CadastroProdutoScreen(
    viewModel: MeuViewModel,
    onBack: () -> Unit,
    onSuccessNavigateToCardapio: () -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    val backgroundColor = Color(0xFFFFFFE4)
    val cardBackgroundColor = Color(0xFFECECE3)
    val badgeBackgroundColor = Color(0xFFE2E2D6)
    val goldAccent = Color(0xFFD4AF37)
    val textColor = Color(0xFF5A5A5A)

    var tipoSelecionado by remember { mutableStateOf(TipoProdutoCadastro.PRATO) }

    // Campos do formulário
    var nomeOuRotulo by remember { mutableStateOf("") }
    var categoriaOuTipo by remember { mutableStateOf("") }
    var precoText by remember { mutableStateOf("") }
    var ingredientesOuNotas by remember { mutableStateOf("") }
    var harmonizacaoOuVinicola by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var safraText by remember { mutableStateOf("2020") }
    var paisOrigem by remember { mutableStateOf("França") }

    // Estados de validação
    var erroNome by remember { mutableStateOf<String?>(null) }
    var erroCategoria by remember { mutableStateOf<String?>(null) }
    var erroPreco by remember { mutableStateOf<String?>(null) }
    var erroIngredientes by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    fun validarFormulario(): Boolean {
        var valido = true

        if (nomeOuRotulo.trim().length < 3) {
            erroNome = "Informe um nome válido com no mínimo 3 caracteres."
            valido = false
        } else {
            erroNome = null
        }

        if (categoriaOuTipo.trim().isBlank()) {
            erroCategoria = "A categoria/tipo não pode ficar em branco."
            valido = false
        } else {
            erroCategoria = null
        }

        val precoParsed = precoText.replace(",", ".").toDoubleOrNull()
        if (precoParsed == null || precoParsed <= 0.0) {
            erroPreco = "Informe um preço numérico válido e maior que R$ 0,00."
            valido = false
        } else {
            erroPreco = null
        }

        if (ingredientesOuNotas.trim().isBlank()) {
            erroIngredientes = "Informe os ingredientes ou notas de degustação."
            valido = false
        } else {
            erroIngredientes = null
        }

        return valido
    }

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
                    text = "Cadastrar Produto",
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
                text = "Selecione o tipo de item para cadastrar:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Seletor do tipo (Prato vs Vinho)
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
                        .background(if (tipoSelecionado == TipoProdutoCadastro.PRATO) goldAccent else Color.Transparent)
                        .clickable {
                            tipoSelecionado = TipoProdutoCadastro.PRATO
                            categoriaOuTipo = "Pratos Principais"
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.RestaurantMenu,
                            contentDescription = null,
                            tint = if (tipoSelecionado == TipoProdutoCadastro.PRATO) Color.Black else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Prato Gourmet",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (tipoSelecionado == TipoProdutoCadastro.PRATO) Color.Black else Color.DarkGray
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (tipoSelecionado == TipoProdutoCadastro.VINHO) goldAccent else Color.Transparent)
                        .clickable {
                            tipoSelecionado = TipoProdutoCadastro.VINHO
                            categoriaOuTipo = "Tinto Fino"
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.WineBar,
                            contentDescription = null,
                            tint = if (tipoSelecionado == TipoProdutoCadastro.VINHO) Color.Black else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Vinho Fino",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (tipoSelecionado == TipoProdutoCadastro.VINHO) Color.Black else Color.DarkGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Formulário de Cadastro com Validação
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(cardBackgroundColor)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = if (tipoSelecionado == TipoProdutoCadastro.PRATO) "Dados do Prato" else "Dados do Vinho",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                // Nome / Rótulo
                OutlinedTextField(
                    value = nomeOuRotulo,
                    onValueChange = {
                        nomeOuRotulo = it
                        if (erroNome != null) validarFormulario()
                    },
                    label = { Text(if (tipoSelecionado == TipoProdutoCadastro.PRATO) "Nome do Prato *" else "Rótulo do Vinho *") },
                    isError = erroNome != null,
                    supportingText = { erroNome?.let { Text(it, color = Color.Red) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Categoria / Tipo
                OutlinedTextField(
                    value = categoriaOuTipo,
                    onValueChange = {
                        categoriaOuTipo = it
                        if (erroCategoria != null) validarFormulario()
                    },
                    label = { Text(if (tipoSelecionado == TipoProdutoCadastro.PRATO) "Categoria (ex: Entradas, Sobremesas) *" else "Tipo (ex: Tinto Fino, Espumante) *") },
                    isError = erroCategoria != null,
                    supportingText = { erroCategoria?.let { Text(it, color = Color.Red) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Preço (R$)
                OutlinedTextField(
                    value = precoText,
                    onValueChange = {
                        precoText = it
                        if (erroPreco != null) validarFormulario()
                    },
                    label = { Text("Preço R$ *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = erroPreco != null,
                    supportingText = { erroPreco?.let { Text(it, color = Color.Red) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Ingredientes / Notas
                OutlinedTextField(
                    value = ingredientesOuNotas,
                    onValueChange = {
                        ingredientesOuNotas = it
                        if (erroIngredientes != null) validarFormulario()
                    },
                    label = { Text(if (tipoSelecionado == TipoProdutoCadastro.PRATO) "Ingredientes Principais *" else "Notas de Degustação *") },
                    isError = erroIngredientes != null,
                    supportingText = { erroIngredientes?.let { Text(it, color = Color.Red) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (tipoSelecionado == TipoProdutoCadastro.PRATO) {
                    OutlinedTextField(
                        value = harmonizacaoOuVinicola,
                        onValueChange = { harmonizacaoOuVinicola = it },
                        label = { Text("Sugestão de Harmonização com Vinho") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    OutlinedTextField(
                        value = harmonizacaoOuVinicola,
                        onValueChange = { harmonizacaoOuVinicola = it },
                        label = { Text("Vinícola / Produtor") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = safraText,
                            onValueChange = { safraText = it },
                            label = { Text("Safra") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = paisOrigem,
                            onValueChange = { paisOrigem = it },
                            label = { Text("País de Origem") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Descrição Geral
                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição / Detalhes Autorais") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botão de Cadastrar
            Button(
                onClick = {
                    if (validarFormulario()) {
                        val precoDouble = precoText.replace(",", ".").toDouble()
                        if (tipoSelecionado == TipoProdutoCadastro.PRATO) {
                            viewModel.adicionarPrato(
                                nome = nomeOuRotulo.trim(),
                                categoria = categoriaOuTipo.trim(),
                                precoBase = precoDouble,
                                ingredientes = ingredientesOuNotas.trim(),
                                harmonizacaoVinho = harmonizacaoOuVinicola.trim(),
                                descricao = descricao.trim()
                            )
                        } else {
                            viewModel.adicionarVinho(
                                rotulo = nomeOuRotulo.trim(),
                                vinicola = harmonizacaoOuVinicola.trim(),
                                safra = safraText.toIntOrNull() ?: 2020,
                                paisOrigem = paisOrigem.trim(),
                                tipo = categoriaOuTipo.trim(),
                                precoGarrafa = precoDouble,
                                notasDegustacao = ingredientesOuNotas.trim(),
                                descricao = descricao.trim()
                            )
                        }
                        showSuccessDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = cardBackgroundColor)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = goldAccent
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Validar e Cadastrar Produto",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                }
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
            title = { Text("Produto Cadastrado com Sucesso!") },
            text = {
                Text(
                    "O produto '$nomeOuRotulo' foi validado e inserido no cardápio do Restaurante L'Essence.",
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        onSuccessNavigateToCardapio()
                    }
                ) {
                    Text("Ver no Cardápio")
                }
            }
        )
    }
}
