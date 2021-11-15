package cmps312.yalapay.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
) {
    override fun toString() = "$firstName $lastName"
}