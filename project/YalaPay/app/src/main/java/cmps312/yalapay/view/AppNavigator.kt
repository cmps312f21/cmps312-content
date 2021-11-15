package cmps312.yalapay.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cmps312.yalapay.view.auth.LoginScreen
import cmps312.yalapay.view.cheque.*
import cmps312.yalapay.view.customer.CustomerDetails
import cmps312.yalapay.view.customer.CustomersList
import cmps312.yalapay.view.dashboard.DashboardScreen
import cmps312.yalapay.view.invoice.*

@Composable
fun AppNavigator(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController,
            startDestination = Screen.LoginScreen.route,
            modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = Screen.LoginScreen.route) {
           LoginScreen {
                navController.navigate(Screen.DashboardScreen.route)
            }
        }

        composable(route = Screen.DashboardScreen.route) {
            DashboardScreen()
        }

        composable(route = Screen.MainScreen.route) {
            MainScreen()
        }

        ///////////////////////////////////////customer/////////////////////////////////////////////

        composable(route = Screen.CustomersList.route) {
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

        ////////////////////////////////////////invoice/////////////////////////////////////////////
        composable(route = Screen.InvoicesList.route) {
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
                    navController.navigate(Screen.InvoicesList.route)
            }, onUpdatePayment = {
                    navController.navigate(Screen.PaymentScreen.route)
            })
        }

        composable(route = Screen.PaymentScreen.route) {
            PaymentScreen {
                navController.navigateUp()
            }
        }
        /////////////////////////////////////cheque/////////////////////////////////////////////////

        composable(route = Screen.ChequeList.route) {
            ChequeList(onChequesSelected = {
                navController.navigate(Screen.SelectAccount.route)
            })
        }

        composable(route = Screen.SelectAccount.route) {
            SelectAccount(
                onDepositClick = {
                    navController.navigate(Screen.ChequeList.route)
                }
            )
        }

        composable(Screen.ChequeDepositList.route) {
            ChequeDepositList(
                onReturnedSelected = {
                    navController.navigate(Screen.SetReturnReason.route)
                },
                onSelectSet = {
                    navController.navigate(Screen.SetChequesStatus.route)
                }
            )
        }

        composable(Screen.SetReturnReason.route) {
            SetReturnReason(
                onSetSelected = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.SetChequesStatus.route) {
            SetChequesStatus(
                onSetSelected = {
                    navController.navigateUp()
                }
            )
        }

        /////////////////////////////////////////report/////////////////////////////////////////////

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