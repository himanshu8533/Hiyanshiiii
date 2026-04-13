package com.example.gupshup.presentation.bottomnavigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.gupshup.R

@Composable
fun BottomNavigation(

    navHostController: NavHostController,
    onClick: (Int) -> Unit,
    selectedItem: Int,
    unSelectedItem: Color

) {

    val items = listOf(
        NavigationItem("Chats", R.drawable.chat, R.drawable.chat),
        NavigationItem("Updates", R.drawable.updates, R.drawable.updates),
        NavigationItem("Reels", R.drawable.reels, R.drawable.reels),
        NavigationItem("Communities", R.drawable.communities, R.drawable.communities),
        NavigationItem("Calls", R.drawable.calling, R.drawable.calling)


    )
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .navigationBarsPadding()
            .height(80.dp)
    ) {

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onClick(index) },
                label = {
                    if (index == selectedItem) {
                        Text(item.name, color = Color.Black)

                    } else {
                        Text(item.name, color = Color.DarkGray)

                    }
                }, icon = {
                    Icon(
                        painter = if (
                            index == selectedItem
                        ) {
                            painterResource(item.selectedIcon)
                        } else {
                            painterResource(item.unSelectedIcon)
                        }, contentDescription = null,
                        tint = if (index == selectedItem) {
                            Color.DarkGray
                        } else {
                            Color.Black
                        },
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                },

                colors = NavigationBarItemDefaults.colors(indicatorColor = colorResource(R.color.mint_Blue))
            )
        }
    }


}

data class NavigationItem(

    val name: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int
)