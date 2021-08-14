package variables

/**
 * The `is` operator checks if a variable is an instance of a type
 * If the expression is true, there's no need to cast the variable
 * explicitly to the is-checked type.
 * See this pages for details:
 * http://kotlinlang.org/docs/reference/typecasts.html#smart-casts
 */
fun main() {
    println(getStringLength("Dar Assalam"))
    println(getStringLength(1))
}

fun getStringLength(obj: Any): Int? {
    if (obj is String)
        return obj.length // no cast to String is needed
    return null
}