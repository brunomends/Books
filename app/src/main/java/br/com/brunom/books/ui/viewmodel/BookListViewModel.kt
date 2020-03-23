package br.com.brunom.books.ui.viewmodel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunom.books.R
import br.com.brunom.books.http.BookHttp
import br.com.brunom.books.model.Volume
import br.com.brunom.books.ui.adapter.BookAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookListViewModel: ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun loadBooks(){
        if (state.value != null) return
        viewModelScope.launch {
            _state.value = State.StatedLoading
            val result = withContext(Dispatchers.IO) {
                BookHttp.searchBook("Dominando o Android")
            }
            if (result?.items != null) {
                _state.value = State.StateLoaded(result.items)
            } else {
                _state.value = State.StateError(Exception("No Results"), false)
            }
        }

    }
    sealed class State {
        object StatedLoading: State()
        data class StateLoaded(val list: List<Volume>): State()
        data class StateError(val error: Throwable, var hasConsumed: Boolean): State()
    }
}
