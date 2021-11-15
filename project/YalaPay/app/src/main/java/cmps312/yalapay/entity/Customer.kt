package cmps312.yalapay.entity

import kotlinx.serialization.Serializable
import java.security.SecureRandom

@Serializable
data class Address(
    val street: String,
    val city: String,
    val country: String
) {
    override fun toString() = "$street, $city, $country"
}

@Serializable
data class ContactDetails(
    val firstName: String,
    val lastName: String,
    val mobile: String,
    val email: String
) {
    override fun toString() = "$firstName $lastName, $email"
}

@Serializable
data class Customer(
    var customerId: Int = SecureRandom().nextInt(1000),
    val companyName: String,
    val address: Address,
    val contactDetails: ContactDetails
) {
    override fun toString() = "#$customerId - $companyName"
}