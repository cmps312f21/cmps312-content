package cmps312.yalapay.entity

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
import kotlinx.serialization.Serializable

@Serializable
data class Cheque(
    val chequeNo: Int,
    val amount: Double,
    val drawer: String,
    val bankName: String,
    var status: String = ChequeStatus.AWAITING.label,
    val chequeImageUri: String = "",
    val receivedDate: LocalDate = Clock.System.todayAt(TimeZone.currentSystemDefault()),
    val dueDate: LocalDate,
    var cashedDate: LocalDate? = null,
    var returnedDate: LocalDate? = null,
    var returnReason: String? = null
){
    override fun toString() = "Cheque#$chequeNo - $bankName ($dueDate). $status"
}