package cmps312.compose

sealed class NavigationItem(var route: String, var title: String, var icon: Int? = null) {
    object TextField: NavigationItem("textfield", "TextField")
    object Button : NavigationItem("button", "Button")
    object RadioButton : NavigationItem("radiobutton", "Radio Button")
    object CheckBox : NavigationItem("checkbox", "CheckBox")
    object Switch : NavigationItem("switch", "Switch")
    object Dropdown : NavigationItem("dropdown", "Dropdown")
    object Slider : NavigationItem("slider", "Slider")

    object Card : NavigationItem("card", "Artist Card")
    object Box : NavigationItem("box", "Box Layout")
    object Responsive : NavigationItem("responsive", "Responsive Design")
    object TipCalculator : NavigationItem("TipCalculator", "Tip Calculator", R.drawable.ic_quran)
    object Divider: NavigationItem("Divider", "Divider")
}