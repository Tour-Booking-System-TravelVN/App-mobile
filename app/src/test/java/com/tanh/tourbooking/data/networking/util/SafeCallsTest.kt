package com.tanh.tourbooking.data.networking.util

import com.google.common.truth.Truth
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SafeCallsTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `server return success`() = runTest {
        val fakeData = "Success"
        val fakeResponse: Response<String> = Response.success(fakeData)

        val result = safeCall { fakeResponse }

        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
        Truth.assertThat((result as Result.Success).data).isEqualTo(fakeData)

    }

    @Test
    fun `server return error`() = runTest {
        val errorResponseBody = ResponseBody.create("application/json".toMediaTypeOrNull(), "Error")
        val fakeResponse: Response<String> = Response.error(500, errorResponseBody)
        val result = safeCall { fakeResponse }
        Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
        Truth.assertThat((result as Result.Error).error).isEqualTo(NetworkError.SERVER_ERROR)
    }

}