package com.example.developersdata

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.crazylegend.common.isOnline
import com.crazylegend.retrofit.throwables.NoConnectionException
import com.example.cacheauth.AuthTokenDao
import com.example.constants.Constant
import com.example.datastore.AppDataStore
import com.example.dispatchers.IoDispatcher
import com.example.networkdeveloper.DevelopersApiService
import com.example.networkresponses.developerreponse.Developer
import com.example.networkresponses.developerreponse.DeveloperModel
import com.example.networkresponses.developerreponse.DeveloperModelItem
import com.example.networkresponses.developerreponse.toDevelopers
import com.example.paging.fetchPaginatedContent
import com.example.paging.pagedResult
import com.example.retrofit.handleUseCaseException
import com.example.util.DataState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.coroutineContext

class DeveloperDataSource @AssistedInject constructor(
    private val service: DevelopersApiService,
    private val authTokenDao: AuthTokenDao,
    private val appDataStore: AppDataStore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) : PagingSource<Int, Developer>(){


    private val scope =  CoroutineScope(dispatcher)
    private var token: String = ""

    @AssistedFactory
    interface DeveloperDataSourceFactory{
        fun create(): DeveloperDataSource
    }

    init {
        getToken()
    }

    override fun getRefreshKey(state: PagingState<Int, Developer>): Int? = state.anchorPosition


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Developer> {
        val page = params.key ?: 1
        return if(context.isOnline){
            try {
                val data = withContext(dispatcher){
                    service.getDevelopersProfile(token).toDevelopers()
                }
                pagedResult(data, page)
            }catch (throwable : Throwable){
                LoadResult.Error(throwable)
            }
        }else{
            LoadResult.Error(NoConnectionException())
        }
    }


    private fun getToken(){
        scope.launch {
            appDataStore.readValue(Constant.PREVIOUS_AUTH_USER)?.let {email ->
                token = authTokenDao.searchByEmail(email)?.token!!
            }
        }
    }

}