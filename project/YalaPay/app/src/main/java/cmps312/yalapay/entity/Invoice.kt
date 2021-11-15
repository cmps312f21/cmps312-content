package cmps312.yalapay.entity

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable
import java.security.SecureRandom

@Serializable
data class Invoice(
    var invoiceNo: Int = SecureRandom().nextInt(1000),
    val customerId: Int,
    val amount: Double,
    val invoiceDate: LocalDate = Clock.System.todayAt(TimeZone.currentSystemDefault()),
    val dueDate: LocalDate = Clock.System.todayAt(TimeZone.currentSystemDefault()).plus(15, DateTimeUnit.DAY),
) {
    var totalPayments: Double = 0.0
}

val Invoice.balance : Double
    get() = this.amount - this.totalPayments

val Invoice.status : String
    get() = when {
        (this.balance == 0.0) -> "Paid"
        (this.balance == this.amount) -> "Pending"
        else -> "Partially Paid"
    }