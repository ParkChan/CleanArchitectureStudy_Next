package com.chan.moviesearcher.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chan.moviesearcher.domain.dto.ItemDto
import com.chan.moviesearcher.domain.usecase.MovieSearchUseCase
import com.chan.moviesearcher.ui.main.data.ClickEventMessage
import com.chan.moviesearcher.ui.main.data.PageData
import com.chan.moviesearcher.ui.main.data.PageInfo
import com.chan.moviesearcher.ui.main.data.ProgressItem
import com.chan.moviesearcher.ui.main.test.testHeaderData
import com.chan.ui.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val useCase: MovieSearchUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<List<ItemDto>>()
    val movies: LiveData<List<ItemDto>> = _movies

    private val _progressData = MutableLiveData<List<ProgressItem>>()
    val progressData: LiveData<List<ProgressItem>> = _progressData

    private val _saveMovies = MutableLiveData<List<ItemDto>>()
    val saveMovies: LiveData<List<ItemDto>> = _saveMovies

    private val _message = MutableLiveData<Event<ClickEventMessage>>()
    val message: LiveData<Event<ClickEventMessage>> = _message

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(">>>> ${exception.message}")
    }

    private val movieList = mutableListOf<ItemDto>()
    private val saveList = mutableListOf<ItemDto>()

    private val pagingInfo = PageInfo(PageData())
    private var job: Job? = null

    private fun fetchMovies(page: Int, query: String, isFirst: Boolean) {
        viewModelScope.launch(coroutineExceptionHandler) {
            useCase.request(page, query)
                .onSuccess {
                    pagingInfo.pageInfo(
                        start = it.start,
                        total = it.total
                    )
                    if (isFirst) {
                        _movies.value = movieList.apply {
                            addAll(it.items)
                        }
                        bottomProgessBarVisibility(pagingInfo.isPaging())
                    } else {
                        bottomProgessBarVisibility(pagingInfo.isPaging())
                        delay(INTERVAL_PROGRESS_VISIBLE_TIME)
                        _movies.value = movieList.apply {
                            addAll(it.items)
                        }
                    }
                }.onFailure {
                    Timber.e(it.message)
                }
        }
    }

    fun searchMovies(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            initPaging()
            clearMovies()
            bottomProgessBarVisibility(false)
            if (query.isNotBlank()) {
                delay(INTERVAL_KEYWORD_SEARCH)
                fetchMovies(pagingInfo.startPage(), query.trim(), true)
            }
        }
    }

    fun moreMovies(query: String) {
        if (pagingInfo.isPaging()) {
            fetchMovies(pagingInfo.nextPage(), query, false)
        }
    }

    private fun initPaging() {
        pagingInfo.init()
    }

    private fun clearMovies() {
        _movies.value = movieList.apply {
            clear()
        }
    }

    private fun bottomProgessBarVisibility(isVisible: Boolean) {
        if (isVisible) {
            _progressData.value = testHeaderData
        } else {
            _progressData.value = emptyList()
        }
    }

    fun onClickSaveItem(contentData: ItemDto) {
        if (!saveList.contains(contentData)) {
            _saveMovies.value = saveList.apply {
                add(contentData)
            }
            _message.value = Event(ClickEventMessage.SAVE_SUCCESS)
        } else {
            _message.value = Event(ClickEventMessage.ALREADY_EXIST)
        }
    }

    fun onClickDeleteItem(contentData: ItemDto) {
        if (saveList.contains(contentData)) {
            _saveMovies.value = saveList.apply {
                remove(contentData)
            }
            _message.value = Event(ClickEventMessage.DELETE_SUCCESS)
        }
    }

    companion object {
        private const val INTERVAL_KEYWORD_SEARCH = 1500L
        private const val INTERVAL_PROGRESS_VISIBLE_TIME = 250L
    }
}