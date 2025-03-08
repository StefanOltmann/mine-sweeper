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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import de.stefan_oltmann.mines.model.GameState
import de.stefan_oltmann.mines.ui.AppFooter
import de.stefan_oltmann.mines.ui.MinefieldCanvas
import de.stefan_oltmann.mines.ui.Toolbar
import de.stefan_oltmann.mines.ui.theme.EconomicaFontFamily
import de.stefan_oltmann.mines.ui.theme.colorBackground
import de.stefan_oltmann.mines.ui.theme.colorCardBackground
import de.stefan_oltmann.mines.ui.theme.colorCardBorder
import de.stefan_oltmann.mines.ui.theme.colorCardBorderGameOver
import de.stefan_oltmann.mines.ui.theme.colorCardBorderGameWon
import de.stefan_oltmann.mines.ui.theme.defaultRoundedCornerShape
import de.stefan_oltmann.mines.ui.theme.doublePadding

@Composable
fun App() {

    val fontFamily = EconomicaFontFamily()

    val gameState = remember { GameState() }

    val elapsedSeconds by gameState.elapsedSeconds.collectAsState()

    val redrawState = remember { mutableStateOf(0) }

    println("REDRAW")

    /*
     * Force redraw if state changes.
     *
     * FIXME This is a hack
     */
    redrawState.value

    val textMeasurer = rememberTextMeasurer()

    val density = LocalDensity.current.density

    val cellSizeWithDensity = Size(
        width = CELL_SIZE * density,
        height = CELL_SIZE * density
    )

    Column {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .background(colorBackground)
        ) {

            val borderColor = when {
                gameState.gameOver -> colorCardBorderGameOver
                gameState.gameWon -> colorCardBorderGameWon
                else -> colorCardBorder
            }

            Card(
                backgroundColor = colorCardBackground,
                shape = defaultRoundedCornerShape,
                border = BorderStroke(1.dp, borderColor),
                modifier = Modifier.doublePadding()
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.doublePadding()
                ) {

                    Toolbar(
                        highlightRestartButton = gameState.gameOver || gameState.gameWon,
                        elapsedSeconds = elapsedSeconds,
                        remainingFlagsCount = gameState.minefield.getRemainingFlagsCount(),
                        fontFamily = fontFamily,
                        showSettings = {

                            // TODO
                        },
                        restartGame = {

                            gameState.restart()

                            // HACK
                            redrawState.value += 1
                        }
                    )

                    MinefieldCanvas(
                        gameState,
                        CELL_SIZE,
                        cellSizeWithDensity,
                        redrawState,
                        textMeasurer,
                        fontFamily
                    )
                }
            }
        }

        AppFooter(fontFamily)
    }
}
