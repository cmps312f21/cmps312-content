package qa.edu.cmps312.colorchanger

import android.graphics.Color

fun getRandomColor() : Int {
    val rgbValues = (0..255).shuffled().take(4)
    return Color.argb(rgbValues[0], rgbValues[1], rgbValues[2], rgbValues[3])
}