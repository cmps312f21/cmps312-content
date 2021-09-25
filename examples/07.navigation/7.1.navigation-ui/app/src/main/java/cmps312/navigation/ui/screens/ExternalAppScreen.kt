package cmps312.navigation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Other common intent examples available @ https://developer.android.com/guide/components/intents-common.html
@Composable
fun ExternalAppScreen() {
    /*val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "profile") {
        composable("profile") { Profile(/*...*/) }
        composable("friendslist") { FriendsList(/*...*/) }

        composable(
            "profile?userId={userId}",
            arguments = listOf(navArgument("userId") { defaultValue = "me" })
        ) { backStackEntry ->
            Profile(navController, backStackEntry.arguments?.getString("userId"))
        }

        /*...*/
        val uri = "https://example.com"

        composable(
            "profile?id={id}",
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/{id}" })
        ) { backStackEntry ->
            Profile(navController, backStackEntry.arguments?.getString("id"))
        }
    }

    @Composable
    fun Profile(navController: NavController) {
        /*...*/
        Button(onClick = { navController.navigate("friends") }) {
            Text(text = "Navigate next")
        }
        /*...*/

        // Pop everything up to the "home" destination off the back stack before
        // navigating to the "friends" destination
        navController.navigate("friends") {
            popUpTo("home")
        }

        // Pop everything up to and including the "home" destination off
        // the back stack before navigating to the "friends" destination
        navController.navigate("friends") {
            popUpTo("home") { inclusive = true }
        }

        // Navigate to the "search” destination only if we’re not already on
        // the "search" destination, avoiding multiple copies on the top of the
        // back stack
        navController.navigate("search") {
            launchSingleTop = true
        }

        NavHost(startDestination = "profile/{userId}") {
            //...
            composable(
                "profile/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
            ) { backStackEntry ->
                Profile(navController, backStackEntry.arguments?.getInt("userId"))
        }
    }*/

    val quLatitude = "25.3773"
    val quLongitude = "51.4912"
    val context = LocalContext.current
    Column(Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                openMap(context, latitude = quLatitude, longitude = quLongitude)
            }) {
            Text(
                text = "Open QU map"
            )
        }

        Button(
            onClick = {
                openUri(context, uri = "https://www.qu.edu.qa")
            }) {
            Text(
                text = "Open QU website"
            )
        }

        Button(
            onClick = {
                sendEmail(
                    context,
                    toEmail = "compose@test.com",
                    subject = "Compose is cool!",
                    message = "Join us"
                )
            }) {
            Text(
                text = "Send an email"
            )
        }

        Button(
            onClick = {
                shareContent(
                    context, content = "Jetpack Compose is cool!"
                )
            }) {
            Text(
                text = "Share Content"
            )
        }

        Button(
            onClick = {
                dialPhoneNumber(
                    context, "111"
                )
            }) {
            Text(
                text = "Call Ooredoo"
            )
        }
    }
}

fun openMap(context: Context, latitude: String, longitude: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("geo:$latitude, $longitude"),
    )
    intent.setPackage("com.google.android.apps.maps")
    context.startActivity(intent)
}

fun openUri(context: Context, uri: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(uri)
    )
    context.startActivity(intent)
}

fun sendEmail(context: Context, toEmail: String, subject: String, message: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:") // only email apps should handle this
        putExtra(Intent.EXTRA_EMAIL, arrayOf(toEmail))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(Intent.createChooser(intent, "Choose an Email client"))
}

fun shareContent(context: Context, content: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}

fun dialPhoneNumber(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    context.startActivity(intent)
}

@Preview
@Composable
fun ExternalAppScreenPreview() {
    ExternalAppScreen()
}