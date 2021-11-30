package com.chan.movie.domain.di

import com.chan.movie.domain.usecase.MovieSearchUseCase
import com.chan.movie.domain.usecase.MovieSearchUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MovieSearchUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindsMovieSearchUseCase(useCase: MovieSearchUseCaseImpl): MovieSearchUseCase

}