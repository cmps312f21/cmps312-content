package cmps312.compose.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmps312.compose.R
import cmps312.compose.ui.theme.AppTheme

data class Artist(val firstName: String,
                  val lastName: String,
                  val country: String,
                  val birthYear: Int, val deathYear: Int,
                  val artWorksCount: Int,
                  val paintingId: Int)

@Composable
fun ArtistCardScreen() {
    val alfred = Artist("Alfred", "Sisley", "France",
                1839, 1899,
                 471, R.drawable.img_alfred_sisley_painting)
    ArtistCard(alfred)
}

@Composable
fun ArtistCard(artist: Artist) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Card(
            backgroundColor = Color(0xFFFFFAF0),
            modifier = Modifier.fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(4.dp),
        ) {
            Row {
                Box(Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.img_alfred_sisley),
                        contentDescription = "${artist.firstName} ${artist.lastName}",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.Transparent, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorite",
                        modifier = Modifier.align(Alignment.BottomEnd),
                        tint = Color.Red
                    )
                }
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("${artist.firstName} ${artist.lastName}", fontWeight = FontWeight.Bold)
                    Text(
                        "${artist.birthYear}-${artist.deathYear} (${artist.country})",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.ExtraBold,
                                                  color = Color.Blue)
                            ) {
                                append("${artist.artWorksCount}")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append(" Artworks")
                            }
                        }
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = artist.paintingId),
            contentDescription = "${artist.firstName} ${artist.lastName} Painting",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun UserCardScreenPreview() {
    AppTheme {
        ArtistCardScreen()
    }
}
