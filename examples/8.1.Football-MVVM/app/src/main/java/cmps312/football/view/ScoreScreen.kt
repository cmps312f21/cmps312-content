package cmps312.football.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.football.R
import cmps312.football.ui.theme.FootballMVVMTheme
import cmps312.football.viewmodel.ScoreViewModel

@Composable
fun ScoreScreen() {
    // Get an instance of the ScoreViewModel
    val scoreViewModel = viewModel<ScoreViewModel>()
    /*
    var team1Score by remember {
        mutableStateOf(0)
    }

    var team2Score by remember {
        mutableStateOf(0)
    }*/

    val redCardsCount = scoreViewModel.redCardsCount.observeAsState()
    val newsUpdate = scoreViewModel.newsFlow.collectAsState(initial = "")
    val timeRemaining = scoreViewModel.timeRemainingFlow.collectAsState(initial = "")

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    Row(
        modifier = Modifier.weight(3F).fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TeamScore(
            modifier = Modifier.weight(1F),
            iconId = R.drawable.img_rayyan,
            score = scoreViewModel.team1Score.value
        ) { //team1Score) {
            scoreViewModel.onIncrementTeam1Score()
            //team1Score++
        }
        TeamScore(
            modifier = Modifier.weight(1F),
            iconId = R.drawable.img_alkhor,
            score = scoreViewModel.team2Score.value
        ) { //team2Score) {
            scoreViewModel.onIncrementTeam2Score()
            //team2Score++
        }
    }
        // Recomposes whenever redCardsCount changes
        Text(
            modifier = Modifier.weight(0.5F),
            textAlign = TextAlign.Center,
            text = "Red cards count: ${redCardsCount.value}"
        )
        Text(
            modifier = Modifier.weight(0.5F),
            textAlign = TextAlign.Center,
            text = timeRemaining.value
        )
        Text(
            modifier = Modifier.weight(0.5F),
            textAlign = TextAlign.Center,
            text = "\uD83D\uDCE2 ${newsUpdate.value}"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScoreScreenPreview() {
    FootballMVVMTheme {
        ScoreScreen()
    }
}