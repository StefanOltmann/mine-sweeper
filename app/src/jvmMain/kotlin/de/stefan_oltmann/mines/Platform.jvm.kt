package de.stefan_oltmann.mines

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.isSecondary
import androidx.compose.ui.input.pointer.pointerInput

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.addRightClickListener(onClick: (Offset) -> Unit): Modifier =
    this.pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {

                val event = awaitPointerEvent()

                val change = event.changes.first()

                if (change.pressed && event.button.isSecondary) {

                    val offset = change.position

                    onClick(offset)
                }
            }
        }
    }
