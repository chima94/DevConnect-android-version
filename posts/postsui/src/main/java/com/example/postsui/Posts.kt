package com.example.postsui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

val imageUri = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80"

@Composable
fun Posts(){

    Scaffold(
        content = {

            Row {
                AuthorImage()
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ){
                    AuthorName()
                    Text(
                        text = "I think i should tweet more about blockchain, it's definitely the new tech" +
                                "everyone should be part of the revolution",
                        style = MaterialTheme.typography.body1
                    )
                    PostIconSection()
                    Divider(thickness = 0.5.dp)
                }
            }
        }
    )
}


@Composable
fun AuthorName(){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Chima Nwakigwe.",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = "12m",
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AuthorImage(){
    val remoteImage = rememberImagePainter(data = imageUri)
    Image(
        painter = remoteImage,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
            .clip(CircleShape)
    )
}

@Composable
fun PostIconSection(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(onClick = { /*TODO*/ }) {
            Row{
                Icon(
                    painter = painterResource(id = com.example.drawables.R.drawable.speech_bubble),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.LightGray
                )
                Text(
                    text = "3",
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.caption
                )
            }
        }

        IconButton(onClick = { /*TODO*/ }) {
            Row{
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.LightGray
                )
                Text(
                    text = "5",
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.caption
                )
            }
        }


        IconButton(onClick = { /*TODO*/ }) {
            Row{
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.LightGray
                )
            }
        }
    }
}