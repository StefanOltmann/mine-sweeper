package de.stefan_oltmann.minesweeper

class Map(
    val width: Int,
    val height: Int,
    val mineCount: Int,
    val seed: Int
) {

    val fieldCount = width * height

    fun createFields(): Array<Array<Field>> {

        val matrix: Array<Array<Field>> =
            Array(width) {
                Array(height) {
                    Field.EMPTY
                }
            }

        return matrix
    }

    fun toMapId(): String =
        "MAP/$width/$height/$mineCount/$seed"

}

enum class Field {

    EMPTY,
    MINE
}
