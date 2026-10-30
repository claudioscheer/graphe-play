package app.graphe.cartoes.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.graphe.cartoes.R
import app.graphe.cartoes.domain.QuizTopic
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
    BackHandler(enabled = state.finished) { viewModel.backToSetup() }
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
    val mix = when {
        state.scope.history -> "História e geografia"
        state.count <= 5 && state.scope.isAll() ->
            "Versículo · História · Curiosidade · Assunto · Nomes e lugares"
        state.count <= 5 ->
            "Versículo · Curiosidade · Assunto · Nomes e lugares"
        state.scope.isAll() ->
            "Versículo · História · Curiosidade · Assunto · Nomes e lugares · Carta aleatória"
        else ->
            "Versículo · Curiosidade · Assunto · Nomes e lugares · Carta aleatória"
    }
    val mixNote = when {
        state.scope.history -> "Todas as perguntas são de história e geografia."
        state.count <= 5 -> "Uma pergunta de cada tipo disponível."
        else -> "1 em cada 5 é uma carta aleatória."
    }
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
            Spacer(Modifier.height(24.dp))
            Text("Mistura", color = Mist, style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(6.dp))
            Text(
                mix,
                color = Cream,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                mixNote,
                color = Mist,
                style = MaterialTheme.typography.labelLarge,
            )
            state.message?.let {
                Spacer(Modifier.height(16.dp))
                Text(it, color = Peach, style = MaterialTheme.typography.bodyLarge)
            }
            if (state.history.isNotEmpty()) {
                Spacer(Modifier.height(32.dp))
                Text("Resultados", color = Mist, style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(8.dp))
                state.history.forEach { row ->
                    key(row.id) {
                        HistoryRow(
                            row,
                            onOpen = { viewModel.openHistory(row.id) },
                            onDelete = { viewModel.deleteHistory(row.id) },
                        )
                    }
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
    if (correct == state.questions.size) {
        PerfectQuizResult(correct, state.questions.size, onDone)
        return
    }
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
                "Respostas para rever.",
                color = Mist,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.height(24.dp))
            missed.forEach { (question, mark) ->
                Column(Modifier.padding(bottom = 22.dp)) {
                    question.topic?.let { topic ->
                        Text(topic.label(), color = Mist, style = MaterialTheme.typography.labelLarge)
                        Spacer(Modifier.height(4.dp))
                    }
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
private fun PerfectQuizResult(correct: Int, total: Int, onDone: () -> Unit) {
    val entrance = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        entrance.animateTo(1f, tween(durationMillis = 700, easing = FastOutSlowInEasing))
    }
    Column(
        Modifier
            .fillMaxSize()
            .graphicsLayer {
                alpha = entrance.value
                translationY = (1f - entrance.value) * 20.dp.toPx()
            },
    ) {
        Column(
            Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp),
        ) {
            Row {
                Text(
                    correct.toString(),
                    color = Cream,
                    style = TextStyle(
                        fontFamily = Fraunces,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 208.sp,
                        lineHeight = 208.sp,
                        letterSpacing = (-0.04f).em,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.Both,
                        ),
                    ),
                    modifier = Modifier.alignByBaseline(),
                )
                Spacer(Modifier.width(14.dp))
                Text(
                    "de $total",
                    color = Mist,
                    fontSize = 27.sp,
                    lineHeight = 32.sp,
                    modifier = Modifier.alignByBaseline(),
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "Tudo certo.",
                color = Cream,
                fontFamily = Fraunces,
                fontWeight = FontWeight.SemiBold,
                fontSize = 38.sp,
                lineHeight = 44.sp,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Você acertou todas.",
                color = Mist,
                style = MaterialTheme.typography.bodyLarge,
            )
            PerfectTrophy(
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Cream.copy(alpha = 0.16f)),
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    if (correct == 1) "1 acerto" else "$correct acertos",
                    color = Mist.copy(alpha = 0.76f),
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    "0 para rever",
                    color = Mist.copy(alpha = 0.76f),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        BottomAction("Concluir", enabled = true, onClick = onDone)
    }
}

@Composable
private fun PerfectTrophy(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "perfect")
    val bob by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 6000
                0f at 0
                1f at 2520
                0.3f at 3720
                0f at 6000
            },
            repeatMode = RepeatMode.Restart,
        ),
        label = "bob",
    )
    val tiltProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 9000
                0f at 0
                0f at 7380
                1f at 9000
            },
            repeatMode = RepeatMode.Restart,
        ),
        label = "tilt",
    )
    val tilt = when {
        tiltProgress < 0.82f -> 0f
        tiltProgress < 0.86f -> -3f * ((tiltProgress - 0.82f) / 0.04f)
        tiltProgress < 0.90f -> -3f + 6f * ((tiltProgress - 0.86f) / 0.04f)
        tiltProgress < 0.94f -> 3f - 4.2f * ((tiltProgress - 0.90f) / 0.04f)
        tiltProgress < 0.97f -> -1.2f + 1.6f * ((tiltProgress - 0.94f) / 0.03f)
        else -> 0.4f * (1f - (tiltProgress - 0.97f) / 0.03f)
    }
    val sparkle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "sparkle",
    )

    BoxWithConstraints(modifier, contentAlignment = Alignment.Center) {
        val scale = minOf(maxWidth.value / 220f, maxHeight.value / 230f, 1f).coerceAtLeast(0.05f)
        Box(
            Modifier
                .size(220.dp * scale, 230.dp * scale)
                .graphicsLayer {
                    translationY = -7.dp.toPx() * bob * scale
                    rotationZ = tilt
                    transformOrigin = TransformOrigin(0.5f, 0.92f)
                },
        ) {
            Image(
                painter = painterResource(R.drawable.ic_trophy),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(156.dp * scale, 180.dp * scale),
            )
            Sparkle(
                progress = sparkle,
                phase = 0.05f,
                color = Peach,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 30.dp * scale, y = 24.dp * scale)
                    .size(11.dp * scale),
            )
            Sparkle(
                progress = sparkle,
                phase = 0.38f,
                color = Mint,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 176.dp * scale, y = 50.dp * scale)
                    .size(9.dp * scale),
            )
            Sparkle(
                progress = sparkle,
                phase = 0.63f,
                color = Cream,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 42.dp * scale, y = 190.dp * scale)
                    .size(7.dp * scale),
            )
            Sparkle(
                progress = sparkle,
                phase = 0.82f,
                color = Peach,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 176.dp * scale, y = 164.dp * scale)
                    .size(6.dp * scale),
            )
        }
    }
}

