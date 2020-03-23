package br.com.brunom.books.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import br.com.brunom.books.repository.BookRepository
import br.com.brunom.books.ui.BaseActivity

class BookFavoritesViewModel(
    private val repository: BookRepository
): ViewModel(){

    val favoritesBooks = repository.allFavorites().asLiveData()

}