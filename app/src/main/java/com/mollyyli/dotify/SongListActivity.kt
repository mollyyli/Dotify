package com.mollyyli.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.AudioColumns.TITLE_KEY
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {
    private var currentSong: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        var allSongs = SongDataProvider.getAllSongs()
        val songListAdaptor = SongListAdaptor(allSongs)
        rvSongs.adapter = songListAdaptor

//
//
        tvMiniPlayer.setOnClickListener {
            val intent = Intent(this, MainActivity:: class.java)
            intent.putExtra("SONG_INFO", currentSong)
            startActivity(intent)
        }
//
        btnShuffle.setOnClickListener {
            val newSongs = allSongs.toMutableList().apply {
                shuffle()
            }
            songListAdaptor.change(newSongs)
        }

        songListAdaptor.onSongClickListener = {
            tvMiniPlayer.text = "${it.title} - ${it.artist}"
            this.currentSong = it
        }
    }
}
