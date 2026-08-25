package com.educationforkids.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educationforkids.app.ui.theme.EducationForKidsTheme

private val Ink = Color(0xFF14213A)
private val Muted = Color(0xFF667184)
private val Green = Color(0xFF52C63B)
private val Blue = Color(0xFF31A4EE)
private val Purple = Color(0xFF8844DC)
private val DarkBlue = Color(0xFF183A5A)
private val Orange = Color(0xFFFFB625)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EducationForKidsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    EducationForKidsApp()
                }
            }
        }
    }
}

private enum class AppPage { HOME, STORY, LESSONS, SUBJECT, CHALLENGES, STORE, PROFILE, QUIZ }

private data class Subject(
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val topics: List<String>,
    val firstLessons: List<String>
)

private data class Lesson(val title: String, val description: String, val progress: Float)

private data class QuizQuestion(
    val prompt: String,
    val instruction: String,
    val answers: List<String>,
    val correct: Int,
    val explanation: String
)

private val subjects = listOf(
    Subject("Português", Icons.Filled.MenuBook, Green, listOf("Alfabetização", "Gramática", "Leitura e escrita"), listOf("Vogais e letras", "Sílabas", "Formação de palavras", "Leitura e compreensão")),
    Subject("Matemática", Icons.Filled.Calculate, Purple, listOf("Números", "Operações", "Raciocínio"), listOf("Números e contagem", "Adição", "Subtração", "Formas geométricas")),
    Subject("Ciências", Icons.Filled.Science, Orange, listOf("Seres vivos", "Natureza", "Descobertas"), listOf("Seres vivos", "Corpo humano", "Plantas", "Animais")),
    Subject("História", Icons.Filled.HistoryEdu, Green, listOf("Nossa história", "Brasil", "Sociedade"), listOf("Minha história", "Família e comunidade", "Objetos antigos", "Passagem do tempo")),
    Subject("Geografia", Icons.Filled.Public, Color(0xFFEF6A6A), listOf("Espaços", "Natureza", "Mundo"), listOf("Minha casa", "Minha escola", "Bairro", "Paisagens")),
    Subject("Inglês", Icons.Filled.ChatBubble, Orange, listOf("Vocabulário", "Cotidiano", "Conversação"), listOf("Saudações", "Cores", "Números", "Animais")),
    Subject("Artes", Icons.Filled.Palette, Green, listOf("Fundamentos", "Técnicas", "Criação"), listOf("Cores", "Formas", "Desenho", "Texturas")),
    Subject("Música", Icons.Filled.MusicNote, Purple, listOf("Sons e ritmo", "Linguagem musical", "Composição"), listOf("Sons do cotidiano", "Ritmo", "Voz", "Instrumentos")),
    Subject("Leitura", Icons.Filled.Book, Orange, listOf("Primeiros textos", "Gêneros", "Interpretação"), listOf("Palavras e imagens", "Frases curtas", "Personagens", "Sequência da história"))
)

private val portugueseQuiz = listOf(
    QuizQuestion("Qual palavra começa com a letra B?", "Toque na resposta correta.", listOf("Casa", "Bola", "Dado"), 1, "Bola começa com a letra B."),
    QuizQuestion("Quantas sílabas tem a palavra boneca?", "Separe a palavra em partes ao falar.", listOf("2", "3", "4"), 1, "Bo-ne-ca possui três sílabas."),
    QuizQuestion("Qual é o plural de gato?", "Escolha a palavra que indica mais de um gato.", listOf("Gatos", "Gato", "Gatinho"), 0, "Gatos é o plural de gato."),
    QuizQuestion("Qual frase é uma pergunta?", "Observe o sinal no final da frase.", listOf("O livro é azul.", "Onde está meu livro?", "Guarde o livro!"), 1, "Perguntas terminam com ponto de interrogação.")
)

