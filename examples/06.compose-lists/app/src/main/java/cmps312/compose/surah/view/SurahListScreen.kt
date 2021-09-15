package cmps312.compose.surah.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmps312.compose.surah.model.Surah
import cmps312.compose.surah.model.SurahRepository
import cmps312.compose.ui.theme.ComposeListsTheme

enum class SortBy { SURAH_NUMBER, SURAH_NUMBER_DESC, SURAH_NAME, SURAH_NAME_DESC, AYA_COUNT, AYA_COUNT_DESC }

@Composable
fun SurahListScreen() {
    var searchText by remember { mutableStateOf("") }
    var surahType by remember {
        mutableStateOf("All")
    }

    var sortBy by remember {
        mutableStateOf(SortBy.SURAH_NUMBER)
    }

    Scaffold(
        topBar = { TopBar(
            searchText = searchText,
            onSearchTextChange = { searchText = it },
            surahType,
            onSurahTypeChange = { surahType = it},
            onSortByChange = { sortBy = it  }
        )},
    ) {
        SurahList(SurahRepository.getSurahs(LocalContext.current), surahType, searchText, sortBy)
    }
}

@Composable
fun SurahList(surahs: List<Surah>,
              surahType: String,
              searchText: String,
              sortBy: SortBy
) {
    var filteredSurah =
        if (searchText.isNullOrEmpty() && surahType == "All") {
            surahs
        } else {
            surahs.filter {
                (it.englishName.contains(searchText, true) ||
                        it.name.contains(searchText, true) ||
                        searchText.isNullOrEmpty())
                && (it.type == surahType || surahType == "All")
            }
        }

    filteredSurah = sort(filteredSurah, sortBy)

    if (filteredSurah.isEmpty()) {
        Text("No surah found.")
    } else {
        val surahCount = filteredSurah.size
        val ayaCount = filteredSurah.sumOf { it.ayaCount }
        /* contentPadding:
        - add 8.dp of padding to the horizontal edges (left and right),
        - and then 8.dp to the top and bottom of the content
       */
        /*LazyRow(contentPadding =
        PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {*/
        LazyColumn(
            contentPadding =
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
            items(filteredSurah) {
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

fun sort(surahList: List<Surah>, sortBy: SortBy) =
    when (sortBy) {
        SortBy.SURAH_NUMBER -> surahList.sortedBy { it.id }
        SortBy.SURAH_NUMBER_DESC -> surahList.sortedByDescending { it.id }
        SortBy.SURAH_NAME -> surahList.sortedBy { it.englishName }
        SortBy.SURAH_NAME_DESC -> surahList.sortedByDescending { it.englishName }
        SortBy.AYA_COUNT -> surahList.sortedBy { it.ayaCount }
        SortBy.AYA_COUNT_DESC -> surahList.sortedByDescending { it.ayaCount }
    }

/*
Ok to use a Column to display a small list
For displaying a large list, using a Column/Row layout
can cause performance issues since all the items will be composed
and laid out whether or not they are visible

Use a Lazy List (i.e., LazyColumn o LazyRow) to only compose and lay out
items which are visible on screen
 */
@Composable
fun SurahColumn(surahs: List<Surah>) {
    /*Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
    modifier =  Modifier.horizontalScroll(rememberScrollState())*/
    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
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