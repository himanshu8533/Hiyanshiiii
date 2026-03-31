package com.example.gupshup.presentation.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gupshup.R
import com.example.gupshup.presentation.bottomnavigation.BottomNavigation
import com.example.gupshup.presentation.chat_box.ChatDesign
import com.example.gupshup.presentation.chat_box.ChatDesignModel

@Composable
@Preview(showSystemUi = true)
fun HomeScreen(){

    val chatData= listOf(
        ChatDesignModel(
            R.drawable.dipika_rohila,
            name = "Dipika Rohila",
            time = "10:00 AM",
            message = "Hi"
        ),

        ChatDesignModel(
            R.drawable.himanshu,
            name = "Himanshu Kumar",
            time = "11:00 AM",
            message = "Hello"
        ),

        ChatDesignModel(
            R.drawable.prakhar,
            name = "Prakhar Goyal",
            time = "10:00 AM",
            message = "Hi"
        ),

        ChatDesignModel(
            R.drawable.dipika_rohila,
            name = "Dipika Rohila",
            time = "11:00 AM",
            message = "Hello"
        ),

        ChatDesignModel(
            R.drawable.himanshu,
            name = "Himanshu Kumar",
            time = "10:00 AM",
            message = "Hi"
        ),

        ChatDesignModel(
            R.drawable.prakhar,
            name = "Prakhar Goyal",
            time = "11:00 AM",
            message = "Hello"
        ),

        ChatDesignModel(
            R.drawable.dipika_rohila,
            name = "Dipika Rohila",
            time = "10:00 AM",
            message = "Hi"
        ),

        ChatDesignModel(
            R.drawable.himanshu,
            name = "Himanshu Kumar",
            time = "11:00 AM",
            message = "Hello"
        ),


        )



    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {/*TODO*/},
                containerColor = colorResource(id= R.color.Royal_Blue),
                modifier = Modifier.size(65.dp),
                contentColor = Color.White
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.add_chat_icon),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )

            }
        },

        bottomBar = {
            BottomNavigation()
        }
    ) {

        Column(modifier = Modifier.padding(it)) {

            Box(modifier = Modifier.fillMaxWidth()){

                Text(
                    text = "GupShup",
                    fontSize = 28.sp,
                    color = colorResource(id = R.color.Royal_Blue),
                    modifier = Modifier.align(
                        Alignment.CenterStart).padding(start = 16.dp),
                    fontWeight = FontWeight.Bold
                )

                Row(modifier = Modifier.align(Alignment.CenterEnd)) {

                    IconButton(onClick = {/*TODO*/}) {

                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(onClick = {/*TODO*/}) {

                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )

                    }

                    IconButton(onClick = {/*TODO*/}) {

                        Icon(
                            painter = painterResource(id = R.drawable.more),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )

                    }
                }


            }

            HorizontalDivider()

            LazyColumn {

                items(chatData){

                    ChatDesign(
                        chatDesignModel = it
                    )
                }



            }

        }

    }
}