package qa.edu.cmps312.welcomeapp

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object SurahRepository {
    var surahs = listOf<Surah>()
    val count: Int get() = surahs.size

    fun initsurahs(context: Context): List<Surah> {
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