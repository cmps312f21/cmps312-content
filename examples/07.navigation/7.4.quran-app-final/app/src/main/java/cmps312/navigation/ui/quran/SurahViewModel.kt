package cmps312.navigation.ui.quran

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import cmps312.navigation.model.SurahRepository

class SurahViewModel (appContext: Application) : AndroidViewModel(appContext) {
    /*
    lazy() is a function that takes a lambda to initialize surahs
    The first call to get surahs executes the lambda passed to lazy()
    to initialize surahs
    subsequent calls simply return the remembered surahs.
    surahs is a lazy property!
    */
    val surahs =
          SurahRepository.getSurahs(appContext)
    //}

    fun getSurah(surahId: Int) = surahs.find { it.id == surahId }
}