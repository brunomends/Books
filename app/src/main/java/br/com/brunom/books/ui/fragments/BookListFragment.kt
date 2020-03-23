package br.com.brunom.books.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunom.books.R
import br.com.brunom.books.model.Volume
import br.com.brunom.books.ui.BookDetailActivity
import br.com.brunom.books.ui.adapter.BookAdapter
import br.com.brunom.books.ui.viewmodel.BookListViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_book_list.*

class BookListFragment: Fragment(){
    private val viewMode: BookListViewModel by lazy {
        ViewModelProvider(this).get(BookListViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(
            R.layout.fragment_book_list,
            container,
            false
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewMode.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is BookListViewModel.State.StatedLoading -> {
                    progressLayout.visibility = View.VISIBLE
                }
                is BookListViewModel.State.StateLoaded -> {
                    progressLayout.visibility = View.GONE
                    val bookAdapter = BookAdapter(state.list, this@BookListFragment::onVolumeClik)
                    rvBooks.layoutManager = LinearLayoutManager(requireContext())
                    rvBooks.adapter = bookAdapter
                }
                is BookListViewModel.State.StateError -> {
                    progressLayout.visibility = View.GONE
                    if (!state.hasConsumed) {
                        state.hasConsumed = true
                        Toast.makeText(
                            requireContext(),
                            R.string.error_load_books, Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })


        viewMode.loadBooks()
    }
    private fun onVolumeClik (volume: Volume){
        BookDetailActivity.openWithVolume(requireContext(), volume)
}

}