package cmps312.yalapay.entity

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate
import java.security.SecureRandom

@Serializable
data class Payment(
    var paymentId: Int = SecureRandom().nextInt(1000),
    val invoiceNo: Int,
    val amount: Double,
    val paymentDate: LocalDate,
    val paymentMode: String,
    var cheque: Cheque? = null,
    var chequeNo: Int? = null
)

