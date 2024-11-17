package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.ui.theme.Typography

/*fun main(args: Array<String>) {
    val inputStream: InputStream = File("listofcities.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    lineList.forEach{println(">  " + it)}
}*/


@Composable
fun LocationList(location: String, currTemp: Double, currWeather: String, modifier: Modifier = Modifier) {
        Row(
            //horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Icon(
                Icons.Default.FavoriteBorder, // @TODO change icon when added to favorites
                contentDescription = "favorite icon",
                Modifier
                    .padding(end = 5.dp)
                    .size(36.dp)
            )
            Text(
                style = Typography.bodyLarge,
                textAlign = TextAlign.Right,
                text = "$currTemp°C",
                modifier = Modifier
                    .padding(end = 32.dp)
                    .weight(1f)

            )

//            Icon(
//                Icons.Default.Home, // @TODO icon has to change when weather changes (switch case)
//                // @TODO change icons to real weather icons
//                contentDescription = "Weather icon",
//                Modifier
//                    .padding(start = 24.dp, end = 24.dp) //@TODO align icons to center
//                    .size(36.dp)
//                    .weight(1f)
//            )
            Text(
                style = Typography.bodyLarge,
                text = location,
                modifier = Modifier
                    .weight(2f)

            )

        }
        }


@Preview(showBackground = true)
@Composable
fun LocPrev() {
    Column {
        LocationList("Copenhagen", 23.5, "sunny")
        LocationList("james", 200.0, "sunny")
        LocationList("cuba", -40.0, "sunny")
    }
}