@Composable
private fun EducationForKidsApp() {
    var page by rememberSaveable { mutableStateOf(AppPage.HOME) }
    var selectedSubject by remember { mutableStateOf(subjects.first()) }
    var selectedTopic by rememberSaveable { mutableIntStateOf(0) }

    val navPage = when (page) {
        AppPage.SUBJECT, AppPage.QUIZ -> AppPage.LESSONS
        else -> page
    }

    Box(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (page) {
                AppPage.HOME -> HomeScreen(
                    onStory = { page = AppPage.STORY },
                    onLessons = { selectedSubject = subjects.first(); selectedTopic = 0; page = AppPage.SUBJECT }
                )
                AppPage.STORY -> StoryScreen()
                AppPage.LESSONS -> SubjectsScreen { subject ->
                    selectedSubject = subject
                    selectedTopic = 0
                    page = AppPage.SUBJECT
                }
                AppPage.SUBJECT -> SubjectLessonsScreen(
                    subject = selectedSubject,
                    selectedTopic = selectedTopic,
                    onTopic = { selectedTopic = it },
                    onBack = { page = AppPage.LESSONS },
                    onLesson = { page = AppPage.QUIZ }
                )
                AppPage.QUIZ -> QuizScreen(onExit = { page = AppPage.SUBJECT })
                AppPage.CHALLENGES -> PlaceholderScreen(Icons.Filled.EmojiEvents, "Desafios", "Complete missões para ganhar novas recompensas.")
                AppPage.STORE -> PlaceholderScreen(Icons.Filled.Store, "Loja", "Novos itens educativos chegarão em breve.")
                AppPage.PROFILE -> ProfileScreen()
            }
        }
        if (page != AppPage.QUIZ) {
            AppBottomBar(
                selected = navPage,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) { destination ->
                page = destination
                if (destination == AppPage.LESSONS) selectedTopic = 0
            }
        }
    }
}

@Composable
private fun AppBottomBar(selected: AppPage, modifier: Modifier = Modifier, onSelect: (AppPage) -> Unit) {
    val items = listOf(
        Triple(AppPage.HOME, "Início", Icons.Filled.Home),
        Triple(AppPage.STORY, "História", Icons.Filled.Map),
        Triple(AppPage.LESSONS, "Lições", Icons.Filled.MenuBook),
        Triple(AppPage.CHALLENGES, "Desafios", Icons.Filled.EmojiEvents),
        Triple(AppPage.STORE, "Loja", Icons.Filled.Store),
        Triple(AppPage.PROFILE, "Perfil", Icons.Filled.Person)
    )
    Box(
        modifier = modifier.widthIn(max = 430.dp).fillMaxWidth().navigationBarsPadding()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().height(73.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = .96f)),
            border = BorderStroke(1.dp, Color(0xFFEEF0EF)),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Row(Modifier.fillMaxSize().padding(horizontal = 6.dp, vertical = 4.dp)) {
                items.forEach { (page, label, icon) ->
                    val active = selected == page
                    Box(
                        modifier = Modifier.weight(1f).height(61.dp).clip(RoundedCornerShape(17.dp))
                            .background(if (active) Color(0xFFF0FAED) else Color.Transparent)
                            .clickable { onSelect(page) },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            if (page == AppPage.PROFILE) {
                                Box(
                                    Modifier.size(27.dp).clip(CircleShape)
                                        .background(if (active) Green else Color.Transparent)
                                        .border(2.dp, if (active) Green else Color(0xFF747B86), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("PF", color = if (active) Color.White else Color(0xFF747B86), fontWeight = FontWeight.Black, fontSize = 10.sp)
                                }
                            } else {
                                Icon(icon, contentDescription = label, tint = if (active) Green else Color(0xFF747B86), modifier = Modifier.size(27.dp))
                            }
                            Spacer(Modifier.height(3.dp))
                            Text(label, color = if (active) Green else Color(0xFF747B86), fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeScreen(onStory: () -> Unit, onLessons: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(Color(0xFFFCFDFC))) {
        StatsBar()
        HomeHero()
        Button(
            onClick = onLessons,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(46.dp),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green)
        ) { Text("CONTINUAR LIÇÃO", fontWeight = FontWeight.Black, fontSize = 14.sp) }
        HomePath(onStory)
        PracticeSection()
        Spacer(Modifier.height(116.dp))
    }
}

@Composable
private fun StatsBar() {
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp).background(Color.White).padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MiniStat(Icons.Filled.LocalFireDepartment, "12", Color(0xFFFFAD16))
        MiniStat(Icons.Filled.Diamond, "480", Purple)
        MiniStat(Icons.Filled.Favorite, "5", Color(0xFFEF4556))
        Box {
            Icon(Icons.Filled.Notifications, contentDescription = "Notificações", tint = Color(0xFF626976), modifier = Modifier.size(23.dp))
            Box(Modifier.align(Alignment.TopEnd).size(9.dp).clip(CircleShape).background(Color(0xFFFF4B50)))
        }
    }
}

@Composable
private fun MiniStat(icon: ImageVector, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(23.dp))
        Spacer(Modifier.width(5.dp))
        Text(value, color = color, fontWeight = FontWeight.Black, fontSize = 15.sp)
    }
}

