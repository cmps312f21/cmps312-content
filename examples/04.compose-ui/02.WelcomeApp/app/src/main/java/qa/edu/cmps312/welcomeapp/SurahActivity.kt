package qa.edu.cmps312.welcomeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qa.edu.cmps312.welcomeapp.ui.theme.ColorChangerTheme

class SurahActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorChangerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SurahScreen()
                }
            }
        }
    }
}


@Composable
fun SurahScreen() {
    SurahRepository.initsurahs(LocalContext.current)
    SurahsList(SurahRepository.surahs)
}

@Composable
fun SurahsList(surahs: List<Surah>) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())
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
    val surahText = "${surah.id}. ${surah.name} ${surah.englishName} (${surah.ayaCount})"
    Text(text = surahText,
            modifier = Modifier.padding(10.dp))
}

@Preview(showBackground = true)
@Composable
fun SurahCardPreview() {
    ColorChangerTheme {
        SurahCard(Surah(id =1, name="الفاتحة",
            englishName = "Al-Fatiha", ayaCount = 7))
    }
}