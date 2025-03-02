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

import kotlin.random.Random

class Map(
    val width: Int,
    val height: Int,
    val mineCount: Int,
    val seed: Int
) {

    private val matrix: Array<Array<Field>> = createMatrix()

    fun get(x: Int, y: Int): Field = matrix[x][y]

    fun toMapId(): String =
        "$width-$height-$mineCount-$seed"

    private fun createMatrix(): Array<Array<Field>> {

        val matrix: Array<Array<Field>> =
            Array(width) {
                Array(height) {
                    Field.EMPTY
                }
            }

        placeMines(matrix)

        return matrix
    }

    private fun placeMines(matrix: Array<Array<Field>>) {

        val random = Random(seed)

        var placedMinesCount = 0

        while (placedMinesCount < mineCount) {

            val x = random.nextInt(width)
            val y = random.nextInt(height)

            /*
             * Only place mines into empty fields.
             */
            if (matrix[x][y] == Field.EMPTY) {

                matrix[x][y] = Field.MINE

                placedMinesCount++
            }
        }
    }
}

enum class Field {

    EMPTY,
    MINE
}