@Composable
private fun HomeHero() {
    Box(
        modifier = Modifier.fillMaxWidth().height(243.dp)
            .background(Brush.linearGradient(listOf(Color(0xFFFFFAF0), Color(0xFFF6F8ED))))
    ) {
        Image(
            painter = painterResource(R.drawable.mascot),
            contentDescription = "Mascote educativo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.align(Alignment.BottomEnd).offset(x = 25.dp, y = 32.dp).width(221.dp).height(234.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp, top = 18.dp).width(202.dp)) {
            Text("Olá, Paulo!", color = Ink, fontWeight = FontWeight.Black, fontSize = 25.sp)
            Text("Que tal continuar\nsua jornada hoje?", color = Muted, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, lineHeight = 18.sp, modifier = Modifier.padding(top = 6.dp))
            DailyGoalCard()
        }
    }
}

@Composable
private fun DailyGoalCard() {
    Card(
        modifier = Modifier.width(188.dp).padding(top = 8.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = .96f)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.School, contentDescription = null, tint = Color(0xFFEF6656), modifier = Modifier.size(19.dp))
                Text("Meta diária", color = Ink, fontWeight = FontWeight.Black, fontSize = 11.sp, modifier = Modifier.padding(start = 6.dp))
            }
            Text("Complete 3 lições\npara atingir sua meta!", color = Muted, fontSize = 9.sp, lineHeight = 12.sp, modifier = Modifier.padding(vertical = 5.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(progress = { .67f }, modifier = Modifier.weight(1f).height(8.dp).clip(CircleShape), color = Green, trackColor = Color(0xFFEDF0EE))
                Text("2/3", color = Green, fontWeight = FontWeight.Black, fontSize = 11.sp, modifier = Modifier.padding(start = 7.dp))
            }
        }
    }
}

@Composable
private fun HomePath(onStory: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 18.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(Modifier.padding(horizontal = 10.dp, vertical = 9.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Seu caminho", color = Ink, fontWeight = FontWeight.Black, fontSize = 17.sp)
                Button(onClick = onStory, contentPadding = ButtonDefaults.ContentPadding, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Muted), shape = RoundedCornerShape(0.dp), modifier = Modifier.height(32.dp)) {
                    Icon(Icons.Filled.Map, contentDescription = null, modifier = Modifier.size(14.dp))
                    Text(" VER MAPA", fontSize = 8.sp, fontWeight = FontWeight.Black)
                }
            }
            Box(Modifier.fillMaxWidth().padding(top = 10.dp)) {
                Canvas(Modifier.fillMaxWidth().height(70.dp)) {
                    drawLine(
                        color = Color(0xFFD9DDDF),
                        start = Offset(32.dp.toPx(), 32.dp.toPx()),
                        end = Offset(size.width - 32.dp.toPx(), 32.dp.toPx()),
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Round,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 6.dp.toPx()))
                    )
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Top) {
                    PathStep(R.drawable.ic_path_chat, "1. Saudações", Green, selected = false, completed = true)
                    PathStep(R.drawable.ic_path_headphones, "2. Escuta", Blue, selected = false, completed = true)
                    PathStep(R.drawable.ic_path_book, "3. Vocabulário", Purple, selected = true)
                    PathStep(R.drawable.ic_path_lock, "4. Frases", Color(0xFFB9BEC3), selected = false)
                    PathStep(R.drawable.ic_path_lock, "5. Conversa", Color(0xFFB9BEC3), selected = false)
                }
            }
        }
    }
}

