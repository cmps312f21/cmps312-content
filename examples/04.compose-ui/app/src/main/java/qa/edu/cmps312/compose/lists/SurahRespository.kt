package qa.edu.cmps312.compose.lists

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object SurahRepository {
    var surahs = listOf<Surah>()

    fun getSurahs(context: Context): List<Surah> {
        if (surahs.isEmpty()) {
            val surahsJson = context.assets
                .open("surahs.json")
                .bufferedReader()
                .use { it.readText() }
            surahs = Json.decodeFromString(surahsJson)
        }
        return surahs
    }
}