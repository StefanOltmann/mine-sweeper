package de.stefan_oltmann.minesweeper.de.stefan_oltmann.minesweeper

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Minesweeper",
    ) {
        App()
    }
}