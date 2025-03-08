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

package de.stefan_oltmann.mines.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stefan_oltmann.mines.FONT_SIZE
import de.stefan_oltmann.mines.model.GameState
import de.stefan_oltmann.mines.ui.icons.IconFlag
import de.stefan_oltmann.mines.ui.icons.IconPlay
import de.stefan_oltmann.mines.ui.icons.IconTimer
import de.stefan_oltmann.mines.ui.theme.DoubleSpacer
import de.stefan_oltmann.mines.ui.theme.HalfSpacer
import de.stefan_oltmann.mines.ui.theme.lightGray

@Composable
fun Toolbar(
    gameState: GameState,
    redrawState: MutableState<Int>,
    fontFamily: FontFamily
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

//        Icon(
//            imageVector = IconSettings,
//            contentDescription = null,
//            tint = lightGray
//        )
//
//        DefaultSpacer()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {

                gameState.restart()

                redrawState.value += 1
            }
        ) {

            Icon(
                imageVector = IconPlay,
                contentDescription = null,
                tint = lightGray
            )

            Text(
                text = "New",
                fontFamily = fontFamily,
                color = lightGray,
                fontSize = FONT_SIZE.sp
            )
        }

        DoubleSpacer()

        Icon(
            imageVector = IconTimer,
            contentDescription = null,
            tint = lightGray
        )

        HalfSpacer()

        Text(
            text = "99999",
            fontFamily = fontFamily,
            color = lightGray,
            fontSize = FONT_SIZE.sp,
            textAlign = TextAlign.Right,
            modifier = Modifier.widthIn(min = 28.dp)
        )

        DoubleSpacer()

        Icon(
            imageVector = IconFlag,
            contentDescription = null,
            tint = lightGray
        )

        HalfSpacer()

        Text(
            text = gameState.minefield.getRemainingFlagsCount().toString(),
            fontFamily = fontFamily,
            color = lightGray,
            fontSize = FONT_SIZE.sp,
            textAlign = TextAlign.Right,
            modifier = Modifier.widthIn(min = 20.dp)
        )
    }
}
