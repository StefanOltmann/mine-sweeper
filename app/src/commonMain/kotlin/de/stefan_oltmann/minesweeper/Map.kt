package de.stefan_oltmann.minesweeper

import kotlin.random.Random

class Map(
    val width: Int,
    val height: Int,
    val mineCount: Int,
    val seed: Int
) {

    val fieldCount = width * height

    private val fields: Array<Array<Field>> = createFields()

    fun get(x: Int, y: Int): Field = fields[x][y]

    fun toMapId(): String =
        "MAP/$width/$height/$mineCount/$seed"

    private fun createFields(): Array<Array<Field>> {

            val matrix: Array<Array<Field>> =
                Array(width) {
                    Array(height) {
                        Field.EMPTY
                    }
                }

            val random = Random(seed)

            var placedMines = 0

            while (placedMines < mineCount) {

                val x = random.nextInt(width)
                val y = random.nextInt(height)

                /*
                 * Only place mines into empty fields.
                 */
                if (matrix[x][y] == Field.EMPTY) {

                    matrix[x][y] = Field.MINE

                    placedMines++
                }
            }

            return matrix
    }
}

enum class Field {

    EMPTY,
    MINE
}
