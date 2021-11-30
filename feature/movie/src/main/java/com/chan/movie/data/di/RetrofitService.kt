package com.chan.movie.data.di

import com.chan.movie.data.source.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitService {

    @Provides
    @Singleton
    fun providesMovieService(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)
}