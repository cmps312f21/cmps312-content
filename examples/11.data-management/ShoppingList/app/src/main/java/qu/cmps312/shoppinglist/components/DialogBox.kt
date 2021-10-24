package qu.cmps312.shoppinglist.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DialogBox(isDialogOpen: Boolean,
              onDialogOpenChange: (Boolean)-> Unit,
              title: String,
              content: @Composable (() -> Unit)? = null,
              onDialogResult: (Boolean)-> Unit,
) {
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog
                // or on the back button
                onDialogOpenChange(false)
            },
            title = {
                Text(text = title)
            },
            text =  content,
            confirmButton = {
                Button(
                    onClick = {  onDialogResult(true) }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {  onDialogResult(false) }) {
                    Text("Cancel")
                }
            }
        )
    }
}