package cmps312.yalapay.repository

import android.content.Context

fun readData(context: Context , filename: String) =
        context.assets.open(filename).bufferedReader().use { it.readText() }