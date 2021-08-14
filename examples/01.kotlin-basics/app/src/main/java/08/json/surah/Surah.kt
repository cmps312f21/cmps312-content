package json.surah

import kotlinx.serialization.Serializable

/*
To be able use @Serializable and Json class you need to add these dependencies then sync:
1) Add to dependencies of the 1st (Project) build.gradle:
    //Added for Kotlin Serialization
    classpath "org.jetbrains.kotlin:kotlin-serialization:$kotlin_version"

2) Add to dependencies of the 2nd (Module) build.gradle
    //Added for Kotlin Serialization
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC"

3) Add this apply plugin to the 2nd build.gradle before line "android {"
    //Added for Kotlin Serialization
    apply plugin: 'kotlinx-serialization'
 */

@Serializable
data class Surah (
    val id : Int,
    val name: String,
    val englishName : String,
    val ayaCount : Int,
    val type: String
)