package com.mollyyli.dotify.fragment

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.mollyyli.dotify.R
import com.mollyyli.dotify.model.SongListAdaptor
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment: Fragment() {
    private lateinit var songListAdaptor: SongListAdaptor

    private var allSongs: List<Song>? = null

    private var onSongClickListener: OnSongClickListener? = null

    companion object {
        const val ALL_SONGS = "all_songs"
        val TAG = SongListFragment::class.java.simpleName

        fun getInstance(listOfSongs: List<Song>): SongListFragment {
            return SongListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ALL_SONGS, ArrayList(listOfSongs))
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(ALL_SONGS, allSongs as java.util.ArrayList<out Parcelable>)
        super.onSaveInstanceState(outState)

    }

     override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {args ->
            with(args) {
                allSongs = getParcelableArrayList<Song>(ALL_SONGS) as ArrayList<Song>
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_song_list, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                allSongs = getParcelableArrayList(ALL_SONGS)
            }
        }
        allSongs?.let {
            songListAdaptor = SongListAdaptor(it)
        }
        rvSongs.adapter = songListAdaptor

        songListAdaptor.onSongClickListener = { song ->
            onSongClickListener?.onSongClicked(song)

        }

    }

    fun shuffleList() {
        this.allSongs?.let {immuntableAllSongs ->
            val nonNullAllSongs = immuntableAllSongs
            val newSongs = nonNullAllSongs.toMutableList().apply {
                shuffle()
            }
            songListAdaptor.change(newSongs)
            allSongs = newSongs
        }
    }
}

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}