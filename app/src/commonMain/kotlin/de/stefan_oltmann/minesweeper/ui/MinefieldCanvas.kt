/*
 * 💣 Minesweeper 💣
 * Copyright (C) 2025 Stefan Oltmann
 * https://github.com/StefanOltmann/mine-sweeper
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.stefan_oltmann.minesweeper.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stefan_oltmann.minesweeper.model.CellType
import de.stefan_oltmann.minesweeper.model.GameState

@Composable
fun MinefieldCanvas(
    gameState: GameState,
    cellSize: Float,
    cellSizeWithDensity: Size,
    redrawState: MutableState<Int>,
    textMeasurer: TextMeasurer
) {

    Canvas(
        modifier = Modifier
            .size(
                width = (gameState.minefield.width * cellSize).dp,
                height = (gameState.minefield.height * cellSize).dp
            )
            .background(colorMapBackground)
            .border(1.dp, colorMapBorder, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->

                        gameState.hit(
                            x = (offset.x / cellSizeWithDensity.width).toInt(),
                            y = (offset.y / cellSizeWithDensity.height).toInt()
                        )

                        redrawState.value += 1
                    },
                    onLongPress = { offset ->

                        gameState.flag(
                            x = (offset.x / cellSizeWithDensity.width).toInt(),
                            y = (offset.y / cellSizeWithDensity.height).toInt()
                        )

                        redrawState.value += 1
                    }
                )
            }
    ) {

        /*
         * Force redraw if state changes.
         *
         * FIXME This is a hack
         */
        redrawState.value

        repeat(gameState.minefield.width) { x ->
            repeat(gameState.minefield.height) { y ->

                val offset = Offset(x * cellSizeWithDensity.width, y * cellSizeWithDensity.height)

                if (gameState.minefield.isRevealed(x, y)) {

                    val cellType = gameState.minefield.getCellType(x, y)

                    drawRevealedCell(
                        cellType = cellType,
                        textMeasurer = textMeasurer,
                        offset = offset,
                        cellSizeWithDensity = cellSizeWithDensity
                    )

                } else {

                    drawRoundRect(
                        color = Color.LightGray,
                        topLeft = offset,
                        size = cellSizeWithDensity,
                        cornerRadius = CornerRadius(10f),
                        style = Fill
                    )

                    if (gameState.minefield.isFlagged(x, y)) {

                        drawFlag(
                            topLeft = offset,
                            size = cellSizeWithDensity
                        )
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawRevealedCell(
    cellType: CellType,
    textMeasurer: TextMeasurer,
    offset: Offset,
    cellSizeWithDensity: Size,
) {

    drawRoundRect(
        color = Color.LightGray,
        topLeft = offset,
        size = cellSizeWithDensity,
        cornerRadius = CornerRadius(10f),
        style = Stroke()
    )

    when (cellType) {

        CellType.MINE ->
            drawCircle(
                color = Color.Red,
                radius = cellSizeWithDensity.width / 3,
                center = Offset(
                    offset.x + cellSizeWithDensity.width / 2,
                    offset.y + cellSizeWithDensity.height / 2
                )
            )

        CellType.ONE ->
            drawNumber(
                textMeasurer = textMeasurer,
                number = 1,
                color = colorOneAdjacentMine,
                topLeft = offset,
                size = cellSizeWithDensity
            )

        CellType.TWO ->
            drawNumber(
                textMeasurer = textMeasurer,
                number = 2,
                color = colorTwoAdjacentMines,
                topLeft = offset,
                size = cellSizeWithDensity
            )

        CellType.THREE ->
            drawNumber(
                textMeasurer = textMeasurer,
                number = 3,
                color = colorThreeAdjacentMines,
                topLeft = offset,
                size = cellSizeWithDensity
            )

        CellType.FOUR ->
            drawNumber(
                textMeasurer = textMeasurer,
                number = 4,
                color = colorFourAdjacentMines,
                topLeft = offset,
                size = cellSizeWithDensity
            )

        CellType.FIVE ->
            drawNumber(
                textMeasurer = textMeasurer,
                number = 5,
                color = colorFiveAdjacentMines,
                topLeft = offset,
                size = cellSizeWithDensity
            )

        CellType.SIX ->
            drawNumber(
                textMeasurer = textMeasurer,
                number = 6,
                color = colorSixAdjacentMines,
                topLeft = offset,
                size = cellSizeWithDensity
            )

        CellType.SEVEN ->
            drawNumber(
                textMeasurer = textMeasurer,
                number = 7,
                color = colorSevenAdjacentMines,
                topLeft = offset,
                size = cellSizeWithDensity
            )

        CellType.EIGHT ->
            drawNumber(
                textMeasurer = textMeasurer,
                number = 8,
                color = colorEightAdjacentMines,
                topLeft = offset,
                size = cellSizeWithDensity
            )

        CellType.EMPTY -> Unit
    }
}

private fun DrawScope.drawNumber(
    textMeasurer: TextMeasurer,
    number: Number,
    color: Color,
    topLeft: Offset,
    size: Size,
) {

    val text = number.toString()

    val style = TextStyle.Default.copy(
        color = color,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )

    val textLayout = textMeasurer.measure(text, style)

    val centeredOffset = Offset(
        topLeft.x + (size.width - textLayout.size.width) / 2,
        topLeft.y + (size.height - textLayout.size.height) / 2
    )

    drawText(
        textMeasurer = textMeasurer,
        text = text,
        style = style,
        topLeft = centeredOffset,
        size = size
    )
}

private fun DrawScope.drawFlag(
    topLeft: Offset,
    size: Size,
) {

    val poleHeight = size.height * 0.5f
    val poleWidth = size.width * 0.05f
    val flagWidth = size.width * 0.3f
    val flagHeight = size.height * 0.3f

    /* Calculate the centered position */
    val centerX = topLeft.x + size.width / 2
    val centerY = topLeft.y + size.height / 2

    /* Adjust so that the pole starts at the bottom center */
    val poleStart = Offset(centerX, centerY - poleHeight / 2)
    val poleEnd = Offset(centerX, centerY + poleHeight / 2)

    /* Draw the flagpole */
    drawLine(
        color = Color.Black,
        start = poleStart,
        end = poleEnd,
        strokeWidth = poleWidth
    )

    /* Define the flag shape (triangle) relative to the top of the pole */
    val flagPath = Path().apply {

        /* Start the flag slightly to the right of the pole */
        val flagStartX = poleStart.x + poleWidth / 2

        /* Top of the pole, slightly right */
        moveTo(flagStartX, poleStart.y)

        /* Flag tip */
        lineTo(flagStartX + flagWidth, poleStart.y + flagHeight / 2)

        /* Bottom of the flag */
        lineTo(flagStartX, poleStart.y + flagHeight)

        close()
    }

    /* Draw the flag */
    drawPath(
        path = flagPath,
        color = Color.Red
    )
}