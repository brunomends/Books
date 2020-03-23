package br.com.brunom.books.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.brunom.books.R
import br.com.brunom.books.ui.adapter.BookPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager.adapter = BookPagerAdapter(this)
        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.setText(
            if (position == 0) {
                R.string.tab_books
            }else{
                R.string.tab_favorites
            }
            )
        }.attach()

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