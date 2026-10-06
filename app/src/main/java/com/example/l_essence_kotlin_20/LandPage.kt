package com.example.l_essence_kotlin_20

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LandPageScreen(
    onNavigateToPratos: () -> Unit,
    onNavigateToVinhos: () -> Unit,
    onNavigateToDetalhePrato: (Int) -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    val backgroundColor = Color(0xFFFFFFE4)
    val cardBackgroundColor = Color(0xFFECECE3)
    val badgeBackgroundColor = Color(0xFFE2E2D6)
    val goldAccent = Color(0xFFD4AF37)
    val textColor = Color(0xFF5A5A5A)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .border(1.5.dp, goldAccent, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "♢", color = goldAccent, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "L'ESSENCE",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    letterSpacing = 2.sp
                )
                Text(
                    text = "HAUTE GASTRONOMIE",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = goldAccent,
                    letterSpacing = 1.sp
                )
            }

            IconButton(
                onClick = onNavigateToPerfil,
                modifier = Modifier
                    .size(42.dp)
                    .background(goldAccent, CircleShape)
            ) {
                Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Hero Card de Luxo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(cardBackgroundColor)
                .border(1.dp, goldAccent.copy(alpha = 0.3f), RoundedCornerShape(30.dp))
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
                        imageVector = Icons.Default.Restaurant,
                        contentDescription = "Restaurante",
                        tint = goldAccent,
                        modifier = Modifier.size(44.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(goldAccent)
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "ESTRELA MICHELIN EXPERIENCE 2025",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "L'ESSENCE",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF333333),
                    letterSpacing = 3.sp
                )

                Text(
                    text = "Gastronomia Contemporânea & Adega de Seleção",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Uma jornada sensorial inesquecível. Pratos autorais refinados pelo Chef Jean-Luc e os rótulos de vinhos mais aclamados do mundo.",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Destaques / Estatísticas em linha
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DestaqueCard(titulo = "4.9 ★", subtitulo = "Avaliação dos Clientes", badgeBg = cardBackgroundColor, gold = goldAccent, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(10.dp))
            DestaqueCard(titulo = "100%", subtitulo = "Ingredientes Orgânicos", badgeBg = cardBackgroundColor, gold = goldAccent, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(10.dp))
            DestaqueCard(titulo = "Sommelier", subtitulo = "Carta Exclusiva", badgeBg = cardBackgroundColor, gold = goldAccent, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botão 1: Cardápio Completo
        Button(
            onClick = onNavigateToPratos,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = cardBackgroundColor)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.RestaurantMenu,
                    contentDescription = null,
                    tint = goldAccent,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Explorar Cardápio Completo",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botão 2: Carta de Vinhos Finos
        Button(
            onClick = onNavigateToVinhos,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = cardBackgroundColor)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.WineBar,
                    contentDescription = null,
                    tint = goldAccent,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Ver Carta de Vinhos Finos",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Card Clicável: Recomendação do Chef -> Vai Direto para o Prato (ex: Risotto id = 3)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(cardBackgroundColor)
                .clickable { onNavigateToDetalhePrato(3) }
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(badgeBackgroundColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = goldAccent)
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "RECOMENDAÇÃO DO CHEF • CLIQUE PARA VER",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = goldAccent,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Risotto de Cogumelos Porcini & Trufas",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = "Harmonização perfeita com Barolo DOCG 2016",
                        fontSize = 12.sp,
                        color = textColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "L'Essence Restaurant & Cave © 2025",
            fontSize = 11.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun DestaqueCard(titulo: String, subtitulo: String, badgeBg: Color, gold: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(badgeBg)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = titulo, fontSize = 15.sp, fontWeight = FontWeight.Black, color = gold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitulo,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5A5A5A),
                textAlign = TextAlign.Center,
                lineHeight = 12.sp
            )
        }
    }
}
