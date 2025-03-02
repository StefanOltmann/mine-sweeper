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

package de.stefan_oltmann.minesweeper

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App() {

    val gameState = remember { GameState() }

    val redrawState = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text = "Minesweeper " + gameState.minefield.id)

        Spacer(modifier = Modifier.height(16.dp))

        val cellSize = 40f

        val textMeasurer = rememberTextMeasurer()

        val density = LocalDensity.current.density

        val cellSizeWithDensity = Size(
            width = cellSize * density,
            height = cellSize * density
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
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

                println("REDRAW")

                /* Force redraw if state changes. */
                redrawState.value

                repeat(gameState.minefield.width) { x ->
                    repeat(gameState.minefield.height) { y ->

                        val offset = Offset(x * cellSize * density, y * cellSize * density)

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
                                    textMeasurer = textMeasurer,
                                    topLeft = offset,
                                    size = cellSizeWithDensity
                                )
                            }
                        }
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
    textMeasurer: TextMeasurer,
    topLeft: Offset,
    size: Size,
) {

    val text = "⚑"

    val style = TextStyle.Default.copy(
        color = Color.Red,
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