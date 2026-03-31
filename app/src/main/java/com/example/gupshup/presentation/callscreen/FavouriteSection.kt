package com.example.gupshup.presentation.callscreen

import android.media.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gupshup.R

@Composable
@Preview(showSystemUi = true)
fun FavouriteSection() {

    val sampleFavourites= listOf(
        FavouriteContacts(image = R.drawable.dipika_rohila, name = "Dipika"),
        FavouriteContacts(image = R.drawable.himanshu, name = "Himanshu"),
        FavouriteContacts(image = R.drawable.prakhar, name = "Prakhar"),
        FavouriteContacts(image = R.drawable.dipika_rohila, name = "Dipika"),
        FavouriteContacts(image = R.drawable.himanshu, name = "Himanshu"),
        FavouriteContacts(image = R.drawable.prakhar, name = "Prakhar"),

        )

    Column(modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)) {

        Text(
            "Favourites",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())) {

            sampleFavourites.forEach {
                FavouriteItems(it)
            }

        }
    }

}
data class FavouriteContacts(
    val image: Int, val name: String
)