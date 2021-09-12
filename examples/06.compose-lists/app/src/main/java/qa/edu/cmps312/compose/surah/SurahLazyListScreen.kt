package qa.edu.cmps312.compose.surah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import qa.edu.cmps312.compose.ui.theme.ComposeListsTheme

@Composable
fun SurahLazyListScreen() {
    SurahRepository.getSurahs(LocalContext.current)
    SurahsLazyList(SurahRepository.surahs)
}

@Composable
fun SurahsLazyList(surahs: List<Surah>) {
    if (surahs.isEmpty()) {
        Text("Loading surahs failed.")
    } else {
        val surahCount = surahs.size
        val ayaCount = surahs.sumOf { it.ayaCount }
        /* contentPadding:
        - add 8.dp of padding to the horizontal edges (left and right),
        - and then 8.dp to the top and bottom of the content
       */
        /*LazyRow(contentPadding =
        PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {*/
        LazyColumn(contentPadding =
            PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "سور القرآن الكريم",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Blue,
                        textDirection = TextDirection.Rtl
                    )
                )
            }
            items(surahs) {
                SurahCard(it)
            }
            item {
                Text(
                    text = "$surahCount سورة  - $ayaCount آية ",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Blue,
                        textDirection = TextDirection.Rtl
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun SurahLazyListScreenPreview() {
    ComposeListsTheme {
        SurahLazyListScreen()
    }
}