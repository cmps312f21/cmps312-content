package qa.edu.cmps312.compose

sealed class NavigationItem(var route: String, var title: String, var icon: Int? = null) {
    object Hello : NavigationItem("hello", "Hello (Compose Basics)", R.drawable.ic_home)
    object ClicksCounter : NavigationItem("clicks-counter", "Clicks Counter (Stateless Composable)")
    object ComposeLogo : NavigationItem("compose-logo", "Compose Logo (Card)")
    object Welcome : NavigationItem("welcome", "Welcome (Stateful Composable)")
    object Counter : NavigationItem("counter", "Counter (Modifier.clickable)")
    object Responsive : NavigationItem("responsive", "Responsive Screen (Responsive Design)")
    object Buttons : NavigationItem("buttons", "Buttons")
    object Divider: NavigationItem("Divider", "Divider")
}