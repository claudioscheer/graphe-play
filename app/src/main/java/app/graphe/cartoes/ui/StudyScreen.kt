package app.graphe.cartoes.ui

import android.Manifest
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.view.HapticFeedbackConstants
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import app.graphe.cartoes.domain.Card
import app.graphe.cartoes.domain.Face
import app.graphe.cartoes.domain.Grade
import app.graphe.cartoes.reminder.scheduleStreakReminder
import app.graphe.cartoes.ui.theme.Cream
import app.graphe.cartoes.ui.theme.Fraunces
import app.graphe.cartoes.ui.theme.Ink
import app.graphe.cartoes.ui.theme.Mint
import app.graphe.cartoes.ui.theme.Mist
import app.graphe.cartoes.ui.theme.Navy
import app.graphe.cartoes.ui.theme.Outfit
import app.graphe.cartoes.ui.theme.Peach
import kotlin.math.abs

private val Arrive = CubicBezierEasing(0.16f, 1f, 0.3f, 1f)
private val CardShape = RoundedCornerShape(28.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyScreen(state: StudyUiState, viewModel: StudyViewModel, onHome: () -> Unit) {
    val reduceMotion = reduceMotion()
    val view = LocalView.current
    val flash = remember { Animatable(0f) }
    LaunchedEffect(state.feedbackNonce) {
        if (state.feedbackNonce == 0) {
            return@LaunchedEffect
        }
        val constant = if (Build.VERSION.SDK_INT >= 30) {
            if (state.feedback == Grade.CORRECT) {
                HapticFeedbackConstants.CONFIRM
            } else {
                HapticFeedbackConstants.REJECT
            }
        } else {
            HapticFeedbackConstants.CONTEXT_CLICK
        }
        view.performHapticFeedback(constant)
        if (!reduceMotion) {
            flash.snapTo(0.42f)
            flash.animateTo(0f, tween(durationMillis = 680, easing = Arrive))
        }
    }
    Box(Modifier.fillMaxSize().background(Ink)) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(420.dp)
                .align(Alignment.Center)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0xFF2A3658), Color.Transparent),
                    ),
                ),
        )
        val wash = if (state.feedback == Grade.WRONG) Peach else Mint
        Box(
            Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = flash.value }
                .background(wash.copy(alpha = 0.22f)),
        )
        if (!state.ready) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Cream)
        } else {
            Column(Modifier.fillMaxSize()) {
                StudyBar(
                    title = state.scopeLabel,
                    onHome = onHome,
                    onTitle = viewModel::openScope,
                )
                when {
                    state.error != null && state.card == null -> Message(state.error)
                    state.empty -> EmptyScope(onShowAll = viewModel::clearScope)
                    state.card != null -> {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 28.dp, bottom = 8.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            FlashCard(
                                card = state.card,
                                face = state.face,
                                reduceMotion = reduceMotion,
                                onFlip = viewModel::onFlip,
                                onVerse = viewModel::openVerse,
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
    }
    if (state.showScopeSheet) {
        ContextSheet(onDismiss = viewModel::dismissScope) {
            ScopeSheet(scope = state.scope, onChoose = viewModel::chooseScope)
        }
    }
    val verseBody = state.verseBody
    val verseReference = state.verseReference
    if (verseBody != null && verseReference != null) {
        VerseDialog(verseReference, verseBody, viewModel::dismissVerse)
    }
}

@Composable
private fun StudyBar(
    title: String,
    onHome: () -> Unit,
    onTitle: () -> Unit,
) {
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
        ContextTitle(title, onTitle, Modifier.weight(1f))
        Spacer(Modifier.width(48.dp))
    }
}

@Composable
internal fun FlashCard(
    card: Card,
    face: Face,
    reduceMotion: Boolean,
    onFlip: () -> Unit,
    onVerse: () -> Unit,
    showReference: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val rotation = remember { Animatable(0f) }
    val entrance = remember { Animatable(1f) }
    var lastCardId by remember { mutableStateOf<String?>(null) }
    val pressedSource = remember { MutableInteractionSource() }
    val pressed by pressedSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (pressed) 0.975f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "press",
    )
    LaunchedEffect(card.id, face, reduceMotion) {
        val target = if (face == Face.BACK) 180f else 0f
        val cardChanged = lastCardId != null && lastCardId != card.id
        lastCardId = card.id
        if (cardChanged) {
            rotation.snapTo(0f)
            if (reduceMotion) {
                entrance.snapTo(1f)
            } else {
                entrance.snapTo(0f)
                entrance.animateTo(1f, tween(durationMillis = 340, easing = Arrive))
            }
        } else if (reduceMotion) {
            rotation.snapTo(target)
        } else {
            rotation.animateTo(target, tween(durationMillis = 520, easing = Arrive))
        }
    }
    var showingBack by remember { mutableStateOf(face == Face.BACK) }
    LaunchedEffect(rotation) {
        snapshotFlow { rotation.value > 90f }
            .distinctUntilChanged()
            .collect { showingBack = it }
    }
    Box(
        modifier = modifier.graphicsLayer {
            val arrived = entrance.value
            translationY = (1f - arrived) * 36.dp.toPx()
            alpha = 0.55f + 0.45f * arrived
        },
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val angle = rotation.value
                    val edgeOn = angle > 90f
                    val faceScale = 0.94f + 0.06f * (abs(angle / 180f - 0.5f) * 2f)
                    rotationY = if (edgeOn) angle - 180f else angle
                    scaleX = faceScale * pressScale
                    scaleY = faceScale * pressScale
                    cameraDistance = 14f * density
                    shadowElevation = 18.dp.toPx()
                    shape = CardShape
                    clip = true
                }
                .background(if (showingBack) Navy else Cream)
                .clickable(
                    interactionSource = pressedSource,
                    indication = null,
                    onClick = onFlip,
                )
                .semantics {
                    contentDescription = if (showingBack) "Resposta" else "Pergunta. Toque para virar."
                }
                .padding(horizontal = 32.dp, vertical = 36.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (showingBack) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        card.answer,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                    )
                    if (showReference) {
                        Spacer(Modifier.height(28.dp))
                        Text(
                            card.reference,
                            color = Navy,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Cream)
                                .clickable(onClick = onVerse)
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                }
            } else {
                Text(
                    card.prompt,
                    color = Navy,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
internal fun GradeRow(onWrong: () -> Unit, onRight: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = 56.dp, end = 56.dp, top = 4.dp, bottom = 22.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GradeButton(color = Peach, description = "Errei", onClick = onWrong) {
            Icon(Icons.Filled.Close, contentDescription = null, tint = Navy, modifier = Modifier.size(36.dp))
        }
        GradeButton(color = Mint, description = "Acertei", onClick = onRight) {
            Icon(Icons.Filled.Check, contentDescription = null, tint = Navy, modifier = Modifier.size(36.dp))
        }
    }
}

@Composable
private fun GradeButton(
    color: Color,
    description: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.9f else 1f,
        animationSpec = tween(durationMillis = 110),
        label = "grade-press",
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    shadowElevation = 8.dp.toPx()
                    shape = CircleShape
                    clip = true
                }
                .background(color)
                .clickable(interactionSource = interaction, indication = null, onClick = onClick)
                .semantics { contentDescription = description },
            contentAlignment = Alignment.Center,
        ) {
            icon()
        }
        Spacer(Modifier.height(8.dp))
        Text(description, color = Mist, fontFamily = Outfit, fontWeight = FontWeight.Medium, fontSize = 13.sp)
    }
}

@Composable
private fun EmptyScope(onShowAll: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Ainda não há cartas neste assunto.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = Cream,
        )
        TextButton(onClick = onShowAll) {
            Text("Ver toda a Bíblia", color = Cream)
        }
    }
}

@Composable
private fun Message(text: String) {
    Box(Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
        Text(text, textAlign = TextAlign.Center, color = Cream, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
internal fun reduceMotion(): Boolean {
    val context = LocalContext.current
    return remember {
        Settings.Global.getFloat(
            context.contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE,
            1f,
        ) == 0f
    }
}

