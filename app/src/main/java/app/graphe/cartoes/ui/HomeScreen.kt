package app.graphe.cartoes.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
    var showAbout by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink),
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
        IconButton(
            onClick = { showAbout = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(top = 8.dp, end = 4.dp),
        ) {
            Icon(Icons.Filled.Info, contentDescription = "Sobre o Graphe", tint = Cream)
        }
    }
    if (showAbout) {
        AboutDialog(onDismiss = { showAbout = false })
    }
}

private const val GrapheSite = "https://graphe.com.br/"
private const val GrapheTelegram = "https://t.me/graphebiblesoftware/222"

@Composable
private fun AboutDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 12.dp),
        ) {
            Text(
                "Sobre",
                color = Cream,
                fontFamily = Fraunces,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Eu, Claudio Scheer, fiz este app como parte do Graphe, um programa para estudar a Bíblia.",
                color = Cream,
                fontSize = 16.sp,
                lineHeight = 22.sp,
            )
            Spacer(Modifier.height(18.dp))
            AboutLink(
                title = "Site do Graphe",
                detail = "graphe.com.br",
                onClick = { openLink(context, GrapheSite) },
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "Se você tiver alguma dúvida, entre no canal do Telegram e pergunte.",
                color = Mist,
                fontSize = 15.sp,
                lineHeight = 21.sp,
            )
            Spacer(Modifier.height(10.dp))
            AboutLink(
                title = "Canal no Telegram",
                detail = "Perguntar e comentar",
                onClick = { openLink(context, GrapheTelegram) },
            )
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.End),
            ) {
                Text("Fechar", color = Cream)
            }
        }
    }
}

@Composable
private fun AboutLink(title: String, detail: String, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Navy)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(title, color = Cream, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Text(detail, color = Mist, fontSize = 14.sp)
    }
}

private fun openLink(context: android.content.Context, url: String) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
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