@Composable
private fun PathStep(iconRes: Int, label: String, color: Color, selected: Boolean, completed: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(66.dp)) {
        Box(
            Modifier.size(if (selected) 61.dp else 52.dp)
                .then(if (selected) Modifier.shadow(7.dp, CircleShape) else Modifier)
                .background(Color.White, CircleShape)
                .border(2.dp, color.copy(alpha = .48f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier.size(if (selected) 53.dp else 44.dp).clip(CircleShape)
                    .background(Brush.linearGradient(listOf(color.copy(alpha = .82f), color))),
                contentAlignment = Alignment.Center
            ) {
                Icon(painterResource(iconRes), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(if (selected) 27.dp else 22.dp))
            }
            if (completed) {
                Box(
                    Modifier.align(Alignment.TopCenter).offset(y = (-7).dp).size(18.dp).clip(CircleShape)
                        .background(Color(0xFFFFB30C)).border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.LocalFireDepartment, null, tint = Color.White, modifier = Modifier.size(10.dp))
                }
            }
        }
        Text(label, color = Ink, fontWeight = if (selected) FontWeight.Black else FontWeight.SemiBold, fontSize = 8.sp, textAlign = TextAlign.Center, lineHeight = 9.sp, modifier = Modifier.padding(top = 5.dp))
        if (selected) Text("Atual", color = Purple, fontWeight = FontWeight.Black, fontSize = 9.sp, modifier = Modifier.padding(top = 1.dp))
        else Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = if (color == Color(0xFFB9BEC3)) Color.Transparent else Green, modifier = Modifier.padding(top = 3.dp).size(17.dp))
    }
}

@Composable
private fun PracticeSection() {
    Column(Modifier.fillMaxWidth().padding(horizontal = 14.dp)) {
        Text("Pratique mais", color = Ink, fontWeight = FontWeight.Black, fontSize = 17.sp, modifier = Modifier.padding(bottom = 10.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            PracticeCard("Desafios", "Complete desafios e ganhe recompensas!", R.drawable.ic_task_trophy, Orange, Color(0xFFFFF9E7), Modifier.weight(1f))
            PracticeCard("Revisão", "Reforce o que você já aprendeu.", R.drawable.ic_task_bolt, Blue, Color(0xFFEEF8FF), Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth().padding(top = 9.dp), horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            PracticeCard("Testes", "Teste seus conhecimentos.", R.drawable.ic_task_target, Green, Color(0xFFF4FCED), Modifier.weight(1f))
            PracticeCard("Histórias", "Leia e melhore seu entendimento.", R.drawable.ic_path_book, Purple, Color(0xFFFAF1FF), Modifier.weight(1f))
        }
    }
}

@Composable
private fun PracticeCard(title: String, subtitle: String, iconRes: Int, tone: Color, background: Color, modifier: Modifier) {
    Card(modifier = modifier.height(56.dp), shape = RoundedCornerShape(0.dp), colors = CardDefaults.cardColors(containerColor = background)) {
        Row(Modifier.fillMaxSize().padding(horizontal = 7.dp, vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(32.dp).background(tone), contentAlignment = Alignment.Center) {
                Icon(painterResource(iconRes), null, tint = Color.Unspecified, modifier = Modifier.size(22.dp))
            }
            Column(Modifier.padding(start = 6.dp, end = 4.dp).weight(1f)) {
                Text(title, color = Ink, fontWeight = FontWeight.Black, fontSize = 10.sp, maxLines = 1)
                Text(subtitle, color = Muted, fontSize = 7.sp, lineHeight = 8.sp, maxLines = 2)
            }
            Box(
                Modifier.size(20.dp).clip(CircleShape).background(tone),
                contentAlignment = Alignment.Center
            ) {
                Icon(painterResource(R.drawable.ic_task_arrow), null, tint = Color.Unspecified, modifier = Modifier.size(11.dp))
            }
        }
    }
}

@Composable
private fun StoryScreen() {
    var chapterVisible by rememberSaveable { mutableStateOf(true) }
    Box(modifier = Modifier.fillMaxSize().background(DarkBlue)) {
        Column(Modifier.fillMaxSize().padding(14.dp)) {
            PlayerBar()
        }
        if (chapterVisible) {
            ChapterOverlay(onClose = { chapterVisible = false })
        }
    }
}

@Composable
private fun PlayerBar() {
    Card(Modifier.fillMaxWidth().height(55.dp), shape = RoundedCornerShape(0.dp), colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = .96f)), elevation = CardDefaults.cardElevation(6.dp)) {
        Row(Modifier.fillMaxSize().padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(39.dp).clip(CircleShape).background(Color(0xFFEAF7E7)).border(2.dp, Green, CircleShape), contentAlignment = Alignment.Center) { Text("PF", color = Green, fontWeight = FontWeight.Black, fontSize = 13.sp) }
            Column(Modifier.padding(start = 7.dp).weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text("Paulo", color = Ink, fontWeight = FontWeight.Black, fontSize = 13.sp, lineHeight = 14.sp)
                Text("Nível 7", color = Muted, fontSize = 9.sp, lineHeight = 10.sp)
            }
            MiniMapStat(Icons.Filled.LocalFireDepartment, "12", Color(0xFFFFAD16))
            MiniMapStat(Icons.Filled.Diamond, "480", Purple)
            MiniMapStat(Icons.Filled.Favorite, "5", Color(0xFFEF4556))
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Filled.Settings, null, tint = Muted, modifier = Modifier.size(22.dp))
        }
    }
}

