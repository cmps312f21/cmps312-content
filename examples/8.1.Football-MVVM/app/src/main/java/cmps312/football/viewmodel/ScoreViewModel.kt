package cmps312.football.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmps312.football.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@SuppressLint("LongLogTag")
class ScoreViewModel : ViewModel() {
     private val TAG = "LifeCycle->ScoreViewModel ✔"

    var team1Score = mutableStateOf(0)
    var team2Score = mutableStateOf(0)

    fun onIncrementTeam1Score() {
        team1Score.value++
    }

    fun onIncrementTeam2Score() {
        team2Score.value++
    }

    val redCardsCount: LiveData<Int> = DataRepository.getRedCardsCount()

    val newsFlow: Flow<String> = DataRepository.getNews()

    val timeRemainingFlow: Flow<String> =
        DataRepository.countDownTimer(5)
    /*val timeRemainingFlow: StateFlow<String> =
        DataRepository.countDownTimer(5)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = ""
            )*/

    init {
        Log.d(TAG, "Created")
    }

    @SuppressLint("LongLogTag")
    override fun onCleared() {
        Log.d(TAG, "☠️☠️ onCleared ☠️☠️")
        super.onCleared()
    }
}