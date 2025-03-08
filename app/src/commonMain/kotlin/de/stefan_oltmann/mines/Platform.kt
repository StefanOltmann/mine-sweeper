package de.stefan_oltmann.mines

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset

expect fun Modifier.addRightClickListener(
    onClick: (Offset) -> Unit
): Modifier
