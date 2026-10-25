package com.example.l_essence_kotlin_20

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.l_essence_kotlin_20.ui.dao.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [User::class, Prato::class, Vinho::class, MetodoPagamento::class, CartItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pratoDao(): PratoDAO
    abstract fun vinhoDao(): VinhoDAO
    abstract fun metodoPagamentoDao(): MetodoPagamentoDAO
    abstract fun cartItemDao(): CartItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lessence_database.db"
                )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            popularDadosIniciais(getDatabase(context))
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun popularDadosIniciais(db: AppDatabase) {
            db.userDao().salvar(
                User(
                    uid = 1,
                    nome = "Gourmet Guest",
                    email = "guest@lessence.com",
                    telefone = "(11) 99999-8888",
                    endereco = "Alameda Gourmet, 1000 - Jardins"
                )
            )

            db.pratoDao().inserirTodos(
                listOf(
                    Prato(
                        id = 1,
                        nome = "Escargots à la Bourguignonne",
                        chef = "Chef Jean-Luc",
                        categoria = "Entradas",
                        precoBase = 95.0,
                        ingredientes = "Escargots de Bourgogne, manteiga de alho e ervas finas",
                        harmonizacaoVinho = "Chablis Premier Cru 2021",
                        tempoPreparoMinutos = 20,
                        avaliacao = 4.9,
                        descricao = "Escargots franceses gratinados na manteiga aromática com salsa e alho fresco.",
                        disponivel = true,
                        isFavorito = true
                    ),
                    Prato(
                        id = 2,
                        nome = "Filet Mignon au Poivre",
                        chef = "Chef Jean-Luc",
                        categoria = "Pratos Principais",
                        precoBase = 160.0,
                        ingredientes = "Medalhão de filet mignon, pimenta verde, conhaque e creme fresco",
                        harmonizacaoVinho = "Château Margaux 2018",
                        tempoPreparoMinutos = 30,
                        avaliacao = 5.0,
                        descricao = "Medalhão de filet mignon grelhado ao molho aveludado de pimentas verdes e conhaque.",
                        disponivel = true,
                        isFavorito = false
                    ),
                    Prato(
                        id = 3,
                        nome = "Risotto de Cogumelos Porcini & Trufas",
                        chef = "Chef Jean-Luc",
                        categoria = "Pratos Principais",
                        precoBase = 145.0,
                        ingredientes = "Arroz Arbóreo, cogumelos Porcini frescos, azeite de trufas brancas e grana padano",
                        harmonizacaoVinho = "Barolo DOCG 2016",
                        tempoPreparoMinutos = 25,
                        avaliacao = 4.9,
                        descricao = "Risotto cremoso com Porcini italianos finalizado com azeite de trufas brancas.",
                        disponivel = true,
                        isFavorito = true
                    ),
                    Prato(
                        id = 4,
                        nome = "Crème Brûlée de Baunilha Bourbon",
                        chef = "Chef Jean-Luc",
                        categoria = "Sobremesas",
                        precoBase = 55.0,
                        ingredientes = "Gemas, creme de leite fresco, baunilha de Madagascar, açúcar cristal",
                        harmonizacaoVinho = "Sauternes Premier Cru 2017",
                        tempoPreparoMinutos = 15,
                        avaliacao = 4.8,
                        descricao = "Clássica sobremesa francesa com casca crocante de açúcar caramelizado.",
                        disponivel = true,
                        isFavorito = false
                    )
                )
            )

            db.vinhoDao().inserir(
                Vinho(
                    id = 1,
                    rotulo = "Château Margaux Grand Cru",
                    vinicola = "Château Margaux",
                    safra = 2018,
                    paisOrigem = "França",
                    tipo = "Tinto Fino",
                    precoGarrafa = 850.0,
                    teorAlcoolico = "13.5%",
                    notasDegustacao = "Aromas intensos de cassis, violeta e sutil carvalho francês.",
                    avaliacao = 5.0,
                    descricao = "Rótulo lendário de Bordeaux, de elegância e persistência extraordinárias.",
                    isReservado = false,
                    isFavorito = true
                )
            )
            db.vinhoDao().inserir(
                Vinho(
                    id = 2,
                    rotulo = "Barolo DOCG Riserva",
                    vinicola = "Adega L'Essence",
                    safra = 2016,
                    paisOrigem = "Itália",
                    tipo = "Tinto Fino",
                    precoGarrafa = 420.0,
                    teorAlcoolico = "14.0%",
                    notasDegustacao = "Notas de cereja negra, couro, especiarias e taninos aveludados.",
                    avaliacao = 4.9,
                    descricao = "O rei dos vinhos piemonteses, estruturado e envelhecido em carvalho.",
                    isReservado = true,
                    isFavorito = true
                )
            )
            db.vinhoDao().inserir(
                Vinho(
                    id = 3,
                    rotulo = "Chablis Premier Cru",
                    vinicola = "Domaine Laroche",
                    safra = 2021,
                    paisOrigem = "França",
                    tipo = "Branco Seco",
                    precoGarrafa = 310.0,
                    teorAlcoolico = "12.5%",
                    notasDegustacao = "Mineralidade marcante, notas cítricas e acidez vibrante.",
                    avaliacao = 4.8,
                    descricao = "Chardonnay puro da Borgonha, ideal para frutos do mar e entradas refinadas.",
                    isReservado = false,
                    isFavorito = false
                )
            )

            db.metodoPagamentoDao().inserir(
                MetodoPagamento(
                    id = 1,
                    marca = "VISA",
                    numeroMascarado = "**** **** 4321",
                    nomeTitular = "GOURMET GUEST",
                    validade = "12/28",
                    isPadrao = true
                )
            )
        }
    }
}
