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
import cmps312.yalapay.view.customer.CustomerDetails
import cmps312.yalapay.view.customer.CustomersList
import cmps312.yalapay.view.report.DashboardScreen
import cmps312.yalapay.view.invoice.*
import cmps312.yalapay.view.report.ChequeReport
import cmps312.yalapay.view.report.InvoiceReport

@Composable
fun AppNavigator(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController,
            startDestination = Screen.LoginScreen.route,
            modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = Screen.LoginScreen.route) {
           LoginScreen {
                navController.navigate(Screen.Dashboard.route)
            }
        }

        composable(route = Screen.Dashboard.route) {
            DashboardScreen()
        }

        composable(route = Screen.MainScreen.route) {
            MainScreen()
        }

        /// Customer
        composable(route = Screen.Customers.route) {
            CustomersList(
                onAddCustomer = {
                    navController.navigate(Screen.CustomerDetails.route)
                },
                onCustomerEdit = {
                    navController.navigate(Screen.CustomerDetails.route)
                }
            )
        }

        composable(route = Screen.CustomerDetails.route) {
            CustomerDetails {
                navController.navigateUp()
            }
        }

        /// Invoice
        composable(route = Screen.Invoices.route) {
            InvoicesList(
                onAddInvoice = {
                    navController.navigate(Screen.InvoiceScreen.route)
                },
                onUpdateInvoice = {
                    navController.navigate(Screen.InvoiceScreen.route)
                },
                onGetPayments = {
                    navController.navigate(Screen.InvoicePayments.route)
                },
                onAddPayment = {
                    navController.navigate(Screen.PaymentScreen.route)
                }
            )
        }

        composable(route = Screen.InvoiceScreen.route) {
            InvoiceScreen{
                navController.navigateUp()
            }
        }

        composable(route = Screen.PaymentScreen.route) {
            PaymentScreen {
                navController.navigateUp()
            }
        }

        composable(route = Screen.InvoicePayments.route) {
            InvoicePayments(
                onNavigateBack = {
                    navController.navigate(Screen.Invoices.route)
            }, onUpdatePayment = {
                    navController.navigate(Screen.PaymentScreen.route)
            })
        }

        composable(route = Screen.PaymentScreen.route) {
            PaymentScreen {
                navController.navigateUp()
            }
        }

        /// ChequeDeposit
        composable(route = Screen.ChequeDepositScreen.route) {
            ChequeDepositScreen{
                navController.navigateUp()
            }
        }

        composable(Screen.ChequeDeposits.route) {
            ChequeDepositsList(
                onAddChequeDeposit = {
                    navController.navigate(Screen.ChequeDepositScreen.route)
                },
                onViewChequeDeposit = {
                    navController.navigate(Screen.ChequeDepositScreen.route)
                },
                onUpdateChequeDeposit = {
                    navController.navigate(Screen.ChequeDepositScreen.route)
                },
            )
        }

        /// Reports
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