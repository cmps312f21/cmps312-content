package cmps312.yalapay.entity

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
import kotlinx.serialization.Serializable
import java.security.SecureRandom

@Serializable
data class ChequeDeposit(
    var depositId: Int = SecureRandom().nextInt(1000),
    val bankAccountNo: String,
    val depositDate: LocalDate = Clock.System.todayAt(TimeZone.currentSystemDefault()),
    var depositStatus: String = ChequeDepositStatus.DEPOSITED.label,
    var chequeNos: List<Int>
)