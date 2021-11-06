package qu.cmps312.shoppinglist.view

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import qu.cmps312.shoppinglist.view.components.StorageToolBar
import qu.cmps312.shoppinglist.view.components.displayMessage
import qu.cmps312.shoppinglist.viewmodel.StorageViewModel

@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalFoundationApi
@Composable
fun StorageScreen() {
    val viewModel = viewModel<StorageViewModel>()
    val context = LocalContext.current
    var imageToDownload by remember { mutableStateOf("") }

    val imagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            displayMessage(context, "Select image Uri: $uri")
            println(">> Debug: $uri")
            uri?.let {
                viewModel.uploadImage(it)
            }
        }

    val takePicture =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                viewModel.uploadImage(it)
                displayMessage(context, "Picture uploaded successfully", Toast.LENGTH_SHORT)
            }
        }

    Scaffold(
        topBar = {
            StorageToolBar(
                onUploadPhotoFromGallery = { imagePicker.launch("image/*") },
                onTakePicture = { takePicture.launch() },
                onRefresh = { viewModel.getImageURIs() }
            )
        },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(it)
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                OutlinedTextField(
                    value = imageToDownload,
                    onValueChange = { imageToDownload = it },
                    label = {
                        Text(
                            text = "Image Name"
                        )
                    },
                    placeholder = { Text(text = "Image Name") },
                    singleLine = true
                )
                // Download a Photo
                IconButton(onClick = {
                    if (imageToDownload.isNotEmpty())
                        viewModel.getImageURI(imageToDownload)
                    else
                        displayMessage( context,"Enter the name of the image of download", Toast.LENGTH_SHORT)
                }) {
                    Icon(Icons.Outlined.CloudDownload, null)
                }
            }

            /*if (viewModel.bitmap != null) {
                Image(
                    painter = BitmapPainter(viewModel.bitmap!!.asImageBitmap()),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
            }*/

            if (viewModel.imageUri.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(viewModel.imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .border(2.dp, Color.Magenta)
                )
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                item() {
                    Text("Long click an image to delete it")
                }

                items(viewModel.imageURIs) { imageUri ->
                    //val imageUri = "https://firebasestorage.googleapis.com/v0/b/cmp312-fall2020.appspot.com/o/images%2FFirebase-Cloud-Storage.png?alt=media&token=9ee1d523-c459-4e73-b7f4-8ef70ca57d49"
                    Column() {
                        Image(
                            painter = rememberImagePainter(imageUri),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp)
                                .combinedClickable(
                                    onClick = { },
                                    onLongClick = {
                                        viewModel.deleteImage(imageUri)
                                    }
                                )
                        )
                        Text(text = imageUri.lastPathSegment ?: "")
                    }
                }
            }
        }
    }
}