package com.chan.movie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chan.movie.domain.dto.ItemDto
import com.chan.movie.domain.usecase.MovieSearchUseCase
import com.chan.movie.ui.main.data.ClickEventMessage
import com.chan.movie.ui.main.data.PageData
import com.chan.movie.ui.main.data.PageInfo
import com.chan.ui.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val movieSearchUseCase: MovieSearchUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<List<ItemDto>>()
    val movies: LiveData<List<ItemDto>> = _movies

    private val _bottomProgress = MutableLiveData<Boolean>()
    val bottomProgress: LiveData<Boolean> = _bottomProgress

    private val _saveMovies = MutableLiveData<List<ItemDto>>()
    val saveMovies: LiveData<List<ItemDto>> = _saveMovies

    private val _message = MutableLiveData<Event<ClickEventMessage>>()
    val message: LiveData<Event<ClickEventMessage>> = _message

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(">>>> ${exception.message}")
        bottomProgessBar(false)
    }

    private val movieList = mutableListOf<ItemDto>()
    private val saveList = mutableListOf<ItemDto>()

    private val pagingInfo = PageInfo(PageData())

    private val _searchQuery = MutableStateFlow("")
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    private val querySearch: Flow<Unit> = _searchQuery
        .debounce(INTERVAL_KEYWORD_SEARCH)
        .flatMapLatest { query ->
            flow<Unit> {
                searchMovies(query)
            }
        }.catch { e: Throwable ->
            Timber.e(">>>> ${e.message}")
        }

    init {
        viewModelScope.launch {
            querySearch.collect()
        }
    }

    suspend fun fetchMovies(page: Int, query: String, isFirst: Boolean) =
        viewModelScope.launch(coroutineExceptionHandler) {
            movieSearchUseCase.fetchMovies(page, query)
                .catch { e: Throwable ->
                    Timber.e(e.message)
                    bottomProgessBar(false)
                }.collect {
                    pagingInfo.pageInfo(
                        start = it.start,
                        total = it.total
                    )
                    if (isFirst) {
                        _movies.value = movieList.apply {
                            addAll(it.items)
                        }
                        bottomProgessBar(pagingInfo.isPaging())
                    } else {
                        bottomProgessBar(pagingInfo.isPaging())
                        delay(INTERVAL_PROGRESS_VISIBLE_TIME)
                        _movies.value = movieList.apply {
                            addAll(it.items)
                        }
                    }
                }
        }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            initPaging()
            clearMovies()
            if (query.isNotBlank()) {
                fetchMovies(pagingInfo.startPage(), query.trim(), true)
            }
        }
    }

    suspend fun moreMovies(query: String) {
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

    private fun bottomProgessBar(isVisible: Boolean) {
        _bottomProgress.value = isVisible
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
        private const val INTERVAL_KEYWORD_SEARCH = 1_000L
        private const val INTERVAL_PROGRESS_VISIBLE_TIME = 250L
    }
}