package cmps312.yalapay.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object LoginScreen : Screen(
        route = "LoginScreen", title = "Login",
        icon = Icons.Outlined.Login
    )

    object Dashboard : Screen(
        route = "Dashboard", title = "Dashboard",
        icon = Icons.Outlined.Dashboard
    )

    object CustomerScreen : Screen(
        route = "CustomerScreen", title = "Customer",
        icon = Icons.Outlined.Details
    )

    object Customers : Screen(
        route = "Customers", title = "Customers",
        icon = Icons.Outlined.ManageAccounts
    )

    object Invoices : Screen(
        route = "Invoices", title = "Invoices",
        icon = Icons.Outlined.Receipt
    )

    object InvoicePayments : Screen(
        route = "InvoicePayments", title = "Invoice Payments",
        icon = Icons.Outlined.Details
    )

    object InvoiceReport : Screen(
        route = "InvoiceReport", title = "Invoice Report",
        icon = Icons.Outlined.PieChart
    )

    object InvoiceScreen : Screen(
        route = "Invoice", title = "Invoice",
        icon = Icons.Outlined.AddAPhoto
    )

    object PaymentScreen : Screen(
        route = "Payment", title = "Payment",
        icon = Icons.Outlined.AttachMoney
    )

    object ChequeDeposits : Screen(
        route = "ChequeDeposits", title = "Deposits",
        icon = Icons.Outlined.AccountBalanceWallet
    )

    object ChequeDepositScreen : Screen(
        route = "ChequeDeposit", title = "Cheque Deposit",
        icon = Icons.Outlined.AccountBalanceWallet
    )

    object ChequeReport : Screen(
        route = "ChequeReport", title = "Cheque Report",
        icon = Icons.Default.BarChart
    )
}