package com.chan.movie.data.di

import com.chan.movie.BuildConfig
import com.chan.movie.data.HeaderInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun providesHeaderInterceptor() = HeaderInterceptor(
        mapOf(NAVER_ID_KEY to BuildConfig.NAVER_CLIENT_ID,
            NAVER_SECRET_KEY to BuildConfig.NAVER_CLIENT_SECRET)
    )

    @Provides
    @Singleton
    fun providesGson(): Gson =
        GsonBuilder().setDateFormat(NAVER_DATE_FORMAT).create()


    @Provides
    @Singleton
    fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun providesOkHttpClient(
        interceptor: HeaderInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun providesRetrofitBuild(
        converterFactory: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()

    companion object {
        private const val BASE_URL = "https://openapi.naver.com/"
        private const val NAVER_ID_KEY = "X-Naver-Client-Id"
        private const val NAVER_SECRET_KEY = "X-Naver-Client-Secret"
        private const val NAVER_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z"
    }

}