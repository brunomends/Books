package br.com.brunom.books.ui.viewmodel

import androidx.lifecycle.*
import br.com.brunom.books.model.Volume
import br.com.brunom.books.repository.BookRepository
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val repository: BookRepository
): ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite


     fun onCreate(volume: Volume){
        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(volume.id)
        }
    }

    fun saveToFavorite(volume: Volume){
        viewModelScope.launch {
            repository.save(volume)
            _isFavorite.value = repository.isFavorite(volume.id)
        }
    }

    fun removeFromFavorites(volume: Volume){
        viewModelScope.launch {
            repository.delete(volume)
            _isFavorite.value = repository.isFavorite(volume.id)
        }
    }

}
