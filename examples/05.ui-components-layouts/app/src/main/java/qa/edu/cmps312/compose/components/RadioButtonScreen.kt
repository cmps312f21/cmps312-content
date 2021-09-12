package qa.edu.cmps312.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RadioButtonScreen(){
    val languageOptions = listOf("Java", "Kotlin", "JavaScript")
    val ideOptions = listOf("Android Studio", "Visual Studio", "IntelliJ Idea", "Eclipse")
    var selectedLanguage by remember {
        mutableStateOf(languageOptions[0])
    }
    var selectedIde by remember {
        mutableStateOf(ideOptions[0])
    }

    Column {
        RadioButtonGroup(
            radioOptions = languageOptions,
            selectedOption = selectedLanguage,
            onOptionSelected = { selectedLanguage = it },
            title = "Which is your most favorite language?",
            cardBackgroundColor = Color(0xFFFFFAF0)
        )

        RadioButtonGroup(
            radioOptions = ideOptions,
            title = "Which is your most favorite IDE?",
            selectedOption = selectedIde,
            onOptionSelected = { selectedIde = it },
            cardBackgroundColor = Color(0xFFF8F8FF)
        )

        Text(
            text = "Selected : $selectedLanguage & $selectedIde",
            fontSize = 22.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color(0xFF665D1E)
        )
    }
}

@Composable
fun RadioButtonGroup(
    radioOptions: List<String> = listOf(),
    title: String = "",
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    cardBackgroundColor: Color = Color.LightGray
) {
    if (radioOptions.isNotEmpty()) {
        Card(
            backgroundColor = cardBackgroundColor,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp),
        ) {
            // Modifier.selectableGroup() is essential to ensure correct accessibility behavior
            Column(Modifier.padding(10.dp)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp),
                )

                radioOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = { onOptionSelected(option) }
                            )
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = { onOptionSelected(option) }
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

@Preview
@Composable
fun RadioButtonScreenPreview() {
    RadioButtonScreen()
}