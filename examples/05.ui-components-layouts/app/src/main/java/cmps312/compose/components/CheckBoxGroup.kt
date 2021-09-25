package cmps312.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxGroup(
    options: Map<String, Boolean>,
    title: String = "",
    onCheckedChange: (String, Boolean) -> Unit,
    cardBackgroundColor: Color = Color.LightGray
) {
    if (options.isNotEmpty()) {
        Card(
            backgroundColor = cardBackgroundColor,
            modifier = Modifier.fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                if (title.isNotEmpty())
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.dp),
                    )

                options.forEach { (option, isChecked) ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = isChecked,
                                onClick = { onCheckedChange(option, !isChecked) }
                            )
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { onCheckedChange(option, it) }
                        )

                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}