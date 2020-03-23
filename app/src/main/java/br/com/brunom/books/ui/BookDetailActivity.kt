package br.com.brunom.books.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.brunom.books.R
import br.com.brunom.books.model.Volume
import br.com.brunom.books.repository.BookRepository
import br.com.brunom.books.ui.viewmodel.BookDetailViewModel
import br.com.brunom.books.ui.viewmodel.BookVmFactory
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detail.*

class BookDetailActivity : BaseActivity() {

    private val viewModel: BookDetailViewModel by lazy {
        ViewModelProvider(
            this,
            BookVmFactory(
                BookRepository(this)
            )
        ).get(BookDetailViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        val volume = intent.getParcelableExtra<Volume?>(EXTRA_BOOK)
        volume?.run {
            txtTitle.text = volume.volumeInfo.title
            txtAuthor.text = volume.volumeInfo.authors?.joinToString()
            txtPages.text = volume.volumeInfo.pageCount.toString()
            txtDesc.text = volume.volumeInfo.description
            if (volume.volumeInfo.imageLinks?.thumbnail?.isNotEmpty() == true) {
                Picasso.get()
                    .load(volumeInfo.imageLinks?.thumbnail)
                    .into(imgCover)
            }

            viewModel.onCreate(volume)
            viewModel.isFavorite.observe(
                this@BookDetailActivity,
                Observer { isFavorite ->
                    if (isFavorite) {
                        fabFavorite.setImageResource(R.drawable.ic_delete)
                        fabFavorite.setOnClickListener {
                            viewModel.removeFromFavorites(volume)
                        }
                    } else {
                        fabFavorite.setImageResource(R.drawable.ic_add)
                        fabFavorite.setOnClickListener {
                            viewModel.saveToFavorite(volume)
                        }
                    }

                }
            )

        }


    }

    companion object {
        private const val EXTRA_BOOK = "Book"

        fun openWithVolume(context: Context, volume: Volume) {
            val intencao = Intent(context, BookDetailActivity::class.java)
            intencao.putExtra(EXTRA_BOOK, volume)
            context.startActivity(intencao)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.book_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_sign_out) {
            FirebaseAuth.getInstance().signOut()
            return true
        }
        return super.onOptionsItemSelected(item)
    }




}
