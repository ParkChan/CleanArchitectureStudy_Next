package com.chan.moviesearcher.ui

import androidx.lifecycle.*
import com.chan.moviesearcher.domain.dto.MovieDto
import com.chan.moviesearcher.domain.usecase.MovieSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val useCase: MovieSearchUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<MovieDto>()
    val movies: LiveData<MovieDto> get() = _movies

    val query: MutableLiveData<String> = MutableLiveData()
    private val _throwable = MutableLiveData<Throwable>()
    val throwable: LiveData<Throwable> get() = _throwable

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
    }

    fun getMovieList(start: Int = 1, query: String) =
        viewModelScope.launch(coroutineExceptionHandler) {
            useCase.request(start, query)
                .onSuccess {
                    _movies.value = it
                }.onFailure {
                    _throwable.value = it
                }
        }
}