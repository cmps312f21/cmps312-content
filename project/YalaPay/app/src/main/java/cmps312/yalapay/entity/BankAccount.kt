package cmps312.yalapay.entity

import kotlinx.serialization.Serializable

@Serializable
class BankAccount(
    val accountNo: String,
    val bank: String
) {
    override fun toString() = "$accountNo $bank"
}