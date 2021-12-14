package com.chan.movie.ui.main

import androidx.lifecycle.*
import com.chan.movie.domain.data.Item
import com.chan.movie.domain.usecase.MovieSearchUseCase
import com.chan.movie.ui.main.data.ClickEventMessage
import com.chan.movie.ui.main.data.PageData
import com.chan.movie.ui.main.data.PageInfo
import com.chan.ui.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val movieSearchUseCase: MovieSearchUseCase
) : ViewModel() {

    private var movieList =
        savedStateHandle.get(MOVIE_LIST_HANDLE_KEY) ?: emptyList<Item>()
        set(value) {
            savedStateHandle.set(MOVIE_LIST_HANDLE_KEY, value)
            field = value
        }
    private var saveList = savedStateHandle.get(SAVE_LIST_HANDLE_KEY) ?: emptyList<Item>()
        set(value) {
            savedStateHandle.set(SAVE_LIST_HANDLE_KEY, value)
            field = value
        }
    private var pagingInfo = savedStateHandle.get(PAGE_INFO_HANDLE_KEY) ?: PageInfo(PageData())
        set(value) {
            savedStateHandle.set(PAGE_INFO_HANDLE_KEY, value)
            field = value
        }
    private var beforeText = savedStateHandle.get(BEFORE_TEXT_HANDLE_KEY) ?: ""
        set(value) {
            savedStateHandle.set(BEFORE_TEXT_HANDLE_KEY, value)
            field = value
        }

    val movies: LiveData<List<Item>> = savedStateHandle.getLiveData(MOVIE_LIST_HANDLE_KEY)
    val saveMovies: LiveData<List<Item>> = savedStateHandle.getLiveData(SAVE_LIST_HANDLE_KEY)

    private val _bottomProgress = MutableLiveData<Boolean>()
    val bottomProgress: LiveData<Boolean> = _bottomProgress

    private val _message = MutableLiveData<Event<ClickEventMessage>>()
    val message: LiveData<Event<ClickEventMessage>> = _message

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(">>>> ${exception.message}")
        bottomProgessBar(false)
    }

    fun fetchMovies(page: Int, query: String, isFirst: Boolean) =
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
                    pagingInfo = pagingInfo

                    if (isFirst) {
                        movieList = movieList + it.items
                        bottomProgessBar(pagingInfo.isPaging())
                    } else {
                        bottomProgessBar(pagingInfo.isPaging())
                        delay(INTERVAL_PROGRESS_VISIBLE_TIME)
                        movieList = movieList + it.items
                    }
                }
        }

    fun searchMovies(query: String) {
        if (beforeText == query) {
            return
        }
        beforeText = query

        viewModelScope.launch {
            initPaging()
            clearMovies()
            bottomProgessBar(false)
            if (query.isNotBlank()) {
                fetchMovies(pagingInfo.startPage(), query.trim(), true)
            }
        }
    }

    fun moreMovies() {
        viewModelScope.launch(coroutineExceptionHandler) {
            if (pagingInfo.isPaging()) {
                fetchMovies(pagingInfo.nextPage(), beforeText, false)
            }
        }
    }

    private fun initPaging() {
        pagingInfo.init()
    }

    private fun clearMovies() {
        movieList = emptyList()
    }

    private fun bottomProgessBar(isVisible: Boolean) {
        _bottomProgress.value = isVisible
    }

    fun onClickSaveItem(content: Item) {
        if (!saveList.contains(content)) {
            saveList = saveList + content
            _message.value = Event(ClickEventMessage.SAVE_SUCCESS)
        } else {
            _message.value = Event(ClickEventMessage.ALREADY_EXIST)
        }
    }

    fun onClickDeleteItem(content: Item) {
        if (saveList.contains(content)) {
            saveList = saveList - content
            _message.value = Event(ClickEventMessage.DELETE_SUCCESS)
        }
    }

    companion object {
        private const val INTERVAL_PROGRESS_VISIBLE_TIME = 250L
        private const val PAGE_INFO_HANDLE_KEY = "PAGE_INFO_HANDLE_KEY"
        private const val BEFORE_TEXT_HANDLE_KEY = "BEFORE_TEXT_HANDLE_KEY"
        private const val MOVIE_LIST_HANDLE_KEY = "MOVIE_LIST_HANDLE_KEY"
        private const val SAVE_LIST_HANDLE_KEY = "SAVE_LIST_HANDLE_KEY"
    }

}