package com.example.logindata

import java.net.HttpURLConnection

class LoginTest {

    /*private val appDatabaseFake = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl : HttpUrl

    //system in test
    private lateinit var login : LoginDataSource

    //dependencies
    private lateinit var service: DevConnectApiAuthService
    private lateinit var accountDaoFake: AccountDaoFake
    private lateinit var authTokenDao: AuthTokenDaoFake
    private lateinit var dataStore: AppDataStore


    @BeforeEach
    fun setUp(){

        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("login/")

        service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DevConnectApiAuthService::class.java)

        accountDaoFake = AccountDaoFake(appDatabaseFake)
        authTokenDao = AuthTokenDaoFake(appDatabaseFake)
        dataStore = AppDataStoreManagerFake()


        //instantiate the system test
        login = LoginDataSource(
            service = service,
            accountDao = accountDaoFake,
            authTokenDao = authTokenDao,
            appDataStoreManager = dataStore
        )
    }


    @Test
    fun loginSucess() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(AuthResponse.AuthSuccess)
        )

        val email = AuthResponse.email
        val password = AuthResponse.password
        val token = AuthResponse.token

        //confirm no authToken is stored in datastore
        var cacheToken = authTokenDao.searchByEmail(email)
        assert(cacheToken == null)

        var cachedAccount = accountDaoFake.searchByEmail(email)
        assert(cachedAccount == null)

        var storedEmail = dataStore.readValue(Constant.PREVIOUS_AUTH_USER)
        assert(storedEmail == null)
    }*/
}