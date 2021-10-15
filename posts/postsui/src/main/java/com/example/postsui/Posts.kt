package com.example.postsui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.example.errorcomponent.ErrorMessage
import com.example.errorcomponent.ErrorWithRetry
import com.example.networkresponses.developerpost.PostItem
import com.example.paging.PagingUIProviderViewModel
import com.example.paging.appendState
import com.example.postsdata.PostViewModel
import com.example.strings.R
import com.example.util.getTimeAgo
import com.funkymuse.composed.core.rememberBooleanDefaultFalse
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.text.SimpleDateFormat

val imageUri = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Posts(){

    val postViewModel : PostViewModel = hiltViewModel()
    val pagingUIUIProvider: PagingUIProviderViewModel = hiltViewModel()

    val userId = postViewModel.userId

    var progressVisibility by rememberBooleanDefaultFalse()

    val pagingItems = postViewModel.postsData.collectAsLazyPagingItems()

    progressVisibility = pagingUIUIProvider.progressBarVisibility(pagingItems)

    val scope = rememberCoroutineScope()


    pagingUIUIProvider.onPaginationReachError(
        pagingItems.appendState,
        R.string.no_more_posts_to_load
    )

    val retry = {
        pagingItems.refresh()
    }

    Scaffold(
        floatingActionButton = {
            FloatActionButton()
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                AnimatedVisibility(
                    visible = progressVisibility,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .wrapContentSize()
                        .padding(8.dp)
                        .zIndex(2f)
                ) {
                    CircularProgressIndicator()
                }

                pagingUIUIProvider.OnError(
                    scope = scope,
                    pagingItems = pagingItems,
                    noInternetUI = {
                        ErrorMessage(R.string.no_post_loaded_no_connect)
                    },
                    errorUI = {
                        ErrorWithRetry(R.string.no_post_loaded_no_connect) {
                            retry()
                        }
                    }
                )
                val columnState = rememberLazyListState()
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        swipeRefreshState.isRefreshing = true
                        retry()
                        swipeRefreshState.isRefreshing = false
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        state = columnState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp),
                        contentPadding = rememberInsetsPaddingValues(
                            insets = LocalWindowInsets.current.navigationBars,
                            additionalBottom = 84.dp
                        )
                    ){
                        items(pagingItems, key ={it.id}) {item ->
                            item ?: return@items
                            PostContents(item, userId!!)
                        }
                    }
                }
            }
        }
    )
}



@Composable
fun PostContents(item: PostItem, id: String) {
    Row {
        AuthorImage()
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ){
            AuthorName(item)
            Text(
                text = item.text,
                style = MaterialTheme.typography.body1
            )
            PostIconSection(item, id)
            Divider(thickness = 0.5.dp)
        }
    }
}

@SuppressLint("NewApi", "SimpleDateFormat")
@Composable
fun AuthorName(item: PostItem) {
    val date = getTimeAgo(item.date)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "${item.name}.",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = date,
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
fun PostIconSection(item: PostItem, id: String) {
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
                    tint = Color.Gray
                )
                Text(
                    text = "${item.comments.size}",
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.Gray,
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
                    tint = if (item.user == id) Color.Red else Color.Gray,

                )
                Text(
                    text = "${item.likes.size}",
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.Gray,
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
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun FloatActionButton(){
    ExtendedFloatingActionButton(
        text = {
            Text(text = stringResource(id = com.example.strings.R.string.post))
        },
        onClick = { /*TODO*/ },
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(com.example.strings.R.string.post)
            )
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.padding(bottom = 50.dp)
    )
}