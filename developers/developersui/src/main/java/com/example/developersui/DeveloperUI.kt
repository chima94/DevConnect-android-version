package com.example.developersui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.developercomponent.Developers
import com.example.developersdata.DeveloperViewModel
import com.example.errorcomponent.ErrorMessage
import com.example.errorcomponent.ErrorWithRetry
import com.example.paging.PagingUIProviderViewModel
import com.example.paging.appendState
import com.funkymuse.composed.core.rememberBooleanDefaultFalse
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DevelopersUI(){

    val viewModel : DeveloperViewModel = hiltViewModel()
    val pagingUIUIProvider: PagingUIProviderViewModel = hiltViewModel()

    var progressVisibility by rememberBooleanDefaultFalse()

    val pagingItems = viewModel.developerData.collectAsLazyPagingItems()


     progressVisibility = pagingUIUIProvider.progressBarVisibility(pagingItems)


    val scope = rememberCoroutineScope()


        pagingUIUIProvider.onPaginationReachError(
            pagingItems.appendState,
            com.example.strings.R.string.no_more_developers_to_load
        )




    val retry = {
        pagingItems.refresh()
    }



        Scaffold(
            content = {
                Box(modifier = Modifier.fillMaxSize()){
                    AnimatedVisibility(
                        visible = progressVisibility,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .wrapContentSize()
                            .padding(top = 8.dp)
                            .zIndex(2f)
                    ) {
                        CircularProgressIndicator()
                    }

                    pagingUIUIProvider.OnError(
                        scope = scope,
                        pagingItems = pagingItems,
                        noInternetUI = {
                            ErrorMessage(com.example.strings.R.string.no_developer_loaded_no_connect)
                        },
                        errorUI = {
                            ErrorWithRetry(com.example.strings.R.string.no_developer_loaded_no_connect) {
                                retry()
                            }
                        }
                    )
                    val columnState = rememberLazyListState()

                    val swipeToRefreshState = rememberSwipeRefreshState(isRefreshing = false)
                    SwipeRefresh(
                        state = swipeToRefreshState,
                        onRefresh = {
                            swipeToRefreshState.isRefreshing = true
                            retry()
                            swipeToRefreshState.isRefreshing = false
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
                        ) {
                            items(pagingItems, key = {
                                it.id
                            }
                            ){item ->
                                item ?: return@items
                                Developers(item)
                            }
                        }
                    }
                }
            }
        )
    }




