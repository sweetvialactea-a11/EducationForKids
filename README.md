# EducationForKids

Aplicativo educacional infantil nativo para Android, desenvolvido em Kotlin e Jetpack Compose. A interface reúne tarefas curtas, progresso, matérias, lições com perguntas e uma área de história.

## Arquitetura

- Interface 100% nativa em Jetpack Compose; não utiliza WebView.
- Navegação inferior fixa e consistente em todas as telas principais.
- Matérias em grade 3×3, tópicos circulares e quatro lições por tópico.
- Exercícios de Português em formato de pergunta e resposta.
- Estado da janela do capítulo em sobreposição, sem deslocar o mapa ao fechar.

## Gerar o APK

O workflow **Gerar APK Android** compila automaticamente cada pull request e cada atualização da branch `main`. O arquivo fica disponível nos artefatos da execução com o nome `EducationForKids-debug-apk`.

Para compilar localmente, abra o projeto no Android Studio com JDK 17 e execute a configuração `app`.
