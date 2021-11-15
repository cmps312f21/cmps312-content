package cmps312.yalapay.view.customer

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.Address
import cmps312.yalapay.entity.ContactDetails
import cmps312.yalapay.entity.Customer
import cmps312.yalapay.entity.FormMode
import cmps312.yalapay.view.components.TopBar
import cmps312.yalapay.view.components.displayMessage
import cmps312.yalapay.viewmodel.CustomerViewModel

@Composable
fun CustomerDetails(onNavigateBack: ()->Unit) {
    val customerViewModel =
        viewModel<CustomerViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val context = LocalContext.current
    val selectedCustomer = customerViewModel.selectedCustomer

    var formMode = FormMode.ADD
    var screenTitle = "Add Customer"

    if (selectedCustomer != null) {
        formMode = FormMode.EDIT
        screenTitle = "Edit Customer (#${selectedCustomer.customerId})"
    }

    var firstName by remember {
        mutableStateOf(selectedCustomer?.contactDetails?.firstName ?: "")
    }
    var lastName by remember {
        mutableStateOf(selectedCustomer?.contactDetails?.lastName ?: "")
    }
    var mobile by remember {
        mutableStateOf(selectedCustomer?.contactDetails?.mobile ?: "")
    }
    var email by remember {
        mutableStateOf(selectedCustomer?.contactDetails?.email ?: "")
    }
    var companyName by remember {
        mutableStateOf(selectedCustomer?.companyName ?: "")
    }
    var street by remember {
        mutableStateOf(selectedCustomer?.address?.street ?: "")
    }
    var city by remember {
        mutableStateOf(selectedCustomer?.address?.city ?: "")
    }
    var country by remember {
        mutableStateOf(selectedCustomer?.address?.country ?: "")
    }

    fun onSubmit() {
        if (firstName.isNotEmpty() && lastName.isNotEmpty() && mobile.isNotEmpty()
            && email.isNotEmpty() && companyName.isNotEmpty()
            && country.isNotEmpty() && city.isNotEmpty() && street.isNotEmpty()
        ) {
            val contactDetails = ContactDetails(firstName, lastName, mobile, email)
            val address = Address(street, city, country)
            val customer = Customer(
                companyName = companyName,
                address = address,
                contactDetails = contactDetails
            )

            if (formMode == FormMode.ADD) {
                customerViewModel.addCustomer(customer)
            } else {
                customer.customerId = selectedCustomer?.customerId!!
                customerViewModel.updateCustomer(customer)
            }
            onNavigateBack()
        } else {
            displayMessage(
                context, "Please enter missing details",
                Toast.LENGTH_SHORT
            )
        }
    }

    Scaffold(
        topBar = {
            TopBar(
            title = screenTitle,
            onNavigateBack = onNavigateBack,
            onSubmit = { onSubmit() })
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            OutlinedTextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text(text = "Company Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text = "First Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text = "Last Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = street,
                onValueChange = { street = it },
                label = { Text(text = "Street name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text(text = "City") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = { Text(text = "Country") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = mobile,
                onValueChange = { mobile = it },
                label = { Text(text = "Mobile") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}