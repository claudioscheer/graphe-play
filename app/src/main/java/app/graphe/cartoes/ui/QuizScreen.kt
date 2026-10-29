package app.graphe.cartoes.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.graphe.cartoes.ui.theme.Cream
import kotlin.math.roundToInt
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import app.graphe.cartoes.ui.theme.Fraunces
import app.graphe.cartoes.ui.theme.Ink
import app.graphe.cartoes.ui.theme.Mint
import app.graphe.cartoes.ui.theme.Mist
import app.graphe.cartoes.ui.theme.Navy
import app.graphe.cartoes.ui.theme.Peach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(viewModel: QuizViewModel, onHome: () -> Unit) {
    val state by viewModel.ui.collectAsStateWithLifecycle()
    BackHandler(enabled = state.questions.isEmpty() && !state.showScope && !state.finished) { onHome() }
    Column(Modifier.fillMaxSize().background(Ink)) {
        ScreenHeader("Quiz", onBack = {
            if (state.questions.isNotEmpty()) viewModel.backToSetup() else onHome()
        })
        when {
            state.finished -> QuizResult(state, viewModel::backToSetup)
            state.questions.isNotEmpty() -> QuizPlay(state, viewModel::choose, viewModel::continueQuiz)
            else -> QuizSetup(state, viewModel)
        }
    }
    if (state.showScope) {
        ContextSheet(onDismiss = viewModel::dismissScope) {
            ScopeSheet(scope = state.scope, onChoose = viewModel::chooseScope)
        }
    }
}

@Composable
private fun QuizSetup(state: QuizUiState, viewModel: QuizViewModel) {
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 12.dp),
        ) {
            Text("Contexto", color = Mist, style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(8.dp))
            Text(
                state.scopeLabel,
                color = Cream,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(Navy)
                    .clickable(onClick = viewModel::openScope)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.height(24.dp))
            Text("Quantas perguntas", color = Mist, style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(5, 10, 20).forEach { count ->
                    val selected = state.count == count
                    Text(
                        count.toString(),
                        color = if (selected) Navy else Cream,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selected) Cream else Navy)
                            .clickable { viewModel.setCount(count) }
                            .padding(horizontal = 22.dp, vertical = 12.dp),
                        fontFamily = Fraunces,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                    )
                }
            }
            state.message?.let {
                Spacer(Modifier.height(16.dp))
                Text(it, color = Peach, style = MaterialTheme.typography.bodyLarge)
            }
            if (state.history.isNotEmpty()) {
                Spacer(Modifier.height(32.dp))
                Text("Resultados", color = Mist, style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(8.dp))
                state.history.forEach { row ->
                    HistoryRow(row, onDelete = { viewModel.deleteHistory(row.id) })
                }
            }
        }
        BottomAction("Começar", enabled = true, onClick = viewModel::start)
    }
}

@Composable
private fun QuizPlay(
    state: QuizUiState,
    onChoose: (Int) -> Unit,
    onContinue: () -> Unit,
) {
    val question = state.questions[state.index]
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 8.dp),
        ) {
            Text(
                "${state.index + 1} de ${state.questions.size}",
                color = Mist,
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                question.prompt,
                color = Cream,
                fontFamily = Fraunces,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                lineHeight = 38.sp,
            )
            Spacer(Modifier.height(8.dp))
            Text(question.reference, color = Mist, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(24.dp))
            question.options.forEachIndexed { index, option ->
                OptionButton(index, option.text, option.correct, state.chosen, onChoose)
                Spacer(Modifier.height(10.dp))
            }
        }
        BottomAction("Continuar", enabled = state.chosen != null, onClick = onContinue)
    }
}

@Composable
private fun OptionButton(
    index: Int,
    text: String,
    correct: Boolean,
    chosen: Int?,
    onChoose: (Int) -> Unit,
) {
    val revealed = chosen != null
    val background = when {
        revealed && correct -> Mint
        revealed && index == chosen -> Peach
        else -> Navy
    }
    val content = if (background == Navy) Cream else Navy
    Text(
        text,
        color = content,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .border(1.dp, Cream.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
            .clickable(enabled = !revealed) { onChoose(index) }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun QuizResult(state: QuizUiState, onDone: () -> Unit) {
    val correct = state.marks.count { it.correct }
    val missed = state.questions.zip(state.marks).filter { !it.second.correct }
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 12.dp),
        ) {
            Text(
                "$correct de ${state.questions.size}",
                color = Cream,
                fontFamily = Fraunces,
                fontWeight = FontWeight.SemiBold,
                fontSize = 48.sp,
            )
            Text(
                if (correct == state.questions.size) "Tudo certo." else "Respostas para rever.",
                color = Mist,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.height(24.dp))
            missed.forEach { (question, mark) ->
                Column(Modifier.padding(bottom = 22.dp)) {
                    Text(
                        question.prompt,
                        color = Cream,
                        fontFamily = Fraunces,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text("Você: ${mark.chosenText}", color = Peach, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "Certa: ${question.options.first { it.correct }.text}",
                        color = Mint,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
        BottomAction("Concluir", enabled = true, onClick = onDone)
    }
}

@Composable
private fun BottomAction(label: String, enabled: Boolean, onClick: () -> Unit) {
    Text(
        label,
        color = if (enabled) Navy else Navy.copy(alpha = 0.45f),
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(if (enabled) Cream else Cream.copy(alpha = 0.35f))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = 18.dp),
        textAlign = TextAlign.Center,
        fontFamily = Fraunces,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
    )
}

@Composable
private fun HistoryRow(row: QuizHistoryRow, onDelete: () -> Unit) {
    val reveal = 96.dp
    val revealPx = with(LocalDensity.current) { reveal.toPx() }
    val offset = remember { Animatable(0f) }
    Box(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Box(
            Modifier
                .align(Alignment.CenterEnd)
                .width(reveal)
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFC23B3B))
                .clickable(onClick = onDelete),
            contentAlignment = Alignment.Center,
        ) {
            Text("Apagar", color = Color.White, fontWeight = FontWeight.SemiBold)
        }
        Row(
            Modifier
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .fillMaxWidth()
                .height(48.dp)
                .background(Ink)
                .pointerInput(revealPx) {
                    coroutineScope {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, delta ->
                                launch {
                                    offset.snapTo((offset.value + delta).coerceIn(-revealPx, 0f))
                                }
                            },
                            onDragEnd = {
                                launch {
                                    val open = offset.value < -revealPx * 0.4f
                                    offset.animateTo(if (open) -revealPx else 0f, tween(180))
                                }
                            },
                        )
                    }
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(row.label, color = Cream, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            Text(row.score, color = Cream, fontFamily = Fraunces, fontWeight = FontWeight.SemiBold)
        }
    }
}

