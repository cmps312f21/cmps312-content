package qa.edu.cmps312.compose.lists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qa.edu.cmps312.compose.ui.theme.ComposeUITheme

@Composable
fun SurahListScreen() {
    SurahRepository.getSurahs(LocalContext.current)
    SurahsList(SurahRepository.surahs)
}

@Composable
fun SurahsList(surahs: List<Surah>) {
    Column(modifier =
        Modifier.verticalScroll(rememberScrollState())
                .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (surahs.isEmpty()) {
            Text("Loading surahs failed.")
        } else {
            surahs.forEach {
                SurahCard(surah = it)
            }
        }
    }
}

@Composable
fun SurahCard(surah: Surah) {
    val surahText = "${surah.id}. ${surah.name} - ${surah.englishName} (${surah.ayaCount})"
    Card (elevation = 10.dp,
          backgroundColor = Color.LightGray,
          modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = surahText,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview
@Composable
fun SurahCardPreview() {
    SurahListScreen()
}

@Preview
@Composable
fun SurahScreenPreview() {
    ComposeUITheme {
        SurahCard(
            Surah(id =1, name="الفاتحة",
                englishName = "Al-Fatiha", ayaCount = 7)
        )
    }
}