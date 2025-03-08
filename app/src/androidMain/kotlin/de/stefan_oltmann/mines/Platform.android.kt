package de.stefan_oltmann.mines

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset

/* Not effective as there is no right-click on Android */
actual fun Modifier.addRightClickListener(onClick: (Offset) -> Unit): Modifier = this
