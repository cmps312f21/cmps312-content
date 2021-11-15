package cmps312.yalapay.entity

enum class InvoiceStatus(val label: String) {
    ALL("All"),
    PENDING("Pending"),
    PARTIALLY_PAID("Partially Paid"),
    PAID("Paid")
}

enum class ChequeStatus(val label: String) {
    ALL("All"),
    AWAITING("Awaiting"),
    DEPOSITED("Deposited"),
    CASHED("Cashed"),
    RETURNED("Returned")
}

enum class ChequeDepositStatus(val label: String) {
    DEPOSITED("Deposited"),
    CASHED("Cashed"),
    CASHED_WITH_RETURNS("Cashed with Returns")
}

enum class PaymentMode(val label: String) {
    CREDIT_CARD("Credit Card"),
    BANK_TRANSFER("Bank Transfer"),
    CHEQUE ("Cheque")
}

fun getInvoiceStatus() = InvoiceStatus.values().map { it.label }
fun getPaymentModes() = PaymentMode.values().map { it.label }
fun getChequeDepositStatus() = ChequeDepositStatus.values().map { it.label }
fun getChequeStatus() = ChequeStatus.values()
                            //.filter { it == ChequeStatus.CASHED || it == ChequeStatus.RETURNED  }
                            .map { it.label }

enum class FormMode { ADD, UPDATE, VIEW }