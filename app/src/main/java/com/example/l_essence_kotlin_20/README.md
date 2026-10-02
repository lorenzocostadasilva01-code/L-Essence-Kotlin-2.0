1. Sincronizar o Gradle
   Antes de rodar, garanta que todas as dependências do projeto foram baixadas corretamente:

Clique no ícone de elefante com uma seta para baixo (Sync Project with Gradle Files) na barra de ferramentas superior.

Aguarde a mensagem Gradle sync finished no rodapé da tela.

2. Escolher onde executar o aplicativo
   Você pode rodar o aplicativo em um emulador (dispositivo virtual) ou em um celular físico.

Opção A: No Emulador (Android Virtual Device - AVD)
Abra o Device Manager no menu lateral direito (ou vá em Tools > Device Manager).

Se ainda não tiver um dispositivo configurado, clique em Create Device, escolha um modelo (ex.: Pixel 6) e uma versão do Android (API) para baixar.

Ligue o emulador clicando no ícone de Play ao lado do dispositivo criado.

Opção B: No Celular Físico
No seu celular Android, ative o Modo do Desenvolvedor (vá em Configurações > Sobre o telefone e toque 7 vezes em Número da versão).

Vá em Configurações > Opções do desenvolvedor e ative a Depuração USB.

Conecte o celular ao computador via cabo USB e aceite a permissão de depuração que aparecer na tela do celular.

3. Selecionar o Dispositivo e Executar
   Na barra de ferramentas superior do Android Studio, localize o menu suspenso de dispositivos (ao lado do botão verde de Play).

Selecione o dispositivo onde você deseja rodar (seu celular conectado ou o emulador).

Selecione o módulo do aplicativo (geralmente chamado de app).

Clique no botão Run (ícone de Play verde Shift + F10 no Windows/Linux ou Control + R no macOS).

O Android Studio irá compilar o código, gerar o arquivo .apk e instalá-lo automaticamente no dispositivo selecionado.