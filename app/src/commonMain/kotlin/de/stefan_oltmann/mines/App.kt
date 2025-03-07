/*
 * 💣 Mines 💣
 * Copyright (C) 2025 Stefan Oltmann
 * https://github.com/StefanOltmann/mines
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

package de.stefan_oltmann.mines

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stefan_oltmann.mines.model.GameState
import de.stefan_oltmann.mines.ui.MinefieldCanvas
import de.stefan_oltmann.mines.ui.theme.colorBackground
import de.stefan_oltmann.mines.ui.theme.colorCardBackground
import de.stefan_oltmann.mines.ui.theme.colorCardBorder
import de.stefan_oltmann.mines.ui.theme.lightGray

const val APP_TITLE = "Mines"

@Composable
fun App() {

    val gameState = remember { GameState() }

    val redrawState = remember { mutableStateOf(0) }

    println("REDRAW")

    /*
     * Force redraw if state changes.
     *
     * FIXME This is a hack
     */
    redrawState.value

    val cellSize = 40f

    val textMeasurer = rememberTextMeasurer()

    val density = LocalDensity.current.density

    val cellSizeWithDensity = Size(
        width = cellSize * density,
        height = cellSize * density
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorBackground)
    ) {

        Card(
            backgroundColor = colorCardBackground,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, colorCardBorder)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {

                Row {

                    Text(
                        text = "Mines",
                        color = lightGray,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {

                            gameState.restart()

                            redrawState.value += 1
                        }
                    ) {

                        Text("Restart")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Remaining mines: ${gameState.minefield.getRemainingFlagsCount()}",
                        color = lightGray,
                        fontSize = 16.sp
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                MinefieldCanvas(
                    gameState,
                    cellSize,
                    cellSizeWithDensity,
                    redrawState,
                    textMeasurer
                )
            }
        }
    }
}