@Composable
private fun MiniMapStat(icon: ImageVector, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 2.dp)) {
        Icon(icon, null, tint = color, modifier = Modifier.size(18.dp))
        Text(value, color = color, fontWeight = FontWeight.Black, fontSize = 10.sp, modifier = Modifier.padding(start = 2.dp))
    }
}

@Composable
private fun ChapterOverlay(onClose: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(190.dp).padding(horizontal = 14.dp).offset(y = 82.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF13243A)),
        elevation = CardDefaults.cardElevation(14.dp)
    ) {
        Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Color(0xFF101B34), Color(0xFF244C58))))) {
            Column(Modifier.fillMaxWidth(.64f).padding(17.dp)) {
                Text("CAPÍTULO 1", color = Color(0xFF6BD044), fontWeight = FontWeight.Black, fontSize = 10.sp)
                Text("A Jornada do\nAprendizado", color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp, lineHeight = 22.sp, modifier = Modifier.padding(top = 6.dp))
                Text("Embarque em uma aventura para recuperar os livros perdidos do Conhecimento!", color = Color.White.copy(alpha = .9f), fontSize = 9.sp, lineHeight = 12.sp, modifier = Modifier.padding(top = 5.dp))
                Button(onClick = onClose, colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = .13f)), contentPadding = ButtonDefaults.ContentPadding, shape = RoundedCornerShape(0.dp), modifier = Modifier.padding(top = 5.dp).height(30.dp)) {
                    Icon(Icons.Filled.MenuBook, null, modifier = Modifier.size(14.dp))
                    Text(" VER HISTÓRIA", fontSize = 8.sp, fontWeight = FontWeight.Black)
                }
            }
            Image(painterResource(R.drawable.mascot), "Mascote do capítulo", contentScale = ContentScale.Fit, modifier = Modifier.align(Alignment.BottomEnd).offset(x = 18.dp, y = 22.dp).width(190.dp).height(190.dp))
        }
    }
}

@Composable
private fun SubjectsScreen(onSubject: (Subject) -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
        Text("ÁREA DE APRENDIZADO", color = Green, fontWeight = FontWeight.Black, fontSize = 10.sp, letterSpacing = 1.sp)
        Text("Escolha uma matéria", color = Ink, fontWeight = FontWeight.Black, fontSize = 29.sp, modifier = Modifier.padding(top = 6.dp))
        Text("Selecione uma área para acessar as atividades e lições.", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp, bottom = 18.dp))
        subjects.chunked(3).forEach { rowSubjects ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                rowSubjects.forEach { subject -> SubjectCard(subject, Modifier.weight(1f), onSubject) }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun SubjectCard(subject: Subject, modifier: Modifier, onSubject: (Subject) -> Unit) {
    Card(
        modifier = modifier.aspectRatio(1f).clickable { onSubject(subject) },
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = subject.color),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(subject.icon, null, tint = Color.White, modifier = Modifier.size(42.dp))
            Text(subject.name, color = Color.White, fontWeight = FontWeight.Black, fontSize = 10.sp, modifier = Modifier.padding(top = 7.dp))
        }
    }
}

