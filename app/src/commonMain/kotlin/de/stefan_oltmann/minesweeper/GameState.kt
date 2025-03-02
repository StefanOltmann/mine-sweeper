package de.stefan_oltmann.minesweeper

class GameState {

    var minefield = Minefield(
        width = 10,
        height = 10,
        mineCount = 20,
        seed = 1
    )

    fun hit(x: Int, y: Int) {

        /* Ignore clicks on already revealed or flagged fields. */
        if (minefield.isRevealed(x, y) || minefield.isFlagged(x, y))
            return

        minefield.reveal(x, y)
    }

    fun flag(x: Int, y: Int) {

        /* Only non-revealed fields can be flagged. */
        if (minefield.isRevealed(x, y))
            return

        minefield.toggleFlag(x, y)
    }
}