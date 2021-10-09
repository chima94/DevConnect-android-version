package com.example.developercomponent

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.loadingcomponent.BoxShimmer
import com.example.networkresponses.developerreponse.Developer
import com.example.shape.Shapes

@Composable
fun Developers(
    developer: Developer
){

    Card(
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(Shapes.large),
        elevation = 6.dp
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp, 200.dp),
                contentAlignment = Alignment.Center
            ) {
                AddStaticImage(remoteImage = developer.avatar!!)
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = developer.name!!,
                        maxLines = 3,
                        style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 17.sp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Works at ${developer.company}",
                        maxLines = 3,
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = Color.Gray
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Divider(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                color = Color.Gray,
                thickness = 1.dp,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                TextButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = "Message")
                }

                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TextButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountBox,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = "Profile")
                }
            }

            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray,
                thickness = 1.dp,
            )
        }

    }
}



@Composable
private fun AddStaticImage(remoteImage: String){
    val painter = rememberImagePainter(
        data = "https://$remoteImage"
        )
    val size = Size(85.dp.value, 100.dp.value)

    val imageModifier = Modifier
        .size(size.width.dp, size.height.dp)




    when(painter.state){
        is ImagePainter.State.Loading ->{
            Box(modifier = imageModifier){
                BoxShimmer(
                    padding = 0.dp,
                    imageHeight = size.height.dp,
                    imageWidth = size.width.dp
                )
            }
        }
        is ImagePainter.State.Success,  ImagePainter.State.Empty , is ImagePainter.State.Error->{
            Box(modifier = imageModifier, contentAlignment = Alignment.Center){
                Image(
                    painter = painter,
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colors.primaryVariant, CircleShape),
                    contentDescription = stringResource(com.example.strings.R.string.developer_details)
                )
            }
        }
    }
}