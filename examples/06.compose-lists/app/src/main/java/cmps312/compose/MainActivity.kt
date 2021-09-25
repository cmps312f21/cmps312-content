package cmps312.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import cmps312.compose.ui.SurahScreen
import cmps312.compose.ui.theme.ComposeListsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeListsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SurahScreen()
                }
            }
        }
    }
}