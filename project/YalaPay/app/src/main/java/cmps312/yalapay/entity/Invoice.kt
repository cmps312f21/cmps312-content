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
)