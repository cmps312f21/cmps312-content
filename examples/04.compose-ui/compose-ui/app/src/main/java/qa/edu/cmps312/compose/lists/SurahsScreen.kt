package qa.edu.cmps312.compose.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SurahsScreen() {
    SurahRepository.getSurahs(LocalContext.current)
    SurahList(SurahRepository.surahs)
}

@Composable
fun SurahList(surahs: List<Surah>) {
    if (surahs.isEmpty()) {
        Text("Loading surahs failed.")
    } else {
        LazyColumn(contentPadding =
            PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(surahs) {
                SurahCard(it)
            }
        }
    }
}

@Preview
@Composable
fun SurahsScreenPreview() {
    SurahsScreen()
}