@Composable
private fun SubjectLessonsScreen(subject: Subject, selectedTopic: Int, onTopic: (Int) -> Unit, onBack: () -> Unit, onLesson: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(Color.White)) {
        Box(Modifier.fillMaxWidth().height(128.dp).background(DarkBlue)) {
            Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = .12f)), shape = RoundedCornerShape(0.dp), modifier = Modifier.padding(14.dp).height(34.dp), contentPadding = ButtonDefaults.ContentPadding) {
                Icon(Icons.Filled.ArrowBack, null, modifier = Modifier.size(16.dp))
                Text(" Voltar às matérias", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
        Box(Modifier.fillMaxWidth().height(108.dp).background(Color.White)) {
            Row(Modifier.fillMaxWidth().offset(y = (-32).dp).padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Top) {
                subject.topics.forEachIndexed { index, topic ->
                    TopicCircle(index, topic, selectedTopic == index) { onTopic(index) }
                }
            }
        }
        Column(Modifier.padding(horizontal = 15.dp)) {
            lessonsFor(subject, selectedTopic).forEachIndexed { index, lesson ->
                LessonCard(subject, lesson, index + 1, onLesson)
                Spacer(Modifier.height(11.dp))
            }
            Text("Toque em uma lição para começar a praticar.", color = Muted, textAlign = TextAlign.Center, fontSize = 9.sp, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp))
        }
    }
}

@Composable
private fun TopicCircle(index: Int, label: String, selected: Boolean, onClick: () -> Unit) {
    val color = listOf(Green, Blue, Purple)[index]
    val icon = listOf(Icons.Filled.MenuBook, Icons.Filled.ChatBubble, Icons.Filled.Book)[index]
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(108.dp).clickable(onClick = onClick)) {
        Box(
            Modifier.size(if (selected) 64.dp else 56.dp).clip(CircleShape)
                .background(Brush.linearGradient(listOf(color.copy(alpha = .78f), color)))
                .border(3.dp, Color.White, CircleShape)
                .shadow(if (selected) 7.dp else 3.dp, CircleShape),
            contentAlignment = Alignment.Center
        ) { Icon(icon, null, tint = Color.White, modifier = Modifier.size(if (selected) 29.dp else 25.dp)) }
        Text(label, color = Ink, fontWeight = if (selected) FontWeight.Black else FontWeight.Bold, fontSize = 10.sp, textAlign = TextAlign.Center, lineHeight = 11.sp, modifier = Modifier.padding(top = if (selected) 6.dp else 3.dp))
        if (selected) Text("Atual", color = color, fontWeight = FontWeight.Black, fontSize = 9.sp, modifier = Modifier.padding(top = 0.dp))
    }
}

private fun lessonsFor(subject: Subject, topic: Int): List<Lesson> {
    val titles = if (topic == 0) subject.firstLessons else listOf(
        "${subject.topics[topic]}: introdução",
        "Exemplos de ${subject.topics[topic].lowercase()}",
        "Atividade guiada",
        "Revisão do tópico"
    )
    val progress = listOf(.70f, .45f, .20f, 0f)
    return titles.mapIndexed { index, title ->
        Lesson(title, "Aprenda ${title.lowercase()} com exemplos visuais e uma atividade curta.", progress[index])
    }
}

