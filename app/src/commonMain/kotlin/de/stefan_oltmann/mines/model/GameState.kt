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

package de.stefan_oltmann.mines.model

import de.stefan_oltmann.mines.DEFAULT_MAP_HEIGHT
import de.stefan_oltmann.mines.DEFAULT_MAP_WIDTH
import de.stefan_oltmann.mines.DEFAULT_MINE_COUNT

class GameState {

    var gameOver = false

    var minefield = Minefield(
        width = DEFAULT_MAP_WIDTH,
        height = DEFAULT_MAP_HEIGHT,
        mineCount = DEFAULT_MINE_COUNT,
        seed = (1..Int.MAX_VALUE).random()
    )

    fun restart() {

        gameOver = false

        minefield = Minefield(
            width = DEFAULT_MAP_WIDTH,
            height = DEFAULT_MAP_HEIGHT,
            mineCount = DEFAULT_MINE_COUNT,
            seed = (1..Int.MAX_VALUE).random()
        )
    }

    fun hit(x: Int, y: Int) {

        /* Ignore clicks on already revealed or flagged cells. */
        if (minefield.isRevealed(x, y) || minefield.isFlagged(x, y))
            return

        /* Reveal the field in any case */
        minefield.reveal(x, y)

        /* Check game over condition */
        if (minefield.getCellType(x, y) == CellType.MINE) {
            gameOver = true
            return
        }

        /*
         * TODO Check win condition
         */
    }

    fun flag(x: Int, y: Int) {

        /* Only non-revealed fields can be flagged. */
        if (minefield.isRevealed(x, y))
            return

        minefield.toggleFlag(x, y)
    }
}
