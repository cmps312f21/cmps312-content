package cmps312.yalapay.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ImageDialog(openDialog: Boolean,
                imageResourceId: Int,
                onOpenDialogChange: (Boolean)->Unit) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                onOpenDialogChange(false)
            },
            {
                Image(
                    painterResource(imageResourceId), contentDescription = "",
                    modifier = Modifier.graphicsLayer { rotationZ = 90F }
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}