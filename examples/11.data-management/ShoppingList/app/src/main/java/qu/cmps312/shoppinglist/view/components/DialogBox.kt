package qu.cmps312.shoppinglist.view.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

enum class DialogResult { CONFIRM, CANCEL }
@Composable
fun DialogBox(title: String, confirmButtonLabel: String,
              content: @Composable (() -> Unit)? = null,
              onDialogResult: (DialogResult)-> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog
            // or on the back button
            //onDialogResult(DialogResult.CANCEL)
        },
        title = {
            Text(text = title)
        },
        text = content,
        confirmButton = {
            Button(
                onClick = { onDialogResult(DialogResult.CONFIRM) }) {
                Text(text = confirmButtonLabel)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDialogResult(DialogResult.CANCEL) }) {
                Text("Cancel")
            }
        }
    )
}