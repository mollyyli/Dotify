package com.mollyyli.dotify.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.mollyyli.dotify.R
//import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_activity_main.*
import kotlin.random.Random

class NowPlayingFragment:Fragment() {

    private var song: Song? = null

    private var randomNumber: Int? = Random.nextInt(1000, 10000)

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName

        const val ARG_SONG = "arg_song"

        const val RANDOM = "RANDOM_NUM"
        fun getInstance(nowSong: Song): NowPlayingFragment {
            return NowPlayingFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SONG, nowSong)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        randomNumber?.let {
            outState.putInt(RANDOM, it)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            val song = args.getParcelable<Song>(ARG_SONG)
            if(song != null) {
                this.song = song
            }
        }
        if(savedInstanceState != null) {
            with(savedInstanceState) {
                randomNumber = getInt(RANDOM)
            }
        } else {
            randomNumber = Random.nextInt(1000, 10000)
        }

    }

    fun updateSongs(song: Song) {
        song?.let {

            this.song = song
            updateSongViews()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity_main, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numberOfPlayTimes.text = "${randomNumber.toString()} plays"
        playButton.setOnClickListener {
            randomNumber?.let {
                var temp = it
                temp++
                randomNumber = temp
                numberOfPlayTimes.text = "${randomNumber.toString()} plays"
            }
        }
        updateSongViews()
    }



    private fun updateSongViews() {
        song?.let {
            tvSongTitle.text = it.title
            tvCurrentArtist.text = it.artist
            ivCurrentAlbumCover.setImageResource(it.largeImageID)
        }
    }
}



















