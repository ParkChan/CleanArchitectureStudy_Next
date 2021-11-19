package com.chan.moviesearcher.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chan.moviesearcher.domain.dto.ItemDto
import com.chan.moviesearcher.domain.usecase.MovieSearchUseCase
import com.chan.moviesearcher.ui.main.data.ClickEventMessage
import com.chan.moviesearcher.ui.main.data.HeaderData
import com.chan.moviesearcher.ui.main.data.PageData
import com.chan.moviesearcher.ui.main.data.PageInfo
import com.chan.moviesearcher.ui.main.test.testHeaderData
import com.chan.ui.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val useCase: MovieSearchUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<List<ItemDto>>()
    val movies: LiveData<List<ItemDto>> = _movies

    private val _headers = MutableLiveData<List<HeaderData>>()
    val headers: LiveData<List<HeaderData>> = _headers

    private val _saveMovies = MutableLiveData<List<ItemDto>>()
    val saveMovies: LiveData<List<ItemDto>> = _saveMovies

    private val _message = MutableLiveData<Event<ClickEventMessage>>()
    val message: LiveData<Event<ClickEventMessage>> = _message

    private val _searchQuery = MutableStateFlow("")
    private val searchQuery = _searchQuery.asStateFlow()
        .debounce(INTERVAL_KEYWORD_SEARCH)
        .mapLatest { query ->
            getMovieList(query)
        }.catch { error: Throwable ->
            Timber.e(error)
        }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(">>>> ${exception.message}")
    }

    private val movieList = mutableListOf<ItemDto>()
    private val saveList = mutableListOf<ItemDto>()

    private val pagingInfo = PageInfo(PageData())

    init {
        _headers.value = testHeaderData
    }

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
                    _movies.value = movieList.apply {
                        addAll(it.items)
                    }
                }.onFailure {
                    Timber.e(it.message)
                }
        }

    @FlowPreview
    suspend fun setSearchQuery(query: String) {
        _searchQuery.value = query
        searchQuery.collect()
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

    fun clearData() {
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
        private const val INTERVAL_KEYWORD_SEARCH = 500L
    }
}