package cmps312.football

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import cmps312.football.ui.theme.FootballMVVMTheme
import cmps312.football.view.LifeCycleObserver
import cmps312.football.view.ScoreScreen
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FootballMVVMTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ScoreScreen()
                }
            }
        }

        // Get current language and screen orientation
        val language = Locale.getDefault().language
        val screenOrientation = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
            else -> "Portrait"
        }

        //Watch the activity lifecycle events
        LifeCycleObserver(lifecycle,
            tag = "MainActivity ⚽",
            language, screenOrientation
        )
    }
}