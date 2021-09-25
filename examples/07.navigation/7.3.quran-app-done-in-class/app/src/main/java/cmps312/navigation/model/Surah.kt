package cmps312.navigation.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
To be able use @Serializable and Json class you need to add these dependencies then sync:
1) Add to dependencies of the 2nd (Module) build.gradle
    //Added for Kotlin Serialization
    implementation 'org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2'

2) Add this apply plugin to the 2nd build.gradle before line "android {"
    //Added for Kotlin Serialization
    id 'org.jetbrains.kotlin.plugin.serialization' version '1.5.30'
 */

@Serializable
data class Verse(val id : Int, val text: String)

@Serializable
data class Surah (
    val id : Int,
    val name: String,
    @SerialName("transliteration")
    val englishName : String,
    @SerialName("total_verses")
    val ayaCount : Int,
    val type: String = "Meccan",
    val verses: List<Verse>
)