@Composable
private fun LessonCard(subject: Subject, lesson: Lesson, number: Int, onLesson: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onLesson),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Row(Modifier.fillMaxWidth().height(116.dp)) {
                LessonArtwork(subject, number, Modifier.weight(.35f).fillMaxHeight())
                Column(Modifier.weight(.65f).fillMaxHeight().padding(12.dp), verticalArrangement = Arrangement.Center) {
                    Text("LIÇÃO $number", color = subject.color, fontWeight = FontWeight.Black, fontSize = 9.sp, letterSpacing = .7.sp)
                    Text(lesson.title, color = Ink, fontWeight = FontWeight.Black, fontSize = 14.sp, lineHeight = 16.sp, modifier = Modifier.padding(top = 4.dp))
                    Text(lesson.description, color = Muted, fontSize = 9.sp, lineHeight = 12.sp, modifier = Modifier.padding(top = 4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 5.dp)) {
                        Text("INICIAR", color = subject.color, fontWeight = FontWeight.Black, fontSize = 9.sp)
                        Icon(Icons.Filled.PlayArrow, null, tint = subject.color, modifier = Modifier.size(14.dp))
                    }
                }
            }
            Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 9.dp), verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(progress = { lesson.progress }, modifier = Modifier.weight(1f).height(7.dp).clip(CircleShape), color = subject.color, trackColor = Color(0xFFEDF0EE))
                Text("${(lesson.progress * 100).toInt()}%", color = subject.color, fontWeight = FontWeight.Black, fontSize = 9.sp, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
private fun LessonArtwork(subject: Subject, number: Int, modifier: Modifier) {
    Box(modifier.background(Brush.linearGradient(listOf(subject.color.copy(alpha = .62f), subject.color)))) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(Color.White.copy(alpha = .18f), radius = size.minDimension * .32f, center = Offset(size.width * .25f, size.height * .25f))
            drawCircle(Color.White.copy(alpha = .12f), radius = size.minDimension * .40f, center = Offset(size.width * .88f, size.height * .85f))
            val bookWidth = size.width * .54f
            val bookHeight = size.height * .38f
            val left = (size.width - bookWidth) / 2
            val top = (size.height - bookHeight) / 2
            drawRoundRect(Color.White.copy(alpha = .94f), Offset(left, top), Size(bookWidth * .48f, bookHeight), CornerRadius(10f, 10f))
            drawRoundRect(Color(0xFFF4F1DF), Offset(left + bookWidth * .52f, top), Size(bookWidth * .48f, bookHeight), CornerRadius(10f, 10f))
            drawLine(Color(0xFF31516C).copy(alpha = .55f), Offset(size.width / 2, top + 5f), Offset(size.width / 2, top + bookHeight - 5f), strokeWidth = 3f, cap = StrokeCap.Round)
        }
        Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Color(0x59081B2C), Color.Transparent))))
        Text(number.toString(), color = Color.White.copy(alpha = .9f), fontWeight = FontWeight.Black, fontSize = 12.sp, modifier = Modifier.align(Alignment.BottomStart).padding(8.dp))
    }
}

