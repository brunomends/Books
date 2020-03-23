package br.com.brunom.books.http

import br.com.brunom.books.model.SearchResult
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import java.util.concurrent.TimeUnit

object BookHttp {
    private const val API_KEY =
        "AIzaSyC-i5f3CIZ_p_tu_Dh0OMc46s1c3fc35Oc"
    private const val BOOK_JSON_URL =
        "https://www.googleapis.com/books/v1/volumes?q=%s&Key=$API_KEY"

    private val client = OkHttpClient.Builder()
        .readTimeout( 5, TimeUnit.SECONDS)
        .connectTimeout( 10, TimeUnit.SECONDS)
        .build()

    fun searchBook(q: String): SearchResult? {

        val request = Request.Builder()
            .url(String.format(BOOK_JSON_URL, q))
            .build()
        try{
            val response = client.newCall(request).execute()
            val json = response.body?.string()
            return Gson().fromJson<SearchResult>(
                json, SearchResult::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
