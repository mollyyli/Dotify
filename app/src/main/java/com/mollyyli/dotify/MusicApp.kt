package com.mollyyli.dotify

import android.app.Application
import com.ericchee.songdataprovider.Song
import com.mollyyli.dotify.model.AllSongs
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class MusicApp: Application() {

    lateinit var apiManager: ApiManager
    lateinit var musicManager: MusicManager
    override fun onCreate() {
        super.onCreate()
        apiManager = ApiManager(this)

        musicManager = MusicManager()
    }


}