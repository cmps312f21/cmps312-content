package cmps312.yalapay.entity


data class InvoicesSummary (
    val invoicesTotal: Double,
    val invoicesInLess30Days: Double,
    val invoicesInMore30Days: Double
)

data class ChequesSummary (
    val chequesAwaiting: Double,
    val chequesDeposited: Double,
    val chequesCashed: Double,
    val chequesReturned: Double
)