package com.chan.movie.data

import android.content.Context
import androidx.startup.Initializer
import com.chan.movie.BuildConfig
import com.chan.movie.data.di.NetworkModule
import com.chan.movie.data.source.MovieApi
import com.chan.movie.data.source.MovieDataSourceImpl
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

internal class SearchManagerInitializer : Initializer<MovieResponse> {

    override fun create(context: Context): MovieResponse {

        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkModule.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setDateFormat(
                        NetworkModule.NAVER_DATE_FORMAT
                    ).create()
                )
            )
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor()
                        .apply {
                            level = if (BuildConfig.DEBUG) {
                                HttpLoggingInterceptor.Level.BODY
                            } else {
                                HttpLoggingInterceptor.Level.NONE
                            }
                        })
                    .addInterceptor(
                        HeaderInterceptor(
                            mapOf(
                                NetworkModule.NAVER_ID_KEY to BuildConfig.NAVER_CLIENT_ID,
                                NetworkModule.NAVER_SECRET_KEY to BuildConfig.NAVER_CLIENT_SECRET
                            )
                        )
                    ).build()
            ).build().create(MovieApi::class.java)

        val retrofitService = MovieDataSourceImpl(retrofit)
        movieResponse = MovieResponse()

        CoroutineScope(Dispatchers.IO).launch {
            retrofitService.fetchMovies(1, "abc").collect {
                movieResponse = it
            }
            Timber.d("SearchManagerInitializer is $movieResponse")
        }
        return movieResponse
    }


    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    /**
     * TODO : App Startup이 라이브러리 초기화는 참 좋은데 앱내 코드에 데이터를 제공 하는 형태라면 이 방법 뿐일까?..
     */
    companion object {
        var movieResponse = MovieResponse()
            private set
    }

}