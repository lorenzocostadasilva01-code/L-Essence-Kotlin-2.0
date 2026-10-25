package com.example.l_essence_kotlin_20

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.l_essence_kotlin_20.ui.dao.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MeuViewModel(
    private val userDao: UserDao,
    private val pratoDao: PratoDAO,
    private val vinhoDao: VinhoDAO,
    private val metodoPagamentoDao: MetodoPagamentoDAO,
    private val cartItemDao: CartItemDao
) : ViewModel() {

    // --- ESTADOS REATIVOS (StateFlow) ---
    // O UI vai observar essas variáveis usando .collectAsState()

    val usuario: StateFlow<User?> = userDao.observarUsuario()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val pratos: StateFlow<List<Prato>> = pratoDao.observarTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val vinhos: StateFlow<List<Vinho>> = vinhoDao.observarTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val metodosPagamento: StateFlow<List<MetodoPagamento>> = metodoPagamentoDao.observarTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val carrinho: StateFlow<List<CartItem>> = cartItemDao.observarTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalCarrinho: StateFlow<Double> = cartItemDao.observarTotal()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val quantidadeCarrinho: StateFlow<Int> = cartItemDao.observarQuantidadeTotal()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // --- HELPER GETTERS PARA UI ---
    val nomeUsuario: String get() = usuario.value?.nome ?: "Gourmet Guest"
    val emailUsuario: String get() = usuario.value?.email ?: "guest@lessence.com"
    val telefoneUsuario: String get() = usuario.value?.telefone ?: "(11) 99999-8888"
    val enderecoUsuario: String get() = usuario.value?.endereco ?: "Alameda Gourmet, 1000"

    fun obterPratoPorId(id: Int): Prato? = pratos.value.find { it.id == id }
    fun obterVinhoPorId(id: Int): Vinho? = vinhos.value.find { it.id == id }
    fun calcularTotalCarrinho(): Double = totalCarrinho.value
    fun quantidadeTotalItensCarrinho(): Int = quantidadeCarrinho.value

    // --- AÇÕES DO USUÁRIO ---
    fun atualizarPerfil(nome: String, email: String, endereco: String, telefone: String) {
        viewModelScope.launch {
            val user = User(
                uid = 1, // Sempre 1 para perfil único
                nome = nome.ifBlank { usuario.value?.nome ?: "" },
                email = email.ifBlank { usuario.value?.email ?: "" },
                endereco = endereco.ifBlank { usuario.value?.endereco ?: "" },
                telefone = telefone.ifBlank { usuario.value?.telefone ?: "" }
            )
            userDao.salvar(user)
        }
    }

    // --- AÇÕES DE PRATOS ---
    fun adicionarPrato(nome: String, categoria: String, precoBase: Double, ingredientes: String, harmonizacaoVinho: String, descricao: String) {
        viewModelScope.launch {
            pratoDao.inserir(
                Prato(
                    nome = nome,
                    categoria = categoria.ifBlank { "Pratos Principais" },
                    precoBase = precoBase,
                    ingredientes = ingredientes.ifBlank { "Ingredientes selecionados pelo Chef" },
                    harmonizacaoVinho = harmonizacaoVinho.ifBlank { "Vinho Tinto L'Essence" },
                    descricao = descricao.ifBlank { "Prato exclusivo da alta gastronomia." }
                )
            )
        }
    }

    fun removerPrato(id: Int) {
        viewModelScope.launch {
            pratoDao.removerPorId(id)
            cartItemDao.removerPorProduto(produtoId = id, tipoProduto = "Prato")
        }
    }

    fun toggleFavoritoPrato(id: Int) {
        viewModelScope.launch { pratoDao.alternarFavorito(id) }
    }

    suspend fun buscarPratoPorId(id: Int): Prato? {
        return pratoDao.buscarPorId(id)
    }

    // --- AÇÕES DE VINHOS ---
    fun adicionarVinho(rotulo: String, vinicola: String, safra: Int, paisOrigem: String, tipo: String, precoGarrafa: Double, notasDegustacao: String, descricao: String) {
        viewModelScope.launch {
            vinhoDao.inserir(
                Vinho(
                    rotulo = rotulo,
                    vinicola = vinicola.ifBlank { "Adega L'Essence" },
                    safra = if (safra > 0) safra else 2020,
                    paisOrigem = paisOrigem.ifBlank { "França" },
                    tipo = tipo.ifBlank { "Tinto" },
                    precoGarrafa = precoGarrafa,
                    notasDegustacao = notasDegustacao.ifBlank { "Aromas frutados e carvalho" },
                    descricao = descricao.ifBlank { "Rótulo selecionado pelos sommeliers." }
                )
            )
        }
    }

    fun removerVinho(id: Int) {
        viewModelScope.launch {
            vinhoDao.removerPorId(id)
            cartItemDao.removerPorProduto(produtoId = id, tipoProduto = "Vinho")
        }
    }

    fun toggleReservadoVinho(id: Int) {
        viewModelScope.launch { vinhoDao.alternarReservado(id) }
    }

    fun toggleFavoritoVinho(id: Int) {
        viewModelScope.launch { vinhoDao.alternarFavorito(id) }
    }

    suspend fun buscarVinhoPorId(id: Int): Vinho? {
        return vinhoDao.buscarPorId(id)
    }

    // --- AÇÕES DE PAGAMENTO ---
    fun adicionarMetodoPagamento(marca: String, numeroCompleto: String, nomeTitular: String, validade: String) {
        viewModelScope.launch {
            val ultimos4 = if (numeroCompleto.length >= 4) numeroCompleto.takeLast(4) else "0000"
            val numeroMascarado = "**** **** $ultimos4"
            val count = metodoPagamentoDao.contar()

            metodoPagamentoDao.inserir(
                MetodoPagamento(
                    marca = marca.uppercase(),
                    numeroMascarado = numeroMascarado,
                    nomeTitular = nomeTitular.uppercase(),
                    validade = validade,
                    isPadrao = count == 0 // Primeiro cartão vira padrão automaticamente
                )
            )
        }
    }

    fun editarMetodoPagamento(id: Int, marca: String, numeroCompleto: String, nomeTitular: String, validade: String) {
        viewModelScope.launch {
            metodoPagamentoDao.editar(
                id = id,
                marca = marca.uppercase(),
                numeroMascarado = if (numeroCompleto.contains("*")) numeroCompleto else "**** **** ${numeroCompleto.takeLast(4)}",
                nomeTitular = nomeTitular.uppercase(),
                validade = validade
            )
        }
    }

    fun removerMetodoPagamento(id: Int) {
        viewModelScope.launch {
            metodoPagamentoDao.removerPorId(id)
        }
    }

    fun selecionarMetodoPadrao(id: Int) {
        viewModelScope.launch {
            metodoPagamentoDao.selecionarPadrao(id)
        }
    }

    // --- AÇÕES DE CARRINHO ---
    fun adicionarPratoAoCarrinho(prato: Prato, quantidade: Int = 1) {
        viewModelScope.launch {
            cartItemDao.adicionarOuIncrementar(
                CartItem(
                    produtoId = prato.id,
                    tipoProduto = "Prato",
                    titulo = prato.nome,
                    categoriaOuTipo = prato.categoria,
                    quantidade = quantidade,
                    precoUnitario = prato.precoBase
                )
            )
        }
    }

    fun adicionarVinhoAoCarrinho(vinho: Vinho, quantidade: Int = 1) {
        viewModelScope.launch {
            cartItemDao.adicionarOuIncrementar(
                CartItem(
                    produtoId = vinho.id,
                    tipoProduto = "Vinho",
                    titulo = vinho.rotulo,
                    categoriaOuTipo = "Vinho ${vinho.tipo}",
                    quantidade = quantidade,
                    precoUnitario = vinho.precoGarrafa
                )
            )
        }
    }

    fun alterarQuantidadeCarrinho(cartItemId: Int, delta: Int) {
        viewModelScope.launch {
            cartItemDao.alterarQuantidadePorId(cartItemId, delta)
            cartItemDao.removerSeZerado(cartItemId)
        }
    }

    fun removerDoCarrinho(cartItemId: Int) {
        viewModelScope.launch {
            cartItemDao.removerPorId(cartItemId)
        }
    }

    fun limparCarrinho() {
        viewModelScope.launch {
            cartItemDao.limpar()
        }
    }
}

// --- FACTORY PARA CRIAR O VIEWMODEL COM OS DAOs ---
class MeuViewModelFactory(
    private val db: AppDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MeuViewModel(
                userDao = db.userDao(),
                pratoDao = db.pratoDao(),
                vinhoDao = db.vinhoDao(),
                metodoPagamentoDao = db.metodoPagamentoDao(),
                cartItemDao = db.cartItemDao()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
