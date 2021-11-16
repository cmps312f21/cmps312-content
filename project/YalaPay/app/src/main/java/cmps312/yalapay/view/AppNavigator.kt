package cmps312.yalapay.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cmps312.yalapay.view.auth.LoginScreen
import cmps312.yalapay.view.chequedeposit.ChequeDepositScreen
import cmps312.yalapay.view.chequedeposit.ChequeDepositsList
import cmps312.yalapay.view.customer.CustomerScreen
import cmps312.yalapay.view.customer.CustomersList
import cmps312.yalapay.view.invoice.InvoicePayments
import cmps312.yalapay.view.invoice.InvoiceScreen
import cmps312.yalapay.view.invoice.InvoicesList
import cmps312.yalapay.view.invoice.PaymentScreen
import cmps312.yalapay.view.report.ChequeReport
import cmps312.yalapay.view.report.DashboardScreen
import cmps312.yalapay.view.report.InvoiceReport


@Composable
fun AppNavigator(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = Screen.Login.route) {
           LoginScreen {
                navController.navigate(Screen.Dashboard.route)
            }
        }

        /// Customer
        composable(route = Screen.Customers.route) {
            CustomersList(
                onAddCustomer = {
                    navController.navigate(Screen.Customer.route)
                },
                onCustomerEdit = {
                    navController.navigate(Screen.Customer.route)
                }
            )
        }

        composable(route = Screen.Customer.route) {
            CustomerScreen {
                navController.navigateUp()
            }
        }

        /// Invoice
        composable(route = Screen.Invoices.route) {
            InvoicesList(
                onAddInvoice = {
                    navController.navigate(Screen.Invoice.route)
                },
                onUpdateInvoice = {
                    navController.navigate(Screen.Invoice.route)
                },
                onGetPayments = {
                    navController.navigate(Screen.InvoicePayments.route)
                },
                onAddPayment = {
                    navController.navigate(Screen.Payment.route)
                }
            )
        }

        composable(route = Screen.Invoice.route) {
            InvoiceScreen{
                navController.navigateUp()
            }
        }

        composable(route = Screen.Payment.route) {
            PaymentScreen {
                navController.navigateUp()
            }
        }

        composable(route = Screen.InvoicePayments.route) {
            InvoicePayments(
                onNavigateBack = {
                    navController.navigate(Screen.Invoices.route)
                },
                onUpdatePayment = {
                    navController.navigate(Screen.Payment.route)
                }
            )
        }

        composable(route = Screen.Payment.route) {
            PaymentScreen {
                navController.navigateUp()
            }
        }

        /// ChequeDeposit
        composable(route = Screen.ChequeDeposit.route) {
            ChequeDepositScreen {
                navController.navigateUp()
            }
        }

        composable(Screen.ChequeDeposits.route) {
            ChequeDepositsList(
                onAddChequeDeposit = {
                    navController.navigate(Screen.ChequeDeposit.route)
                },
                onViewChequeDeposit = {
                    navController.navigate(Screen.ChequeDeposit.route)
                },
                onUpdateChequeDeposit = {
                    navController.navigate(Screen.ChequeDeposit.route)
                },
            )
        }

        /// Reports
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                onInvoiceReport = {
                    navController.navigate(Screen.InvoiceReport.route)
                },
                onChequeReport = {
                    navController.navigate(Screen.ChequeReport.route)
                }
            )
        }

        composable(Screen.ChequeReport.route) {
            ChequeReport(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.InvoiceReport.route) {
            InvoiceReport(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

    }
}