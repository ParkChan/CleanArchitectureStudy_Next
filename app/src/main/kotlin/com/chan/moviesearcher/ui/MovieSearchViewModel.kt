package com.chan.moviesearcher.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chan.moviesearcher.domain.dto.ItemDto
import com.chan.moviesearcher.domain.usecase.MovieSearchUseCase
import com.chan.moviesearcher.ui.data.PageData
import com.chan.moviesearcher.ui.data.PageInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val useCase: MovieSearchUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<List<ItemDto>>()
    val movies: LiveData<List<ItemDto>> = _movies

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
    }

    private val pagingInfo = PageInfo(PageData())

    private fun fetchMovieList(page: Int, query: String, isFirst: Boolean) =
        viewModelScope.launch(coroutineExceptionHandler) {
            if (isFirst) {
                initPaging()
                clearData()
            }
            useCase.request(page, query)
                .onSuccess {
                    pagingInfo.pageInfo(
                        start = it.start,
                        total = it.total
                    )
                    _movies.value = (_movies.value ?: emptyList()).plus(it.items)
                }.onFailure {
                    Timber.e(it.message)
                }
        }

    fun getMovieList(query: String) {
        fetchMovieList(pagingInfo.startPage(), query, true)
    }

    fun moreMovieList(query: String) {
        if (pagingInfo.isPaging()) {
            fetchMovieList(pagingInfo.nextPage(), query, false)
        } else {
            Timber.d("Paging is End")
        }
    }

    private fun initPaging() {
        pagingInfo.init()
    }

    private fun clearData() {
        _movies.value = emptyList()
    }
}