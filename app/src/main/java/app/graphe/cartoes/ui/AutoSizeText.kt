package app.graphe.cartoes.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.sp

/**
 * Draws [text] in the largest size up to [style]'s own size that fits the
 * space given to [modifier]. Long text shrinks; short text keeps the card size.
 * If even [minFontSize] cannot fit, the text scrolls.
 */
@Composable
internal fun AutoSizeText(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    minFontSize: TextUnit = 12.sp,
) {
    val measurer = rememberTextMeasurer()
    val scroll = rememberScrollState()
    BoxWithConstraints(modifier, contentAlignment = Alignment.Center) {
        val fontSize = remember(text, style, constraints) {
            fittedFontSize(measurer, text, style, constraints, minFontSize)
        }
        val fittedStyle = style.copy(
            fontSize = fontSize,
            lineHeight = scaledLineHeight(style, fontSize),
        )
        val fitsSpace = remember(text, fittedStyle, constraints) {
            fits(measurer, text, fittedStyle, constraints)
        }
        Text(
            text = text,
            color = color,
            textAlign = textAlign,
            style = fittedStyle,
            modifier = if (fitsSpace) {
                Modifier
            } else {
                Modifier.fillMaxWidth().verticalScroll(scroll)
            },
        )
    }
}

private fun fittedFontSize(
    measurer: TextMeasurer,
    text: String,
    style: TextStyle,
    constraints: Constraints,
    minFontSize: TextUnit,
): TextUnit {
    val base = style.fontSize
    if (!base.isSp || !constraints.hasBoundedWidth || !constraints.hasBoundedHeight) {
        return base
    }
    fun sizeStyle(size: Float): TextStyle = style.copy(
        fontSize = size.sp,
        lineHeight = scaledLineHeight(style, size.sp),
    )

    if (fits(measurer, text, sizeStyle(base.value), constraints)) {
        return base
    }
    var low = minOf(minFontSize.value, base.value)
    var high = base.value
    var best = low
    while (low <= high) {
        val mid = (low + high) / 2f
        if (fits(measurer, text, sizeStyle(mid), constraints)) {
            best = mid
            low = mid + 0.25f
        } else {
            high = mid - 0.25f
        }
    }
    return best.sp
}

private fun fits(
    measurer: TextMeasurer,
    text: String,
    style: TextStyle,
    constraints: Constraints,
): Boolean {
    if (!constraints.hasBoundedWidth || !constraints.hasBoundedHeight) {
        return true
    }
    val measured = measurer.measure(
        text = text,
        style = style,
        constraints = Constraints(maxWidth = constraints.maxWidth),
    )
    return measured.size.height <= constraints.maxHeight
}

private fun scaledLineHeight(style: TextStyle, fontSize: TextUnit): TextUnit {
    val base = style.fontSize
    val line = style.lineHeight
    if (!base.isSp || !line.isSpecified) {
        return line
    }
    return (fontSize.value * line.value / base.value).sp
}
