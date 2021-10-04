package cmps312.football.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cmps312.football.repository.DataRepository
import kotlinx.coroutines.flow.Flow

@SuppressLint("LongLogTag")
class ScoreViewModel : ViewModel() {
     private val TAG = "LifeCycle->ScoreViewModel ✔"

    // Private mutable state variables
    private var _team1Score = mutableStateOf(0)
    private var _team2Score = mutableStateOf(0)

    // Public State read-only variables
    val team1Score : State<Int> = _team1Score
    val team2Score : State<Int> = _team2Score

    fun onIncrementTeam1Score() {
        _team1Score.value++
    }

    fun onIncrementTeam2Score() {
        _team2Score.value++
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
        Log.d(TAG, "☠️☠onCleared ☠☠")
        super.onCleared()
    }
}