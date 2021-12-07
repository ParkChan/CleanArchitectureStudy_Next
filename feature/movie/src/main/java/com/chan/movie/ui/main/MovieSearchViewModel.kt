package com.chan.movie.ui.main

import androidx.lifecycle.*
import com.chan.movie.domain.data.ItemData
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
        savedStateHandle.get(MOVIE_LIST_HANDLE_KEY) ?: mutableListOf<ItemData>()
        set(value) {
            savedStateHandle.set(MOVIE_LIST_HANDLE_KEY, value)
            field = value
        }
    private var saveList = savedStateHandle.get(SAVE_LIST_HANDLE_KEY) ?: mutableListOf<ItemData>()
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

    private val _movies = MutableLiveData<List<ItemData>>(movieList)
    val movies: LiveData<List<ItemData>> = _movies

    private val _bottomProgress = MutableLiveData<Boolean>()
    val bottomProgress: LiveData<Boolean> = _bottomProgress

    private val _saveMovies = MutableLiveData<List<ItemData>>(saveList)
    val saveMovies: LiveData<List<ItemData>> = _saveMovies

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
                    savedStateHandle[PAGE_INFO_HANDLE_KEY] = pagingInfo

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
                    savedStateHandle[MOVIE_LIST_HANDLE_KEY] = movieList
                }
        }

    fun searchMovies(query: String) {
        if (beforeText == query) {
            return
        }
        savedStateHandle[BEFORE_TEXT_HANDLE_KEY] = query
        beforeText = query

        viewModelScope.launch {
            initPaging()
            clearMovies()
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
        _movies.value = movieList.apply {
            clear()
        }
    }

    private fun bottomProgessBar(isVisible: Boolean) {
        _bottomProgress.value = isVisible
    }

    fun onClickSaveItem(contentData: ItemData) {
        if (!saveList.contains(contentData)) {
            _saveMovies.value = saveList.apply {
                add(contentData)
            }
            _message.value = Event(ClickEventMessage.SAVE_SUCCESS)
        } else {
            _message.value = Event(ClickEventMessage.ALREADY_EXIST)
        }
        savedStateHandle[SAVE_LIST_HANDLE_KEY] = saveList
    }

    fun onClickDeleteItem(contentData: ItemData) {
        if (saveList.contains(contentData)) {
            _saveMovies.value = saveList.apply {
                remove(contentData)
            }
            _message.value = Event(ClickEventMessage.DELETE_SUCCESS)
        }
        savedStateHandle[SAVE_LIST_HANDLE_KEY] = saveList
    }

    companion object {
        private const val INTERVAL_PROGRESS_VISIBLE_TIME = 250L
        private const val PAGE_INFO_HANDLE_KEY = "PAGE_INFO_HANDLE_KEY"
        private const val BEFORE_TEXT_HANDLE_KEY = "BEFORE_TEXT_HANDLE_KEY"
        private const val MOVIE_LIST_HANDLE_KEY = "MOVIE_LIST_HANDLE_KEY"
        private const val SAVE_LIST_HANDLE_KEY = "SAVE_LIST_HANDLE_KEY"
    }

}