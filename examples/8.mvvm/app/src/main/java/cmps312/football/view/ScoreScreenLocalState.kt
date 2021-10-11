package cmps312.football.view

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.football.R
import cmps312.football.ui.theme.FootballMVVMTheme
import cmps312.football.viewmodel.ScoreViewModel

@Composable
fun ScoreScreenLocalState() {
    // Ticket from MVVM police => ViewModel (manage state)
    var team1Score = remember { mutableStateOf(0) }
    var team2Score = remember { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .weight(3F)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TeamScore(
                modifier = Modifier.weight(1F),
                iconId = R.drawable.img_rayyan,
                score = team1Score.value
            ) {
                team1Score.value++
            }
            TeamScore(
                modifier = Modifier.weight(1F),
                iconId = R.drawable.img_alkhor,
                score = team2Score.value
            ) {
                team2Score.value++
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.remaining_time),
            )
            Text(
                textAlign = TextAlign.Center,
                text = "5 mins"
            )
        }
    }

    // Watch the Composable Lifecycle
    // This function is called when the Composable enters the Composition
    LaunchedEffect(Unit) {
        Log.d("LifeCycle->Compose", "onActive ScoreScreen.")
    }
    DisposableEffect(Unit) {
        // onDispose() is called when the Composable leaves the composition
        onDispose {
            Log.d("LifeCycle->Compose", "onDispose ScoreScreen.")
        }
    }
}
