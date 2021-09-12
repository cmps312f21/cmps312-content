package qa.edu.cmps312.compose

sealed class NavigationItem(var route: String, var title: String, var icon: Int? = null) {
    object Hello : NavigationItem("hello", "Hello (Compose Basics)", R.drawable.ic_home)
    object ComposeLogo : NavigationItem("compose-logo", "Compose Logo (Card)")

    object ClicksCounter : NavigationItem("clicks-counter", "Clicks Counter (Manage State)")
    object Welcome : NavigationItem("welcome", "Welcome (Manage State)")

    object Styling : NavigationItem("styling", "Styling using Modifiers")
    object Clickable : NavigationItem("clickable", "Clickable text (Modifier.clickable)")
    object Divider: NavigationItem("Divider", "Divider")
}