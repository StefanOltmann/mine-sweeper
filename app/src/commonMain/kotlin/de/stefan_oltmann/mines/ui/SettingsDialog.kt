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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stefan_oltmann.mines.FONT_SIZE
import de.stefan_oltmann.mines.ui.icons.IconCheck
import de.stefan_oltmann.mines.ui.theme.buttonSize
import de.stefan_oltmann.mines.ui.theme.colorCardBackground
import de.stefan_oltmann.mines.ui.theme.colorCardBorder
import de.stefan_oltmann.mines.ui.theme.colorCellHidden
import de.stefan_oltmann.mines.ui.theme.defaultRoundedCornerShape
import de.stefan_oltmann.mines.ui.theme.doublePadding
import de.stefan_oltmann.mines.ui.theme.lightGray

@Composable
fun SettingsDialog(
    fontFamily: FontFamily,
    onClose: () -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2F))
            .noRippleClickable(onClick = onClose)
    ) {

        Card(
            backgroundColor = colorCardBackground,
            shape = defaultRoundedCornerShape,
            border = BorderStroke(1.dp, colorCardBorder),
            modifier = Modifier
                .doublePadding()
                .noRippleClickable {
                    /* Catch all clicks */
                }
        ) {

            Column(
                modifier = Modifier.doublePadding()
            ) {

                Text(
                    text = "Settings",
                    fontFamily = fontFamily,
                    color = lightGray,
                    fontSize = FONT_SIZE.sp,
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .doublePadding()
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(buttonSize)
                        .widthIn(min = 300.dp)
                        .background(colorCellHidden, defaultRoundedCornerShape)
                        .noRippleClickable(onClose)
                ) {

                    Icon(
                        imageVector = IconCheck,
                        contentDescription = null,
                        tint = Color.Green
                    )
                }
            }
        }
    }
}
