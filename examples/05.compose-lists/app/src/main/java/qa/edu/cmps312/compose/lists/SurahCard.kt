package qa.edu.cmps312.compose.lists

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qa.edu.cmps312.compose.R
import qa.edu.cmps312.compose.ui.theme.ComposeListsTheme

@Composable
fun SurahCard(surah: Surah) {
    val lightYellow = Color(android.graphics.Color.rgb(255,255,241))
    val lightGreen = Color(android.graphics.Color.rgb(199, 246, 182))
    Card (elevation = 10.dp,
        backgroundColor = if (surah.type == "Medinan") lightGreen else lightYellow,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        Row (verticalAlignment = Alignment.CenterVertically,
             horizontalArrangement = Arrangement.spacedBy(4.dp),
             modifier = Modifier.padding(5.dp)
        ) {
            val imgResourceId = if (surah.type == "Medinan") R.drawable.ic_madina
                                else R.drawable.ic_mecca
            Image(painter = painterResource(id = imgResourceId),
                  contentDescription = "Surah Type",
                  Modifier.height(50.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(text = "${surah.id}. ${surah.name} - ${surah.englishName}")
                Text(text = "Aya count: ${surah.ayaCount}")
            }
        }
    }
}

@Preview
@Composable
fun SurahCardPreview() {
    ComposeListsTheme {
        SurahCard(
            Surah(
                id = 1, name = "الفاتحة",
                englishName = "Al-Fatiha", ayaCount = 7
            )
        )
    }
}