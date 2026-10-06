package com.example.l_essence_kotlin_20

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MeuViewModel : ViewModel() {

    // Dados do Perfil do Usuário
    var nomeUsuario by mutableStateOf("Nome Qualquer da Silva")
        private set

    var emailUsuario by mutableStateOf("Qualquer da Silva@lessence-restaurant.com")
        private set

    var enderecoUsuario by mutableStateOf("Av. Paulista, 1000, Apto 82 - Jardins, São Paulo - SP")
        private set

    var telefoneUsuario by mutableStateOf("(67) 98765-4321")
        private set

    fun atualizarPerfil(nome: String, email: String, endereco: String, telefone: String) {
        if (nome.isNotBlank()) nomeUsuario = nome.trim()
        if (email.isNotBlank()) emailUsuario = email.trim()
        if (endereco.isNotBlank()) enderecoUsuario = endereco.trim()
        if (telefone.isNotBlank()) telefoneUsuario = telefone.trim()
    }

    // Lista 1: Menu de Pratos Gourmets
    val pratos = mutableStateListOf(
        Prato(
            id = 1,
            nome = "Minestrone Gourmet Traveller",
            chef = "Chef Jean-Luc",
            categoria = "Entradas",
            precoBase = 120.00,
            ingredientes = "Legumes orgânicos frescos, caldo aromático de ervas finas, massa artesanal e lascas de parmesão reggiano 36 meses.",
            harmonizacaoVinho = "Chardonnay Riserva",
            tempoPreparoMinutos = 20,
            avaliacao = 4.9,
            descricao = "Uma releitura sofisticada do clássico italiano, preparada com técnicas modernas de gastronomia e vegetais da horta orgânica."
        ),
        Prato(
            id = 2,
            nome = "Rigatoni alla Matriciana",
            chef = "Chef Jean-Luc",
            categoria = "Pratos Principais",
            precoBase = 95.00,
            ingredientes = "Massa rigatoni fresca, molho de tomate San Marzano DOP, guanciale artesanal crocante e queijo pecorino romano.",
            harmonizacaoVinho = "Chianti Classico Riserva",
            tempoPreparoMinutos = 25,
            avaliacao = 4.8,
            descricao = "Sabor autêntico e inconfundível. O guanciale dourado no ponto perfeito funde-se ao molho encorpado de tomates italianos."
        ),
        Prato(
            id = 3,
            nome = "Risotto de Cogumelos Porcini & Trufas",
            chef = "Chef Jean-Luc",
            categoria = "Pratos Principais",
            precoBase = 140.00,
            ingredientes = "Arroz Carnaroli, cogumelos Porcini frescos, azeite de trufas brancas de Alba e manteiga clarificada.",
            harmonizacaoVinho = "Barolo DOCG 2016",
            tempoPreparoMinutos = 30,
            avaliacao = 5.0,
            descricao = "Cremoso, aromático e inesquecível. Uma explosão de sabores terrosos com o toque inconfundível das trufas de Alba."
        )
    )

    // Lista 2: Carta de Vinhos Finos
    val vinhos = mutableStateListOf(
        Vinho(
            id = 1,
            rotulo = "RAR Pinot Noir",
            vinicola = "Vinícola RAR",
            safra = 2018,
            paisOrigem = "Brasil / Campos de Cima da Serra",
            tipo = "Tinto Fino",
            precoGarrafa = 80.00,
            teorAlcoolico = "13.0%",
            notasDegustacao = "Aromas de frutas vermelhas maduras, toque sutil de especiarias e carvalho francês.",
            avaliacao = 4.8,
            descricao = "Vinho elegante e bem estruturado, com taninos aveludados e excelente persistência no paladar."
        ),
        Vinho(
            id = 2,
            rotulo = "Château Margaux Grand Cru",
            vinicola = "Château Margaux",
            safra = 2015,
            paisOrigem = "França / Bordeaux",
            tipo = "Tinto Encorpado",
            precoGarrafa = 450.00,
            teorAlcoolico = "13.5%",
            notasDegustacao = "Groselha preta, cassis, notas de tabaco, cedro e violetas. Complexidade incomparável.",
            avaliacao = 5.0,
            descricao = "Um dos mais prestigiados rótulos do mundo. Envelhecido em barricas de carvalho novo por 18 meses."
        ),
        Vinho(
            id = 3,
            rotulo = "Dom Pérignon Vintage",
            vinicola = "Moët & Chandon",
            safra = 2013,
            paisOrigem = "França / Champagne",
            tipo = "Espumante Brut",
            precoGarrafa = 320.00,
            teorAlcoolico = "12.5%",
            notasDegustacao = "Frutas cítricas cristalizadas, brioche tostado, mineralidade vibrante e borbulhas finíssimas.",
            avaliacao = 4.9,
            descricao = "A essência do luxo e da celebração. Equilíbrio perfeito entre maturidade e vivacidade."
        )
    )

    // Métodos de Pagamento Cadastrados
    val metodosPagamento = mutableStateListOf(
        MetodoPagamento(
            id = 1,
            marca = "VISA",
            numeroMascarado = "**** **** 2222",
            nomeTitular = "ANA SILVA",
            validade = "12/28",
            isPadrao = true
        ),
        MetodoPagamento(
            id = 2,
            marca = "MASTERCARD",
            numeroMascarado = "**** **** 5555",
            nomeTitular = "ANA SILVA",
            validade = "08/27",
            isPadrao = false
        )
    )

    // Carrinho de compras do restaurante
    val carrinho = mutableStateListOf<CartItem>()

    // --- Ações de Pratos ---
    fun adicionarPrato(
        nome: String,
        categoria: String,
        precoBase: Double,
        ingredientes: String,
        harmonizacaoVinho: String,
        descricao: String
    ) {
        val novoId = (pratos.maxOfOrNull { it.id } ?: 0) + 1
        pratos.add(
            Prato(
                id = novoId,
                nome = nome,
                categoria = categoria.ifBlank { "Pratos Principais" },
                precoBase = precoBase,
                ingredientes = ingredientes.ifBlank { "Ingredientes selecionados pelo Chef" },
                harmonizacaoVinho = harmonizacaoVinho.ifBlank { "Vinho Tinto L'Essence" },
                descricao = descricao.ifBlank { "Prato exclusivo da alta gastronomia L'Essence." }
            )
        )
    }

    fun removerPrato(id: Int) {
        val prato = pratos.find { it.id == id }
        pratos.removeAll { it.id == id }
        if (prato != null) {
            carrinho.removeAll { it.titulo == prato.nome }
        }
    }

    fun toggleFavoritoPrato(id: Int) {
        val index = pratos.indexOfFirst { it.id == id }
        if (index != -1) {
            val actual = pratos[index]
            pratos[index] = actual.copy(isFavorito = !actual.isFavorito)
        }
    }

    fun obterPratoPorId(id: Int): Prato? {
        return pratos.find { it.id == id }
    }

    // --- Ações de Vinhos ---
    fun adicionarVinho(
        rotulo: String,
        vinicola: String,
        safra: Int,
        paisOrigem: String,
        tipo: String,
        precoGarrafa: Double,
        notasDegustacao: String,
        descricao: String
    ) {
        val novoId = (vinhos.maxOfOrNull { it.id } ?: 0) + 1
        vinhos.add(
            Vinho(
                id = novoId,
                rotulo = rotulo,
                vinicola = vinicola.ifBlank { "Adega L'Essence" },
                safra = if (safra > 0) safra else 2020,
                paisOrigem = paisOrigem.ifBlank { "França" },
                tipo = tipo.ifBlank { "Tinto" },
                precoGarrafa = precoGarrafa,
                notasDegustacao = notasDegustacao.ifBlank { "Aromas frutados e carvalho" },
                descricao = descricao.ifBlank { "Rótulo exclusivo selecionado por nossos sommeliers." }
            )
        )
    }

    fun removerVinho(id: Int) {
        val vinho = vinhos.find { it.id == id }
        vinhos.removeAll { it.id == id }
        if (vinho != null) {
            carrinho.removeAll { it.titulo == vinho.rotulo }
        }
    }

    fun toggleReservadoVinho(id: Int) {
        val index = vinhos.indexOfFirst { it.id == id }
        if (index != -1) {
            val actual = vinhos[index]
            vinhos[index] = actual.copy(isReservado = !actual.isReservado)
        }
    }

    fun toggleFavoritoVinho(id: Int) {
        val index = vinhos.indexOfFirst { it.id == id }
        if (index != -1) {
            val actual = vinhos[index]
            vinhos[index] = actual.copy(isFavorito = !actual.isFavorito)
        }
    }

    fun obterVinhoPorId(id: Int): Vinho? {
        return vinhos.find { it.id == id }
    }

    // --- Ações de Métodos de Pagamento ---
    fun adicionarMetodoPagamento(
        marca: String,
        numeroCompleto: String,
        nomeTitular: String,
        validade: String
    ) {
        val ultimos4 = if (numeroCompleto.length >= 4) numeroCompleto.takeLast(4) else "0000"
        val numeroMascarado = "**** **** $ultimos4"
        val novoId = (metodosPagamento.maxOfOrNull { it.id } ?: 0) + 1

        val eOPrimeiro = metodosPagamento.isEmpty()

        metodosPagamento.add(
            MetodoPagamento(
                id = novoId,
                marca = marca.uppercase(),
                numeroMascarado = numeroMascarado,
                nomeTitular = nomeTitular.uppercase(),
                validade = validade,
                isPadrao = eOPrimeiro
            )
        )
    }

    fun editarMetodoPagamento(
        id: Int,
        marca: String,
        numeroCompleto: String,
        nomeTitular: String,
        validade: String
    ) {
        val index = metodosPagamento.indexOfFirst { it.id == id }
        if (index != -1) {
            val ultimos4 = if (numeroCompleto.length >= 4) numeroCompleto.takeLast(4) else "0000"
            val numeroMascarado = if (numeroCompleto.contains("*")) metodosPagamento[index].numeroMascarado else "**** **** $ultimos4"
            metodosPagamento[index] = metodosPagamento[index].copy(
                marca = marca.uppercase(),
                numeroMascarado = numeroMascarado,
                nomeTitular = nomeTitular.uppercase(),
                validade = validade
            )
        }
    }

    fun removerMetodoPagamento(id: Int) {
        metodosPagamento.removeAll { it.id == id }
        if (metodosPagamento.isNotEmpty() && metodosPagamento.none { it.isPadrao }) {
            metodosPagamento[0].isPadrao = true
        }
    }

    fun selecionarMetodoPadrao(id: Int) {
        metodosPagamento.forEach {
            it.isPadrao = (it.id == id)
        }
    }

    // --- Ações do Carrinho ---
    fun adicionarPratoAoCarrinho(prato: Prato, quantidade: Int = 1) {
        val index = carrinho.indexOfFirst { it.titulo == prato.nome }
        if (index != -1) {
            val item = carrinho[index]
            carrinho[index] = item.copy(quantidade = item.quantidade + quantidade)
        } else {
            val novoId = (carrinho.maxOfOrNull { it.id } ?: 0) + 1
            carrinho.add(
                CartItem(
                    id = novoId,
                    titulo = prato.nome,
                    categoriaOuTipo = prato.categoria,
                    quantidade = quantidade,
                    precoUnitario = prato.precoBase
                )
            )
        }
    }

    fun adicionarVinhoAoCarrinho(vinho: Vinho, quantidade: Int = 1) {
        val index = carrinho.indexOfFirst { it.titulo == vinho.rotulo }
        if (index != -1) {
            val item = carrinho[index]
            carrinho[index] = item.copy(quantidade = item.quantidade + quantidade)
        } else {
            val novoId = (carrinho.maxOfOrNull { it.id } ?: 0) + 1
            carrinho.add(
                CartItem(
                    id = novoId,
                    titulo = vinho.rotulo,
                    categoriaOuTipo = "Vinho ${vinho.tipo}",
                    quantidade = quantidade,
                    precoUnitario = vinho.precoGarrafa
                )
            )
        }
    }

    fun alterarQuantidadeCarrinho(cartItemId: Int, delta: Int) {
        val index = carrinho.indexOfFirst { it.id == cartItemId }
        if (index != -1) {
            val item = carrinho[index]
            val novaQtd = item.quantidade + delta
            if (novaQtd > 0) {
                carrinho[index] = item.copy(quantidade = novaQtd)
            } else {
                carrinho.removeAt(index)
            }
        }
    }

    fun removerDoCarrinho(cartItemId: Int) {
        carrinho.removeAll { it.id == cartItemId }
    }

    fun limparCarrinho() {
        carrinho.clear()
    }

    fun calcularTotalCarrinho(): Double {
        return carrinho.sumOf { it.precoTotal }
    }

    fun quantidadeTotalItensCarrinho(): Int {
        return carrinho.sumOf { it.quantidade }
    }
}
