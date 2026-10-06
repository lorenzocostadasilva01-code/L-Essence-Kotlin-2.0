# ♢ L'Essence - Restaurant & Cave Exclusive
> **Aplicativo Android de Alta Gastronomia e Vinhos Finos em Jetpack Compose & Kotlin 2.0**

---

## 📋 Sobre o Projeto

O **L'Essence** é um aplicativo móvel de altíssimo padrão para um restaurante contemporâneo e adega exclusiva. Oferece um menu refinado de pratos autorais gourmets e uma carta de vinhos finos selecionados internacionalmente.

Desenvolvido em **Jetpack Compose** utilizando a arquitetura **MVVM (Model-View-ViewModel)** com **Jetpack Navigation**, o app proporciona uma experiência fluida, sofisticada e altamente intuitiva.

---

## 🚀 Como Executar o Projeto

1. **Pré-requisitos**:
   - Android Studio (Ladybug / Jellyfish ou superior com Kotlin 2.0)
   - JDK 11 ou superior
   - Emulador Android (API 24+) ou Dispositivo Físico com depuração USB.

2. **Passos**:
   - Abra a pasta do projeto no Android Studio.
   - Sincronize o Gradle (`Gradle Sync`).
   - Execute o aplicativo (`Shift + F10`).

---

## ✅ Checklist de Requisitos Cumpridos

- [x] **Repositório GitHub & README**: Projeto estruturado com documentação completa.
- [x] **Documentação das Decisões**: Arquitetura MVVM, gerenciamento de estado unificado e navegação reativa.
- [x] **No mínimo 7 Telas Navegáveis**:
  1. `LandPageScreen` - Boas-vindas ao L'Essence Restaurante & Adega.
  2. `ListaPratosScreen` - Menu de Pratos Gourmets em `LazyColumn`.
  3. `DetalhePratoScreen` - Ficha do Prato com sugestão de harmonização com vinho.
  4. `ListaVinhosScreen` - Carta de Vinhos Finos em `LazyColumn`.
  5. `DetalheVinhoScreen` - Ficha do Vinho com notas de degustação e reserva.
  6. `CarrinhoScreen` - Seu Pedido reativo com cálculo dinâmico do valor total.
  7. `PagamentoScreen` - Checkout de pagamento (Visa, Mastercard, Pix).
  8. `PerfilScreen` - Perfil Gourmet do usuário com histórico e reservas.
- [x] **BottomNavigation Funcional**: Barra inferior fixa com botões circulares dourados navegando entre todas as áreas principais.
- [x] **2 Data Classes Distintas**:
  - `Prato`: Representa pratos gourmets (`nome`, `chef`, `categoria`, `precoBase`, `ingredientes`, `harmonizacaoVinho`, `avaliacao`, `isFavorito`).
  - `Vinho`: Representa rótulos finos (`rotulo`, `vinicola`, `safra`, `paisOrigem`, `tipo`, `precoGarrafa`, `notasDegustacao`, `isReservado`).
- [x] **2 Telas de Lista (`LazyColumn` + `Card`)**:
  - `ListaPratosScreen` para a classe `Prato`.
  - `ListaVinhosScreen` para a classe `Vinho`.
- [x] **Adicionar Item Novo pela UI**:
  - `AlertDialog` em ambas as listas permitindo incluir novos Pratos no Menu e novos Vinhos na Carta.
- [x] **Remover ou Marcar Item pela UI**:
  - Exclusão (ícone lixeira) e marcar como favorito/reservado diretamente nos Cards.
- [x] **2 Telas de Detalhes Dinâmicas**:
  - `DetalhePratoScreen` (recebe `pratoId` e exibe o prato certo).
  - `DetalheVinhoScreen` (recebe `vinhoId` e exibe o vinho certo).
- [x] **Detalhes Avançados (Diferencial da Aula)**:
  - Sugestão autoral de harmonização de cada prato com o vinho ideal do sommelier.
  - Notas de degustação detalhadas, teor alcoólico e reserva antecipada da garrafa na adega.
  - Controle de porções e recálculo dinâmico de preços.
- [x] **Objeto `Rotas`**:
  - Centralizado no arquivo `Rotas.kt` com nomeação clara de cada destino e construtores tipados.
- [x] **Botão Voltar Funcional**:
  - Presente em todas as telas de detalhe e pagamento chamando `navController.popBackStack()`.

---

## 🛠️ Tecnologias Utilizadas

- **Kotlin 2.0** & **Jetpack Compose** (Material 3)
- **Jetpack Navigation Compose** (`androidx.navigation:navigation-compose`)
- **State Management**: `ViewModel` & `mutableStateListOf`
- **Design System**: Paleta luxuosa Ivory, Gold & Midnight Charcoal (`#FFFFE4`, `#ECECE3`, `#D4AF37`, `#333333`)
