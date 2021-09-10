package qa.edu.cmps312.compose

import androidx.compose.runtime.Composable
import qa.edu.cmps312.compose.basics.HelloScreen

sealed class NavigationItem(var route: String, var title: String, var icon: Int? = null) {
    object Hello : NavigationItem("hello", "Hello (Compose Basics)", R.drawable.ic_home)
    object ClicksCounter : NavigationItem("clicks-counter", "Clicks Counter (Stateless Composable)")
    object ComposeLogo : NavigationItem("compose-logo", "Compose Logo (Card)")
    object Welcome : NavigationItem("welcome", "Welcome (Stateful Composable)")
    object Counter : NavigationItem("counter", "Counter (Modifier.clickable)")
    object SurahList : NavigationItem("surah-list", "Surah (Display List)")
    object Surahs : NavigationItem("surahs", "Surah List (Display using LazyColumn)")
    object Divider: NavigationItem("Divider", "Divider")
}