package qu.cmps312.shoppinglist.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import qu.cmps312.shoppinglist.view.components.displayMessage
import qu.cmps312.shoppinglist.viewmodel.StorageViewModel

@Composable
fun StorageScreen() {
    val viewModel = viewModel<StorageViewModel>()
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            displayMessage(context, "Image Uri: $uri")
            println(">> Debug: $uri")
            selectedImageUri = uri
            viewModel.bitmap = null
            viewModel.uploadImage(selectedImageUri!!, "myImage")
        }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    viewModel.downloadImage("myImage")
                }) {
                Text(text = "Download")
            }

            Button(
                onClick = {
                    imagePicker.launch("image/*")
                }) {
                Text(text = "Upload")
            }

            Button(
                onClick = {
                    selectedImageUri = null
                    viewModel.getImages()
                }) {
                Text(text = "Reload")
            }
        }

        if (viewModel.bitmap != null) {
            Image(
                painter = BitmapPainter(viewModel.bitmap!!.asImageBitmap()),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
        }

        if (selectedImageUri != null) {
            Image(
                painter = rememberImagePainter(selectedImageUri),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(viewModel.imageUrls) { imageUrl ->
                //val imageUrl = "https://firebasestorage.googleapis.com/v0/b/cmp312-fall2020.appspot.com/o/images%2FFirebase-Cloud-Storage.png?alt=media&token=9ee1d523-c459-4e73-b7f4-8ef70ca57d49"
                Image(
                    painter = rememberImagePainter(imageUrl),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}