package variables

fun main() {
    //Strings
    val firstName = "Ali"
    val lastName = "Faleh"

    /* String Template allow creating dynamic templated string
     with placeholders (instead of long string concatenation!)
     */
    val fullName = "$firstName $lastName"
    // Expression enclosed in {}
    val sum = "2 + 2 = ${2 + 2}"

    println(fullName)
    println(sum)

    var num = 10
    //Convert a number to a string
    val str = num.toString()

    //Convert a string to int
    num = str.toInt()
}