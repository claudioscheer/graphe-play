package app.graphe.cartoes.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.graphe.cartoes.domain.Grade
import app.graphe.cartoes.ui.theme.Cream
import app.graphe.cartoes.ui.theme.Fraunces
import app.graphe.cartoes.ui.theme.Navy
import app.graphe.cartoes.ui.theme.Ink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemorizeScreen(viewModel: MemorizeViewModel, onHome: () -> Unit) {
    val state by viewModel.ui.collectAsStateWithLifecycle()
    val reduceMotion = reduceMotion()
    BackHandler(enabled = !state.showScope) { onHome() }
    Box(Modifier.fillMaxSize().background(Ink)) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(420.dp)
                .align(Alignment.Center)
                .background(Brush.radialGradient(listOf(Color(0xFF2A3658), Color.Transparent))),
        )
        Column(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(start = 4.dp, end = 4.dp, top = 22.dp, bottom = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onHome) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Início", tint = Cream)
                }
                ContextTitle(state.scopeLabel, viewModel::openScope, Modifier.weight(1f))
                Spacer(Modifier.width(48.dp))
            }
            when {
                state.message != null && state.card == null -> {
                    val message = state.message ?: return@Column
                    Box(Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                        Text(message, color = Cream, textAlign = TextAlign.Center)
                    }
                }
                state.card != null -> {
                    val card = state.card ?: return@Column
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        FlashCard(
                            card = card,
                            face = state.face,
                            reduceMotion = reduceMotion,
                            onFlip = viewModel::onFlip,
                            onVerse = viewModel::openVerse,
                            showReference = false,
                            autoSizeAnswer = true,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                    GradeRow(
                        onWrong = { viewModel.onGrade(Grade.WRONG) },
                        onRight = { viewModel.onGrade(Grade.CORRECT) },
                    )
                }
            }
        }
    }
    if (state.showScope) {
        ContextSheet(onDismiss = viewModel::dismissScope) {
            ScopeSheet(
                scope = state.scope,
                onChoose = viewModel::chooseScope,
                modifier = Modifier.fillMaxSize(),
                showHistory = false,
            )
        }
    }
    if (state.showScope && state.scope.chapter != null && state.verseCount != null) {
        VerseSetDialog(state, viewModel)
    }
    val verseBody = state.verseBody
    val verseReference = state.verseReference
    if (verseBody != null && verseReference != null) {
        VerseDialog(verseReference, verseBody, viewModel::dismissVerse)
    }
}

@Composable
private fun VerseSetDialog(state: MemorizeUiState, viewModel: MemorizeViewModel) {
    Dialog(onDismissRequest = viewModel::dismissVerseSelection) {
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Conjunto de versículos",
                color = Cream,
                fontFamily = Fraunces,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(state.scopeLabel, color = Cream.copy(alpha = 0.75f), textAlign = TextAlign.Center)
            Spacer(Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = { viewModel.moveStart(-1) }) { Text("−", color = Cream, fontSize = 28.sp) }
                Text("${state.verseStart}", color = Cream, fontFamily = Fraunces, fontSize = 28.sp)
                TextButton(onClick = { viewModel.moveStart(1) }) { Text("+", color = Cream, fontSize = 28.sp) }
                Text("até", color = Cream, modifier = Modifier.padding(horizontal = 8.dp))
                TextButton(onClick = { viewModel.moveEnd(-1) }) { Text("−", color = Cream, fontSize = 28.sp) }
                Text("${state.verseEnd}", color = Cream, fontFamily = Fraunces, fontSize = 28.sp)
                TextButton(onClick = { viewModel.moveEnd(1) }) { Text("+", color = Cream, fontSize = 28.sp) }
            }
            Spacer(Modifier.height(24.dp))
            Text(
                "Usar estes versículos",
                color = Navy,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(Cream)
                    .clickable(onClick = viewModel::useVerseSet)
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                fontFamily = Fraunces,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
            )
        }
    }
}
