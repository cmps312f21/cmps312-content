package cmps312.yalapay.view.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt

@Composable
fun Datepicker(dateLabel: String,
               initialDate: LocalDate,
               onDateChange: (LocalDate)->Unit,
               enabled: Boolean = true
) {
    val context = LocalContext.current

    val selectedDate = remember { mutableStateOf( "${initialDate.dayOfMonth}/${initialDate.monthNumber}/${initialDate.year}" ) }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            // Month is 0 based so add 1
            selectedDate.value = "$dayOfMonth/${month+1}/$year"
            onDateChange( LocalDate(year, month+1, dayOfMonth) )
        },
        initialDate.year, initialDate.monthNumber-1, initialDate.dayOfMonth
    )

    OutlinedTextField(
        value = initialDate.toString(),
        onValueChange = {
            val selectedDate =
                if (it.isNotEmpty())
                    LocalDate.parse(it)
                else
                    Clock.System.todayAt(TimeZone.currentSystemDefault())

            onDateChange(selectedDate)
        },
        label = { Text(dateLabel) },
        modifier = Modifier.clickable(
                enabled = enabled,
                onClick = {
                    datePickerDialog.show()
                }),
        enabled = false,
        singleLine = true,
        trailingIcon = {
            if (enabled)
                IconButton(onClick = {
                    datePickerDialog.show()
                }, enabled = true) {
                    Icon(imageVector = Icons.Filled.CalendarToday, "")
                }
        },
        colors = if (enabled) TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Black,
            disabledLabelColor = Color.DarkGray
        ) else TextFieldDefaults.textFieldColors()
    )
}