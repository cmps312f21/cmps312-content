package cmps312.yalapay.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Login : Screen(
        route = "login", title = "Login",
        icon = Icons.Outlined.Login
    )

    object Customers : Screen(
        route = "customers", title = "Customers",
        icon = Icons.Outlined.People
    )

    object Customer : Screen(
        route = "customer", title = "Customer",
        icon = Icons.Outlined.Details
    )

    object Invoices : Screen(
        route = "invoices", title = "Invoices",
        icon = Icons.Outlined.Receipt
    )

    object InvoicePayments : Screen(
        route = "InvoicePayments", title = "Invoice Payments",
        icon = Icons.Outlined.Details
    )

    object Invoice : Screen(
        route = "invoice", title = "Invoice",
        icon = Icons.Outlined.AddAPhoto
    )

    object Payment : Screen(
        route = "payment", title = "Payment",
        icon = Icons.Outlined.AttachMoney
    )

    object ChequeDeposits : Screen(
        route = "ChequeDeposits", title = "Deposits",
        icon = Icons.Outlined.AccountBalanceWallet
    )

    object ChequeDeposit : Screen(
        route = "ChequeDeposit", title = "Cheque Deposit",
        icon = Icons.Outlined.AccountBalanceWallet
    )

    object Dashboard : Screen(
        route = "dashboard", title = "Dashboard",
        icon = Icons.Outlined.Dashboard
    )

    object InvoiceReport : Screen(
        route = "InvoiceReport", title = "Invoice Report",
        icon = Icons.Outlined.PieChart
    )

    object ChequeReport : Screen(
        route = "ChequeReport", title = "Cheque Report",
        icon = Icons.Default.BarChart
    )
}