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

// @formatter:off
private val directions = listOf(
    -1 to -1, 0 to -1, 1 to -1,
    -1 to  0,          1 to  0,
    -1 to  1, 0 to  1, 1 to  1
)
// @formatter:on

class Minefield(
    val width: Int,
    val height: Int,
    val mineCount: Int,
    val seed: Int
) {

    val id: String = "$width-$height-$mineCount-$seed"

    private val matrix: Array<Array<FieldType>> =
        createMatrix(width, height, mineCount, seed)

    private val revealedMatrix: Array<Array<Boolean>> =
        Array(width) {
            Array(height) {
                false
            }
        }

    private val flaggedMatrix: Array<Array<Boolean>> =
        Array(width) {
            Array(height) {
                false
            }
        }

    fun isRevealed(x: Int, y: Int): Boolean =
        revealedMatrix[x][y]

    fun reveal(x: Int, y: Int) {
        revealedMatrix[x][y] = true
    }

    fun isFlagged(x: Int, y: Int): Boolean =
        flaggedMatrix[x][y]

    fun toggleFlag(x: Int, y: Int) {
        flaggedMatrix[x][y] = !flaggedMatrix[x][y]
    }

    fun isMine(x: Int, y: Int): Boolean =
        matrix[x][y] == FieldType.MINE

    fun getFieldType(x: Int, y: Int): FieldType = matrix[x][y]

    companion object {

        private fun createMatrix(
            width: Int,
            height: Int,
            mineCount: Int,
            seed: Int
        ): Array<Array<FieldType>> {

            val matrix = createEmptyMatrix(width, height)

            placeMines(matrix, width, height, mineCount, seed)

            placeCounts(matrix, width, height)

            return matrix
        }

        private fun createEmptyMatrix(width: Int, height: Int): Array<Array<FieldType>> =
            Array(width) {
                Array(height) {
                    FieldType.EMPTY
                }
            }

        private fun placeMines(
            matrix: Array<Array<FieldType>>,
            width: Int,
            height: Int,
            mineCount: Int,
            seed: Int
        ) {

            /*
             * Mines are placed according to seed to reproduce results.
             */
            val random = Random(seed)

            val protectedXRange = width / 2 - 2..width / 2 + 2
            val protectedYRange = height / 2 - 2..height / 2 + 2

            var placedMinesCount = 0

            while (placedMinesCount < mineCount) {

                val x = random.nextInt(width)
                val y = random.nextInt(height)

                /*
                 * Keep the middle free of mines to give players a starting point.
                 */
                if (x in protectedXRange && y in protectedYRange)
                    continue

                /*
                 * Only place mines into empty fields.
                 *
                 * This guarantees that we have enough mines,
                 * even if the randomizer selects the same field twice.
                 */
                if (matrix[x][y] == FieldType.EMPTY) {

                    matrix[x][y] = FieldType.MINE

                    placedMinesCount++
                }
            }
        }

        private fun placeCounts(
            matrix: Array<Array<FieldType>>,
            width: Int,
            height: Int
        ) {

            for (x in 0 until width) {
                for (y in 0 until height) {

                    /* Minefields stay as they are. */
                    if (matrix[x][y] == FieldType.MINE)
                        continue

                    val mineCount = countMinesInAdjacentFields(matrix, x, y)

                    matrix[x][y] = FieldType.ofMineCount(mineCount)
                }
            }
        }

        private fun countMinesInAdjacentFields(
            matrix: Array<Array<FieldType>>,
            x: Int,
            y: Int
        ): Int =
            directions.count { (dx, dy) ->

                hasMine(
                    matrix = matrix,
                    x = x + dx,
                    y = y + dy
                )
            }

        private fun hasMine(
            matrix: Array<Array<FieldType>>,
            x: Int,
            y: Int
        ): Boolean {

            /* Return zero for out-of-bounds fields */
            @Suppress("ComplexCondition")
            if (x < 0 || y < 0 || x >= matrix.size || y >= matrix[x].size)
                return false

            return matrix[x][y] == FieldType.MINE
        }
    }
}