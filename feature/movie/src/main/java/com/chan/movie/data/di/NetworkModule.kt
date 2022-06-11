package com.chan.movie.data.di

import com.chan.movie.BuildConfig
import com.chan.movie.data.HeaderInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Provides
    @Singleton
    fun providesStethoInterceptor() = StethoInterceptor()

    @Provides
    @Singleton
    fun providesHeaderInterceptor() = HeaderInterceptor(
        mapOf(
            NAVER_ID_KEY to BuildConfig.NAVER_CLIENT_ID,
            NAVER_SECRET_KEY to BuildConfig.NAVER_CLIENT_SECRET
        )
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
        stethoInterceptor: StethoInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .apply {
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(stethoInterceptor)
                }
            }.build()

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
        const val BASE_URL = "https://openapi.naver.com/"
        const val NAVER_ID_KEY = "X-Naver-Client-Id"
        const val NAVER_SECRET_KEY = "X-Naver-Client-Secret"
        const val NAVER_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z"
    }

}