@Composable
private fun QuizScreen(onExit: () -> Unit) {
    var index by rememberSaveable { mutableIntStateOf(0) }
    var selected by rememberSaveable { mutableIntStateOf(-1) }
    var checked by rememberSaveable { mutableStateOf(false) }
    var finished by rememberSaveable { mutableStateOf(false) }
    var correctCount by rememberSaveable { mutableIntStateOf(0) }

    if (finished) {
        Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(Icons.Filled.EmojiEvents, null, tint = Orange, modifier = Modifier.size(82.dp))
            Text("Lição concluída!", color = Ink, fontWeight = FontWeight.Black, fontSize = 27.sp, modifier = Modifier.padding(top = 16.dp))
            Text("Você acertou $correctCount de ${portugueseQuiz.size} perguntas.", color = Muted, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
            Button(onClick = onExit, modifier = Modifier.fillMaxWidth().padding(top = 26.dp).height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Green), shape = RoundedCornerShape(0.dp)) { Text("VOLTAR ÀS LIÇÕES", fontWeight = FontWeight.Black) }
        }
        return
    }

    val question = portugueseQuiz[index]
    Column(Modifier.fillMaxSize().padding(18.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onExit) { Icon(Icons.Filled.Close, "Sair", tint = Muted) }
            LinearProgressIndicator(progress = { (index + 1f) / portugueseQuiz.size }, modifier = Modifier.weight(1f).height(9.dp).clip(CircleShape), color = Green, trackColor = Color(0xFFE7ECE8))
            Text("♥ 5", color = Color(0xFFEF4556), fontWeight = FontWeight.Black, fontSize = 13.sp, modifier = Modifier.padding(start = 12.dp))
        }
        Text("PORTUGUÊS · LIÇÃO ${index + 1}", color = Green, fontWeight = FontWeight.Black, fontSize = 10.sp, letterSpacing = 1.sp, modifier = Modifier.padding(top = 28.dp))
        Text(question.prompt, color = Ink, fontWeight = FontWeight.Black, fontSize = 25.sp, lineHeight = 29.sp, modifier = Modifier.padding(top = 8.dp))
        Text(question.instruction, color = Muted, fontSize = 13.sp, modifier = Modifier.padding(top = 7.dp, bottom = 22.dp))
        question.answers.forEachIndexed { answerIndex, answer ->
            val chosen = selected == answerIndex
            val answerColor = when {
                checked && answerIndex == question.correct -> Green
                checked && chosen -> Color(0xFFE45B5B)
                chosen -> Blue
                else -> Color(0xFFDDE3E0)
            }
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 11.dp).clickable(enabled = !checked) { selected = answerIndex },
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(containerColor = if (chosen || (checked && answerIndex == question.correct)) answerColor.copy(alpha = .10f) else Color.White),
                border = androidx.compose.foundation.BorderStroke(if (chosen || (checked && answerIndex == question.correct)) 2.dp else 1.dp, answerColor)
            ) { Text(answer, color = Ink, fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.fillMaxWidth().padding(17.dp)) }
        }
        if (checked) {
            Card(colors = CardDefaults.cardColors(containerColor = if (selected == question.correct) Color(0xFFECF9E9) else Color(0xFFFFEEEE)), shape = RoundedCornerShape(0.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(13.dp)) {
                    Text(if (selected == question.correct) "Muito bem!" else "Vamos aprender!", color = if (selected == question.correct) Green else Color(0xFFE45B5B), fontWeight = FontWeight.Black)
                    Text(question.explanation, color = Muted, fontSize = 11.sp, modifier = Modifier.padding(top = 3.dp))
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Button(
            onClick = {
                if (!checked) {
                    checked = true
                    if (selected == question.correct) correctCount++
                } else if (index == portugueseQuiz.lastIndex) {
                    finished = true
                } else {
                    index++
                    selected = -1
                    checked = false
                }
            },
            enabled = selected >= 0,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green),
            shape = RoundedCornerShape(0.dp)
        ) { Text(if (checked) "CONTINUAR" else "VERIFICAR", fontWeight = FontWeight.Black) }
    }
}

@Composable
private fun PlaceholderScreen(icon: ImageVector, title: String, subtitle: String) {
    Column(Modifier.fillMaxSize().padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Box(Modifier.size(112.dp).clip(CircleShape).background(Color(0xFFF0FAED)), contentAlignment = Alignment.Center) { Icon(icon, null, tint = Green, modifier = Modifier.size(58.dp)) }
        Text(title, color = Ink, fontWeight = FontWeight.Black, fontSize = 27.sp, modifier = Modifier.padding(top = 18.dp))
        Text(subtitle, color = Muted, textAlign = TextAlign.Center, fontSize = 13.sp, modifier = Modifier.padding(top = 7.dp))
    }
}

@Composable
private fun ProfileScreen() {
    Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.padding(top = 38.dp).size(100.dp).clip(CircleShape).background(Green), contentAlignment = Alignment.Center) { Text("PF", color = Color.White, fontWeight = FontWeight.Black, fontSize = 32.sp) }
        Text("Paulo", color = Ink, fontWeight = FontWeight.Black, fontSize = 26.sp, modifier = Modifier.padding(top = 14.dp))
        Text("Nível 7 · 480 XP", color = Muted, fontSize = 13.sp)
        Card(Modifier.fillMaxWidth().padding(top = 28.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF6FAF5)), shape = RoundedCornerShape(0.dp)) {
            Column(Modifier.padding(18.dp)) {
                Text("Seu progresso", color = Ink, fontWeight = FontWeight.Black, fontSize = 17.sp)
                Text("12 dias de sequência", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
                LinearProgressIndicator(progress = { .67f }, color = Green, trackColor = Color(0xFFE3EAE2), modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(9.dp).clip(CircleShape))
            }
        }
    }
}
