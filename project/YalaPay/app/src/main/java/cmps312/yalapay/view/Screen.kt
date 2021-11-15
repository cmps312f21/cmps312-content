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

    object DashboardScreen : Screen(
        route = "DashboardScreen", title = "Dashboard",
        icon = Icons.Outlined.Dashboard
    )

    object CustomerDetails : Screen(
        route = "CustomerDetails", title = "Customer",
        icon = Icons.Outlined.Details
    )

    object CustomersList : Screen(
        route = "Customers", title = "Customers",
        icon = Icons.Outlined.ManageAccounts
    )

    object InvoicesList : Screen(
        route = "Invoices", title = "Invoices",
        icon = Icons.Outlined.Money
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

    object SortedInvoices : Screen(
        route = "SortedInvoices", title = "Sorted Invoices",
        icon = Icons.Outlined.Money
    )

    //////////////////////////////////////////////////////
    object ChequeList : Screen(
        route = "ChequeList", title = "Cheques",
        icon = Icons.Outlined.Note
    )

    object SortedCheques : Screen(
        route = "SortedCheques", title = "Sorted Cheques",
        icon = Icons.Outlined.Money
    )

    object SelectAccount : Screen(
        route = "SelectAccount", title = "Select Account",
        icon = Icons.Outlined.AccountBox
    )

    object ChequeDepositList : Screen(
        route = "ChequeDeposits", title = "Cheque Deposits",
        icon = Icons.Outlined.EventNote
    )

    object ChequeReport : Screen(
        route = "ChequeReport", title = "Cheque Report",
        icon = Icons.Default.BarChart
    )

    object SetChequesStatus : Screen(
        route = "SetChequesStatus", title = "Set Cheques Status",
        icon = Icons.Outlined.Update
    )

    object SetReturnReason : Screen(
        route = "SetReturnReason", title = "Set Return Reason",
        icon = Icons.Outlined.Warning
    )

    object MainScreen : Screen(
        route = "MainScreen", title = "Main Screen",
        icon = Icons.Default.ScreenRotation
    )
}