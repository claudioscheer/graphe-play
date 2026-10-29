package app.graphe.cartoes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.graphe.cartoes.domain.Canon
import app.graphe.cartoes.domain.Scope
import app.graphe.cartoes.domain.Testament
import app.graphe.cartoes.ui.theme.Cream
import app.graphe.cartoes.ui.theme.Mist
import app.graphe.cartoes.ui.theme.Navy

@Composable
private fun ScopeChip(selected: Boolean, onClick: () -> Unit, label: String) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Navy,
            labelColor = Cream,
            selectedContainerColor = Cream,
            selectedLabelColor = Navy,
        ),
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScopeSheet(
    scope: Scope,
    onChoose: (Scope) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expandedBook by rememberSaveable { mutableIntStateOf(-1) }
    Column(modifier.fillMaxSize().navigationBarsPadding()) {
        Text(
            "Contexto",
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 12.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
        )
        FlowRow(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ScopeChip(scope.isAll(), { onChoose(Scope.all) }, "Toda a Bíblia")
            ScopeChip(
                scope.testament == Testament.OLD && scope.bookNumber == null,
                { onChoose(Scope(testament = Testament.OLD)) },
                "Antigo Testamento",
            )
            ScopeChip(
                scope.testament == Testament.NEW && scope.bookNumber == null,
                { onChoose(Scope(testament = Testament.NEW)) },
                "Novo Testamento",
            )
            ScopeChip(scope.history, { onChoose(Scope.history) }, "História e geografia")
        }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            modifier = Modifier.fillMaxWidth().weight(1f),
        ) {
            items(Canon.books, key = { it.bookNumber }) { book ->
                val wholeBook = scope.bookNumber == book.bookNumber && scope.chapter == null
                val inBook = scope.bookNumber == book.bookNumber
                val expanded = expandedBook == book.bookNumber
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (inBook) Cream.copy(alpha = 0.12f) else Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        book.longName,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .clickable(role = Role.Button) {
                                onChoose(Scope(bookNumber = book.bookNumber))
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        color = if (wholeBook) Cream else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.88f),
                        fontWeight = if (inBook) FontWeight.SemiBold else FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    IconButton(
                        onClick = {
                            expandedBook = if (expanded) -1 else book.bookNumber
                        },
                    ) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Capítulos de ${book.longName}",
                            tint = Mist,
                        )
                    }
                }
                if (expanded) {
                    Column(Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 16.dp)) {
                        ScopeChip(
                            selected = wholeBook,
                            onClick = { onChoose(Scope(bookNumber = book.bookNumber)) },
                            label = "Livro todo",
                        )
                        ChapterGrid(
                            chapters = book.chapters,
                            selectedChapter = if (inBook) scope.chapter else null,
                            onChapter = { chapter ->
                                onChoose(Scope(bookNumber = book.bookNumber, chapter = chapter))
                            },
                        )
                    }
                }
            }
        }
    }
}

private const val ChapterColumns = 6
private val ChapterGap = 8.dp

@Composable
internal fun ChapterGrid(
    chapters: Int,
    selectedChapter: Int?,
    onChapter: (Int) -> Unit,
) {
    BoxWithConstraints(Modifier.fillMaxWidth().padding(top = 12.dp)) {
        val cell = (maxWidth - ChapterGap * (ChapterColumns - 1)) / ChapterColumns
        Column(verticalArrangement = Arrangement.spacedBy(ChapterGap)) {
            val rows = (chapters + ChapterColumns - 1) / ChapterColumns
            for (row in 0 until rows) {
                Row(horizontalArrangement = Arrangement.spacedBy(ChapterGap)) {
                    for (column in 0 until ChapterColumns) {
                        val chapter = row * ChapterColumns + column + 1
                        if (chapter > chapters) {
                            break
                        }
                        ChapterCell(
                            chapter = chapter,
                            selected = selectedChapter == chapter,
                            size = cell,
                            onClick = { onChapter(chapter) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChapterCell(
    chapter: Int,
    selected: Boolean,
    size: Dp,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(10.dp)
    Box(
        modifier = Modifier
            .size(size)
            .clip(shape)
            .background(if (selected) Cream else Navy)
            .border(1.dp, if (selected) Cream else Cream.copy(alpha = 0.4f), shape)
            .clickable(role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = chapter.toString(),
            color = if (selected) Navy else Cream,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false),
            ),
        )
    }
}
