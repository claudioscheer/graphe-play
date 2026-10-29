package app.graphe.cartoes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.graphe.cartoes.ui.theme.Cream
import app.graphe.cartoes.ui.theme.Fraunces
import app.graphe.cartoes.ui.theme.Ink
import app.graphe.cartoes.ui.theme.Mist
import app.graphe.cartoes.ui.theme.Navy

@Composable
fun HomeScreen(
    onFlashcards: () -> Unit,
    onQuiz: () -> Unit,
    onMemorize: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(18.dp))
        Text(
            "Graphe",
            color = Cream,
            fontFamily = Fraunces,
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            lineHeight = 46.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(6.dp))
        Text(
            "Escolha o que você quer fazer",
            color = Mist,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(20.dp))
        HomeChoice("Flashcards", "Vire a carta e marque certo ou errado.", onFlashcards, Modifier.weight(1f))
        Spacer(Modifier.height(12.dp))
        HomeChoice("Quiz", "Responda um conjunto de perguntas.", onQuiz, Modifier.weight(1f))
        Spacer(Modifier.height(12.dp))
        HomeChoice("Memorizar", "Repita o versículo e confira o texto.", onMemorize, Modifier.weight(1f))
    }
}

@Composable
private fun HomeChoice(title: String, detail: String, onClick: () -> Unit, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Navy)
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            title,
            color = Cream,
            fontFamily = Fraunces,
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            lineHeight = 46.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            detail,
            color = Mist,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
        )
    }
}
