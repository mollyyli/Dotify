package com.mollyyli.dotify

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ericchee.songdataprovider.Song
import com.google.gson.Gson
import com.mollyyli.dotify.model.AllSongs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiManager(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun getSongs(onSongsReady: (AllSongs) -> Unit,onError: (() -> Unit)? = null) {
        val musicURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/musiclibrary.json"
        val request = StringRequest(
            Request.Method.GET,
            musicURL,
            {response ->
                val gson = Gson()
                val allSongs = gson.fromJson(response, AllSongs::class.java)
                onSongsReady(allSongs)
            },
            {
                onError?.invoke()
            }
        )
        queue.add(request)
    }
}