package oop

data class Conference(val name: String,
                 val city: String,
                 val isFree: Boolean = true) {
    var fee : Double = 0.0

    // Secondary Constructor
    constructor(name: String,
                city: String,
                fee: Double) : this(name, city, false) {
        this.fee = fee
    }
}

fun main() {
    val conference = Conference("Kotlin Conf.", "Istanbul", 300.0)
    println(conference)
}
