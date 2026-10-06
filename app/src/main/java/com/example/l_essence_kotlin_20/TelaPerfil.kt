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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
fun PerfilScreen(
    viewModel: MeuViewModel,
    onBack: () -> Unit,
    onNavigateToPratos: () -> Unit,
    onNavigateToVinhos: () -> Unit
) {
    val backgroundColor = Color(0xFFFFFFE4)
    val cardBackgroundColor = Color(0xFFECECE3)
    val badgeBackgroundColor = Color(0xFFE2E2D6)
    val goldAccent = Color(0xFFD4AF37)
    val textColor = Color(0xFF5A5A5A)

    var showEditarPerfilDialog by remember { mutableStateOf(false) }

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
                    text = "Perfil Gourmet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                IconButton(
                    onClick = { showEditarPerfilDialog = true },
                    modifier = Modifier
                        .size(42.dp)
                        .background(goldAccent, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar Perfil",
                        tint = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Card Principal
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(cardBackgroundColor)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(goldAccent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(46.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = viewModel.nomeUsuario,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(badgeBackgroundColor)
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Cliente VIP Gastronomia L'Essence",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = goldAccent
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botão Editar Perfil
                    Button(
                        onClick = { showEditarPerfilDialog = true },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = goldAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Editar Perfil & Endereço", fontSize = 13.sp, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Detalhes do Usuário (Email, Endereço, Telefone)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(cardBackgroundColor)
                    .padding(18.dp)
            ) {
                Text(
                    text = "Dados Cadastrais & Entrega",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(12.dp))

                ItemInfoPerfilRow(
                    icon = Icons.Default.Email,
                    label = "E-mail:",
                    valor = viewModel.emailUsuario,
                    goldAccent = goldAccent,
                    textColor = textColor
                )

                Spacer(modifier = Modifier.height(10.dp))

                ItemInfoPerfilRow(
                    icon = Icons.Default.Phone,
                    label = "Telefone:",
                    valor = viewModel.telefoneUsuario,
                    goldAccent = goldAccent,
                    textColor = textColor
                )

                Spacer(modifier = Modifier.height(10.dp))

                ItemInfoPerfilRow(
                    icon = Icons.Default.LocationOn,
                    label = "Endereço de Entrega:",
                    valor = viewModel.enderecoUsuario,
                    goldAccent = goldAccent,
                    textColor = textColor
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

        }
    }

    if (showEditarPerfilDialog) {
        DialogEditarPerfil(
            nomeAtual = viewModel.nomeUsuario,
            emailAtual = viewModel.emailUsuario,
            enderecoAtual = viewModel.enderecoUsuario,
            telefoneAtual = viewModel.telefoneUsuario,
            onDismiss = { showEditarPerfilDialog = false },
            onSave = { nome, email, endereco, telefone ->
                viewModel.atualizarPerfil(
                    nome = nome,
                    email = email,
                    endereco = endereco,
                    telefone = telefone
                )
                showEditarPerfilDialog = false
            }
        )
    }
}

@Composable
fun ProfileOptionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    goldAccent: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = goldAccent, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333),
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}

@Composable
fun ItemInfoPerfilRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    valor: String,
    goldAccent: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = goldAccent, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Text(text = valor, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = textColor)
        }
    }
}

@Composable
fun DialogEditarPerfil(
    nomeAtual: String,
    emailAtual: String,
    enderecoAtual: String,
    telefoneAtual: String,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String) -> Unit
) {
    var nome by remember { mutableStateOf(nomeAtual) }
    var email by remember { mutableStateOf(emailAtual) }
    var endereco by remember { mutableStateOf(enderecoAtual) }
    var telefone by remember { mutableStateOf(telefoneAtual) }

    var erroNome by remember { mutableStateOf<String?>(null) }
    var erroEmail by remember { mutableStateOf<String?>(null) }

    fun validar(): Boolean {
        var ok = true
        if (nome.trim().length < 2) {
            erroNome = "Informe um nome válido."
            ok = false
        } else {
            erroNome = null
        }

        if (!email.contains("@") || email.trim().length < 5) {
            erroEmail = "Informe um e-mail válido."
            ok = false
        } else {
            erroEmail = null
        }
        return ok
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Perfil Gourmet") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome Completo") },
                    isError = erroNome != null,
                    supportingText = { erroNome?.let { Text(it, color = Color.Red) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
                    isError = erroEmail != null,
                    supportingText = { erroEmail?.let { Text(it, color = Color.Red) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = telefone,
                    onValueChange = { telefone = it },
                    label = { Text("Telefone / WhatsApp") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = endereco,
                    onValueChange = { endereco = it },
                    label = { Text("Endereço Completo de Entrega") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (validar()) {
                        onSave(nome, email, endereco, telefone)
                    }
                }
            ) {
                Text("Salvar Alterações")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
