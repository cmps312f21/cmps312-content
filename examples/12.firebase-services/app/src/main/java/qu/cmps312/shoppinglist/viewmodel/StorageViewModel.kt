package qu.cmps312.shoppinglist.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class StorageViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val storageRef = Firebase.storage.reference
    var bitmap by mutableStateOf<Bitmap?>(null)
    val imageUrls = mutableStateListOf<String>()
    var errorMessage by mutableStateOf("")

    init {
        getImages()
    }

    fun downloadImage(filename: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val maxDownloadSize = 5L * 1024 * 1024 // 5 MB
            val bytes = storageRef.child("images/$filename").getBytes(maxDownloadSize).await()
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            errorMessage = e.message ?: "Download Failed"
        }
    }

    fun getImages() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val images = storageRef.child("images/").listAll().await()
            imageUrls.clear()
            for (image in images.items) {
                val url = image.downloadUrl.await()
                imageUrls.add(url.toString())
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "Get Files Failed"
        }
    }

    fun deleteImage(filename: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            storageRef.child("images/$filename").delete().await()
            //displayMessage(context, "Image successfully deleted")
        } catch (e: Exception) {
            errorMessage = e.message ?: "Delete Failed"
        }
    }

    fun uploadImage(selectedImageUri: Uri, filename: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                storageRef.child("images/$filename").putFile(selectedImageUri).await()
                //displayMessage(context, "Image successfully uploaded")
            } catch (e: Exception) {
                errorMessage = e.message ?: "Upload Failed"
            }
        }
}