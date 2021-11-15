package cmps312.yalapay.entity

enum class ChequeStatus(val label: String) {
    AWAITING("Awaiting"),
    DEPOSITED("Deposited"),
    CASHED("Cashed"),
    RETURNED("Returned")
}

enum class ChequeDepositStatus(val label: String) {
    DEPOSITED("Deposited"),
    CASHED("Cashed"),
    CASHED_WITH_RETURNS("Cashed with Returns"),
    RETURNED("Returned")
}

enum class PaymentMode(val label: String) {
    CREDIT_CARD("Credit Card"),
    BANK_TRANSFER("Bank Transfer"),
    CHEQUE ("Cheque")
}

fun getPaymentModes() = PaymentMode.values().map { it.label }
fun getChequeDepositStatus() = ChequeDepositStatus.values().map { it.label }
fun getChequeStatus() = ChequeStatus.values()
                            .filter { it == ChequeStatus.CASHED || it == ChequeStatus.RETURNED  }
                            .map { it.label }

enum class FormMode { ADD, EDIT }