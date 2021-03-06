package cmps312.compose.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import cmps312.compose.model.SurahRepository
import cmps312.compose.ui.theme.ComposeListsTheme

enum class SortBy(val label: String) {
    SURAH_NUMBER("Sort by Surah number"),
    SURAH_NUMBER_DESC("Sort by Surah number - descending"),
    SURAH_NAME("Sort by Surah name"),
    SURAH_NAME_DESC("Sort by Surah name - descending"),
    AYA_COUNT("Sort by aya count"),
    AYA_COUNT_DESC("Sort by by aya count - descending")
}

@Composable
fun SurahScreen() {
    var searchText by remember { mutableStateOf("") }
    var surahType by remember {
        mutableStateOf("All")
    }

    var sortBy by remember {
        mutableStateOf(SortBy.SURAH_NUMBER)
    }

    Scaffold(
        topBar = {
            TopBar(
                searchText = searchText,
                onSearchTextChange = { searchText = it },
                surahType,
                onSurahTypeChange = { surahType = it },
                onSortByChange = { sortBy = it }
            )
        }
    ) {
        val surahs = SurahRepository.getSurahs(LocalContext.current)
        SurahList(surahs, surahType, searchText, sortBy)
    }
}

@Preview
@Composable
fun SurahListScreenPreview() {
    ComposeListsTheme {
        SurahScreen()
    }
}