package cmps312.coroutines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import cmps312.coroutines.ui.theme.CoroutinesTheme
import cmps312.coroutines.view.WhyCoroutinesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutinesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WhyCoroutinesScreen()
                }
            }
        }
    }
}