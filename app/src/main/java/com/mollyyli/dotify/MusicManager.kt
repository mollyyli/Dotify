package com.mollyyli.dotify

import com.ericchee.songdataprovider.Song

class MusicManager {
    lateinit var listOfSongs: List<Song>
    lateinit var currentSong: Song

//    fun onSongClicked() {
//
//    }
//
    fun shuffle() {
        listOfSongs = listOfSongs.toMutableList().apply {
            shuffle()
        }.toList()
    }
}