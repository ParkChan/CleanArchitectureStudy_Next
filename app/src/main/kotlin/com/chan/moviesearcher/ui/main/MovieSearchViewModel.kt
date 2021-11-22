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

    private val _progresssData = MutableLiveData<List<ProgressItem>>()
    val progresssData: LiveData<List<ProgressItem>> = _progresssData

    private val _saveMovies = MutableLiveData<List<ItemDto>>()
    val saveMovies: LiveData<List<ItemDto>> = _saveMovies

    private val _message = MutableLiveData<Event<ClickEventMessage>>()
    val message: LiveData<Event<ClickEventMessage>> = _message

    private val _progressBar = MutableLiveData<Event<Boolean>>()
    val progressBar: LiveData<Event<Boolean>> = _progressBar

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(">>>> ${exception.message}")
    }

    private var isLoading = false

    private val movieList = mutableListOf<ItemDto>()
    private val saveList = mutableListOf<ItemDto>()

    private val pagingInfo = PageInfo(PageData())
    private var job: Job? = null

    init {
        _progresssData.value = testHeaderData
    }

    private fun fetchMovies(page: Int, query: String, isFirst: Boolean) {
        if (isLoading)
            return

        viewModelScope.launch(coroutineExceptionHandler) {
            if (isFirst) {
                initPaging()
                clearMovies()
            }
            isLoading = true
            useCase.request(page, query)
                .onSuccess {
                    pagingInfo.pageInfo(
                        start = it.start,
                        total = it.total
                    )
                    if (pagingInfo.isPaging()) {
                        _progressBar.value = Event(true)
                    } else {
                        _progressBar.value = Event(false)
                    }
                    delay(INTERVAL_PROGRESS_TIME)
                    _movies.value = movieList.apply {
                        addAll(it.items)
                    }
                }.onFailure {
                    Timber.e(it.message)
                }
            isLoading = false
        }
    }

    fun searchMovies(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            initPaging()
            clearMovies()
            if (query.isNotBlank()) {
                delay(INTERVAL_KEYWORD_SEARCH)
                fetchMovies(pagingInfo.startPage(), query.trim(), true)
            }
        }
    }

    fun moreMovies(query: String) {
        Timber.d("viewModel moreMovies")
        if (pagingInfo.isPaging()) {
            fetchMovies(pagingInfo.nextPage(), query, false)
        } else {
            Timber.d("Paging is End")
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
        private const val INTERVAL_PROGRESS_TIME = 800L
    }
}