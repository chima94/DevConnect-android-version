package com.example.registerdata

import com.example.constants.Constant
import com.example.networkauth.DevConnectApiAuthService
import com.example.util.MessageType
import com.example.util.UIComponentType
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.HTTP
import java.net.HttpURLConnection

class RegisterTest {

    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    //system in test
    private lateinit var register: RegisterDatasource

    //dependencies
    private lateinit var service: DevConnectApiAuthService
    private lateinit var accountDao: AccountDaoFake
    private lateinit var authTokenDao: AuthTokenDaoFake
    private lateinit var dataStore: AppDataStoreManagerFake


    @BeforeEach
    fun setUp(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("user/")
        service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DevConnectApiAuthService::class.java)

        accountDao = AccountDaoFake(appDatabase)
        authTokenDao = AuthTokenDaoFake(appDatabase)
        dataStore = AppDataStoreManagerFake()

        register = RegisterDatasource(
            service = service,
            accountDao = accountDao,
            authTokenDao = authTokenDao,
            appDataStore = dataStore
        )
    }

    @Test
    fun registerSuccess() = runBlocking {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(RegisterResponses.registerSuccess)

        )
        val email = RegisterResponses.email
        val password = RegisterResponses.password
        val token = RegisterResponses.token
        val username = RegisterResponses.username

        var cachedToken = authTokenDao.searchByEmail(email)
        assert(cachedToken == null)

        var cachedAccount = accountDao.searchByEmail(email)
        assert(cachedAccount == null)

        var storedEmail = dataStore.readValue(Constant.PREVIOUS_AUTH_USER)
        assert(storedEmail == null)

        val emissions = register.execute(
            name = username,
            email = email,
            password = password
        ).toList()

        assert(emissions[0].isLoading)

        //confirm cached token
        cachedToken = authTokenDao.searchByEmail(email)
        assert(cachedToken?.account_email == email)
        assert(cachedToken?.token == token)

        //confirm account details
        cachedAccount = accountDao.searchByEmail(email)
        assert(cachedAccount?.email == email)
        assert(cachedAccount?.name == username)
    }


    @Test
    fun registerFail_UserInUser() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody(RegisterResponses.registerSuccess)
        )

        val email = RegisterResponses.email
        val password = RegisterResponses.password
        val token = RegisterResponses.token
        val username = RegisterResponses.username

        //confirm no Account is stored in cache
        var cacheToken = authTokenDao.searchByEmail(email)
        assert(cacheToken == null)

        //confirm no email is stored in datastore
        var storeEmail = dataStore.readValue(Constant.PREVIOUS_AUTH_USER)
        assert(storeEmail == null)

        val emissions = register.execute(
            name = username,
            email = email,
            password = password
        ).toList()

        //first emission should be loading
        assert(emissions[0].isLoading)

        //confirm authtoken is not cached
        cacheToken = authTokenDao.searchByEmail(email)
        assert(cacheToken == null)

        //confirm datastore is not cached
        storeEmail = dataStore.readValue(Constant.PREVIOUS_AUTH_USER)
        assert(storeEmail == null)

        //confirm second emission is an error dialog
        assert(emissions[1].data == null)
        //assert(emissions[1].stateMessage?.response?.message == RegisterResponses.error_msg)
        assert(emissions[1].stateMessage?.response?.uiComponentType is UIComponentType.Dialog)
        assert(emissions[1].stateMessage?.response?.messageType is MessageType.Error)
        assert(!emissions[1].isLoading)
    }
}