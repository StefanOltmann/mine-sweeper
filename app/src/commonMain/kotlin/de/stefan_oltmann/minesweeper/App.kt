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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
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

    val map = remember {

        Map(
            width = 10,
            height = 10,
            mineCount = 20,
            seed = 1
        )
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text = "Minesweeper " + map.getMapId())

        Spacer(modifier = Modifier.height(16.dp))

        val fieldSize = 40f

        val textMeasurer = rememberTextMeasurer()

        val density = LocalDensity.current.density

        val fieldSizeWithDensity = Size(
            width = fieldSize * density,
            height = fieldSize * density
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Canvas(
                modifier = Modifier
                    .size(
                        width = (map.width * fieldSize).dp,
                        height = (map.height * fieldSize).dp
                    )
                    .border(1.dp, Color.Black)
            ) {

                repeat(map.width) { x ->
                    repeat(map.height) { y ->

                        val offset = Offset(x * fieldSize * density, y * fieldSize * density)

                        drawRoundRect(
                            color = Color.LightGray,
                            topLeft = offset,
                            size = fieldSizeWithDensity,
                            cornerRadius = CornerRadius(10f),
                            style = Stroke()
                        )

                        val fieldType = map.get(x, y)

                        when (fieldType) {

                            FieldType.MINE ->
                                drawCircle(
                                    color = Color.Red,
                                    radius = fieldSize / 3,
                                    center = Offset(
                                        offset.x + fieldSize * density / 2,
                                        offset.y + fieldSize * density / 2
                                    )
                                )

                            FieldType.ONE ->
                                drawNumber(
                                    textMeasurer = textMeasurer,
                                    number = 1,
                                    color = colorOneAdjacentMine,
                                    topLeft = offset,
                                    size = fieldSizeWithDensity
                                )

                            FieldType.TWO ->
                                drawNumber(
                                    textMeasurer = textMeasurer,
                                    number = 2,
                                    color = colorTwoAdjacentMines,
                                    topLeft = offset,
                                    size = fieldSizeWithDensity
                                )

                            FieldType.THREE ->
                                drawNumber(
                                    textMeasurer = textMeasurer,
                                    number = 3,
                                    color = colorThreeAdjacentMines,
                                    topLeft = offset,
                                    size = fieldSizeWithDensity
                                )

                            FieldType.FOUR ->
                                drawNumber(
                                    textMeasurer = textMeasurer,
                                    number = 4,
                                    color = colorFourAdjacentMines,
                                    topLeft = offset,
                                    size = fieldSizeWithDensity
                                )

                            FieldType.FIVE ->
                                drawNumber(
                                    textMeasurer = textMeasurer,
                                    number = 5,
                                    color = colorFiveAdjacentMines,
                                    topLeft = offset,
                                    size = fieldSizeWithDensity
                                )

                            FieldType.SIX ->
                                drawNumber(
                                    textMeasurer = textMeasurer,
                                    number = 6,
                                    color = colorSixAdjacentMines,
                                    topLeft = offset,
                                    size = fieldSizeWithDensity
                                )

                            FieldType.SEVEN ->
                                drawNumber(
                                    textMeasurer = textMeasurer,
                                    number = 7,
                                    color = colorSevenAdjacentMines,
                                    topLeft = offset,
                                    size = fieldSizeWithDensity
                                )

                            FieldType.EIGHT ->
                                drawNumber(
                                    textMeasurer = textMeasurer,
                                    number = 8,
                                    color = colorEightAdjacentMines,
                                    topLeft = offset,
                                    size = fieldSizeWithDensity
                                )

                            FieldType.EMPTY -> Unit
                        }
                    }
                }
            }
        }
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