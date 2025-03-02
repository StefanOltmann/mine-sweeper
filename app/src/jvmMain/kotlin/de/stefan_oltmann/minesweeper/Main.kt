package de.stefan_oltmann.minesweeper

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.stefan_oltmann.minesweeper.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Minesweeper",
    ) {
        App()
    }
}