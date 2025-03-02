package de.stefan_oltmann.minesweeper

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times

@Composable
fun App() {

    val map = Map(
        width = 16,
        height = 12,
        mineCount = 10,
        seed = 1
    )

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text = "Minesweeper")

        Spacer(modifier = Modifier.height(16.dp))

        val fieldSize = 40f

        val textMeasurer = rememberTextMeasurer()

        val density = LocalDensity.current.density

        val fieldSizeWithDensity = Size(
            width = fieldSize * density,
            height = fieldSize * density
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Canvas(
                modifier = Modifier
                    .size(
                        width = map.width * fieldSize.dp,
                        height = map.height * fieldSize.dp
                    )
                    .border(1.dp, Color.Blue)
            ) {

                repeat(map.width) { x ->
                    repeat(map.height) { y ->

                        val offset = Offset(x * fieldSize * density, y * fieldSize * density)

                        drawRoundRect(
                            color = Color.Red,
                            topLeft = offset,
                            size = fieldSizeWithDensity,
                            cornerRadius = CornerRadius(10f),
                            style = Stroke()
                        )

                        drawText(
                            textMeasurer = textMeasurer,
                            text = "$x, $y",
                            style = TextStyle.Default.copy(
                                fontSize = 14.sp
                            ),
                            topLeft = offset,
                            size = fieldSizeWithDensity
                        )
                    }
                }
            }
        }
    }
}