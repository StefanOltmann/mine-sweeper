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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stefan_oltmann.mines.FONT_SIZE
import de.stefan_oltmann.mines.ui.icons.IconCheck
import de.stefan_oltmann.mines.ui.icons.IconHeight
import de.stefan_oltmann.mines.ui.icons.IconMines
import de.stefan_oltmann.mines.ui.icons.IconWidth
import de.stefan_oltmann.mines.ui.theme.buttonSize
import de.stefan_oltmann.mines.ui.theme.colorCardBackground
import de.stefan_oltmann.mines.ui.theme.colorCardBorder
import de.stefan_oltmann.mines.ui.theme.colorCellHidden
import de.stefan_oltmann.mines.ui.theme.defaultRoundedCornerShape
import de.stefan_oltmann.mines.ui.theme.defaultSpacing
import de.stefan_oltmann.mines.ui.theme.doublePadding
import de.stefan_oltmann.mines.ui.theme.lightGray
import de.stefan_oltmann.mines.ui.theme.sliderColors

@Composable
fun SettingsDialog(
    fontFamily: FontFamily,
    onClose: () -> Unit
) {

    val width = remember { mutableStateOf(10f) }
    val height = remember { mutableStateOf(10f) }
    val mineCount = remember { mutableStateOf(20f) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2F))
            .noRippleClickable(onClick = onClose)
    ) {

        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = colorCardBackground
            ),
            shape = defaultRoundedCornerShape,
            border = BorderStroke(1.dp, colorCardBorder),
            modifier = Modifier
                .widthIn(max = 400.dp)
                .doublePadding()
                .noRippleClickable {
                    /* Catch all clicks */
                }
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(defaultSpacing),
                modifier = Modifier.doublePadding()
            ) {

//                Text(
//                    text = "Settings",
//                    fontFamily = fontFamily,
//                    color = lightGray,
//                    fontSize = FONT_SIZE.sp
//                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(defaultSpacing),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = defaultSpacing)
                ) {

                    Icon(
                        imageVector = IconWidth,
                        contentDescription = null,
                        tint = lightGray
                    )

                    Slider(
                        value = width.value,
                        onValueChange = {
                            width.value = it
                        },
                        valueRange = 5f..30f,
                        colors = sliderColors,
                        modifier = Modifier.weight(1F)
                    )

                    Text(
                        text = width.value.toInt().toString(),
                        fontFamily = fontFamily,
                        color = lightGray,
                        fontSize = FONT_SIZE.sp,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.widthIn(min = 20.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(defaultSpacing),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = defaultSpacing)
                ) {

                    Icon(
                        imageVector = IconHeight,
                        contentDescription = null,
                        tint = lightGray
                    )

                    Slider(
                        value = height.value,
                        onValueChange = {
                            height.value = it
                        },
                        valueRange = 5f..30f,
                        colors = sliderColors,
                        modifier = Modifier.weight(1F)
                    )

                    Text(
                        text = height.value.toInt().toString(),
                        fontFamily = fontFamily,
                        color = lightGray,
                        fontSize = FONT_SIZE.sp,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.widthIn(min = 20.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(defaultSpacing),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = defaultSpacing)
                ) {

                    Icon(
                        imageVector = IconMines,
                        contentDescription = null,
                        tint = lightGray,
                        modifier = Modifier.size(24.dp)
                    )

                    Slider(
                        value = mineCount.value,
                        onValueChange = {
                            mineCount.value = it
                        },
                        valueRange = 5f..99f,
                        colors = sliderColors,
                        modifier = Modifier.weight(1F)
                    )

                    Text(
                        text = mineCount.value.toInt().toString(),
                        fontFamily = fontFamily,
                        color = lightGray,
                        fontSize = FONT_SIZE.sp,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.widthIn(min = 20.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(defaultSpacing)
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(buttonSize)
                            .weight(0.5f)
                            .background(colorCellHidden, defaultRoundedCornerShape)
                            .noRippleClickable(onClose)
                    ) {

                        Icon(
                            imageVector = IconCheck,
                            contentDescription = null,
                            tint = Color.Green
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(buttonSize)
                            .weight(0.5f)
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
}
