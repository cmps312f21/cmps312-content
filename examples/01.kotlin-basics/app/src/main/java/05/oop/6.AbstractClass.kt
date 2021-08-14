package oop

import kotlin.math.pow

abstract class Shape {
    abstract val area: Double
    open val name: String
        get() = "Shape"
}

class Rectangle(val width: Double, val height: Double) : Shape() {
    override val area: Double
        get() = width * height

    override val name: String
        get() = "Rectangle"
}

class Circle(val radius: Double) : Shape() {
    override val area: Double
        get() = Math.PI * radius.pow(2)

    override val name: String
        get() = "Circle"
}

fun main() {
    val shapes = mutableListOf<Shape>()
    shapes.add( Circle(3.5) )
    shapes.add( Circle(5.0) )
    shapes.add( Rectangle (3.5, 5.0) )

    println("> Processing shapes polymorphically")
    shapes.forEach {
        val shapeInfo = when (it) {
            is Circle -> "Its radius is ${it.radius}"
            is Rectangle -> "Its width is ${it.width} and its height is ${it.height}"
            else -> ""
        }
        println("${it.name} area is ${it.area}. $shapeInfo")
    }
    val areaSum = shapes.sumByDouble { it.area }
    println("Sum of area of all shapes ${"%.2f".format(areaSum)}")
}
