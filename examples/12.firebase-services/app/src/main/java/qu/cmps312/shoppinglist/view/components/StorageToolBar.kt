package qu.cmps312.shoppinglist.view.components

import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun StorageToolBar(
    onUploadPhotoFromGallery: ()-> Unit,
    onTakePicture: ()-> Unit,
    onRefresh: ()-> Unit
) {
    val context = LocalContext.current
    TopAppBar(
        elevation = 4.dp,
        title = {},
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = { },
        actions = {
            // Select a photo from Gallery
            IconButton(onClick = { onUploadPhotoFromGallery() }) {
                Icon(Icons.Outlined.AddPhotoAlternate, null)
            }
            // Take photo using a Camera
            IconButton(onClick = { onTakePicture() }) {
                Icon(Icons.Outlined.AddAPhoto, null)
            }
            // Refresh
            IconButton(onClick = { onRefresh() }) {
                Icon(Icons.Outlined.Refresh, null)
            }
        })
}