@Composable
private fun Sparkle(
    progress: Float,
    phase: Float,
    color: Color,
    modifier: Modifier = Modifier,
) {
    val local = (progress + phase) % 1f
    val wave = when {
        local < 0.45f -> local / 0.45f
        local < 0.62f -> 1f - (local - 0.45f) / 0.17f * 0.65f
        else -> 0.35f * (1f - (local - 0.62f) / 0.38f)
    }
    Icon(
        painter = painterResource(R.drawable.ic_spark),
        contentDescription = null,
        tint = color,
        modifier = modifier.graphicsLayer {
            alpha = 0.18f + 0.82f * wave
            scaleX = 0.65f + 0.45f * wave
            scaleY = 0.65f + 0.45f * wave
            rotationZ = 18f * wave
        },
    )
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

private fun QuizTopic.label(): String = when (this) {
    QuizTopic.VERSE -> "VERSÍCULO"
    QuizTopic.HISTORY -> "HISTÓRIA E GEOGRAFIA"
    QuizTopic.CURIOSITY -> "CURIOSIDADE"
    QuizTopic.SUBJECT -> "ASSUNTO"
    QuizTopic.NAME_PLACE -> "NOMES E LUGARES"
}

@Composable
private fun HistoryRow(row: QuizHistoryRow, onOpen: () -> Unit, onDelete: () -> Unit) {
    val reveal = 96.dp
    val gap = 20.dp
    val travelPx = with(LocalDensity.current) { (reveal + gap).toPx() }
    val offset = remember { Animatable(0f) }
    Box(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Box(
            Modifier
                .align(Alignment.CenterEnd)
                .padding(start = gap)
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
                .clickable(onClick = onOpen)
                .padding(end = 4.dp)
                .pointerInput(travelPx) {
                    coroutineScope {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, delta ->
                                launch {
                                    offset.snapTo((offset.value + delta).coerceIn(-travelPx, 0f))
                                }
                            },
                            onDragEnd = {
                                launch {
                                    val open = offset.value < -travelPx * 0.4f
                                    offset.animateTo(if (open) -travelPx else 0f, tween(180))
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

