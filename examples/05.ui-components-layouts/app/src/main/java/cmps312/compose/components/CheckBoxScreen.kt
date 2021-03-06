package cmps312.compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxScreen(){
    val languageOptions = remember {
        mutableStateMapOf(
            "Java" to false,
            "Kotlin" to true,
            "JavaScript" to true
        )
    }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CheckBoxGroup(
            title = "Which are your most favorite language?",
            options = languageOptions,
            onCheckedChange = { option, isChecked ->
                languageOptions[option] = isChecked
            },
            cardBackgroundColor = Color(0xFFFFFAF0)
        )

        var options = ""
        languageOptions.forEach { (option, isChecked) ->
            options += "$option ($isChecked)\r\n"
        }

        Text(
            text = options,
            modifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun CheckBoxScreenPreview() {
    CheckBoxScreen()
}