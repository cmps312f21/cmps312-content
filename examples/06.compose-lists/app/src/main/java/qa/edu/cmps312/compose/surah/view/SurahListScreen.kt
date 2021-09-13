package qa.edu.cmps312.compose.surah.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qa.edu.cmps312.compose.surah.model.Surah
import qa.edu.cmps312.compose.surah.model.SurahRepository
import qa.edu.cmps312.compose.ui.theme.ComposeListsTheme

@Composable
fun SurahListScreen() {
    SurahList(SurahRepository.getSurahs(LocalContext.current))
}

@Composable
fun SurahList(surahs: List<Surah>) {
    /*Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
    modifier =  Modifier.horizontalScroll(rememberScrollState())*/
    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.verticalScroll(rememberScrollState())
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .clip(MaterialTheme.shapes.medium),
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

@Preview
@Composable
fun SurahListScreenPreview() {
    ComposeListsTheme {
        SurahListScreen()
    }
}