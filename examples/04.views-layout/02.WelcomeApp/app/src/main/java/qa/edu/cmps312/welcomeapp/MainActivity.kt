package qa.edu.cmps312.welcomeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qa.edu.cmps312.welcomeapp.ui.theme.ColorChangerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorChangerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WelcomeScreen()
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    // State in an app = any value that can change over time
    // State hoisting in Compose is a pattern of moving state to
    // the caller to make a composable stateless
    var userName by remember { mutableStateOf("Android") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        NameEditor(name = userName, nameChange = { newName -> userName = newName })
        Spacer(modifier = Modifier.size(16.dp))
        Welcome(userName)
    }
}

@Composable
fun NameEditor(name: String, nameChange: (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = { nameChange(it) },
        label = { Text("Your name") }
    )
}

@Composable
fun Welcome(name: String) {
    // Internal state. It observed is => when changed to UI part that uses it get recomposed
    var backgroundColor by remember { mutableStateOf(Color.White) }
    var count by remember { mutableStateOf(0) }

    // Modifiers modify the composable that its applied to.
    // In this example, we configure the column to have a padding of 16 dp
    val padding = 16.dp
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        // Image is a pre-defined composable to display an image
        Image(
            painter = painterResource(R.drawable.img_yahala),
            contentDescription = "Yahala Image",
            modifier = Modifier
                .sizeIn(maxHeight = 400.dp)
                .padding(padding)
        )
        Spacer(Modifier.size(padding))
        Text(text = "Welcome $name!")
        Spacer(Modifier.size(padding))
        Button(onClick = {
             backgroundColor = Color(getRandomColor())
             count += 1
        }) {
            Text(text="Change Color (clicked $count times)")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ColorChangerTheme {
        WelcomeScreen()
    }
}