package com.example.developersdata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.dispatchers.IoDispatcher
import com.example.paging.data.PagingDataProvider
import com.example.paging.data.PagingDataSourceHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class DeveloperViewModel @Inject constructor(
    private val developerDataSourceFactory: DeveloperDataSource.DeveloperDataSourceFactory,
    override val savedStateHandle: SavedStateHandle,
    application: Application,
    private val dataProvider: PagingDataProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AndroidViewModel(application), PagingDataSourceHandle{

    private  val developerDataSource
        get() = developerDataSourceFactory.create()

    val developerData =  dataProvider.providePageData(viewModelScope, ioDispatcher){
        developerDataSource
    }

}


