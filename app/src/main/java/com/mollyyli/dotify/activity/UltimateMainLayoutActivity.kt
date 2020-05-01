package com.mollyyli.dotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.mollyyli.dotify.R
import com.mollyyli.dotify.fragment.NowPlayingFragment
import com.mollyyli.dotify.fragment.OnSongClickListener
import com.mollyyli.dotify.fragment.SongListFragment
import kotlinx.android.synthetic.main.activity_ultimate_main_layout.*
import kotlinx.android.synthetic.main.fragment_song_list.*
import java.util.*

class UltimateMainLayoutActivity : AppCompatActivity(), OnSongClickListener {

    private lateinit var listOfSongs: List<Song>

    private var songListFragment: SongListFragment? =null

    private var nowPlayingFragment: NowPlayingFragment? =null

    private var currentSong: Song? = null

    companion object {
        private const val CURRENT_SONG = "CURRENT_SONG"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_main_layout)

        if(savedInstanceState != null) {
            with(savedInstanceState) {
                currentSong = getParcelable(CURRENT_SONG)
                currentSong?.let {
                    tvMiniPlayer.text = "${it.title} - ${it.artist}"
                }
            }
        }


        if (supportFragmentManager.findFragmentByTag(SongListFragment.TAG) == null) {

            var allSongs = SongDataProvider.getAllSongs()

            songListFragment = SongListFragment.getInstance(allSongs)
            songListFragment?.let {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, it, SongListFragment.TAG)
//                .addToBackStack(SongListFragment.TAG)
                    .commit()
            }

        }

        if (songListFragment == null) {
            songListFragment = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment
        }

        if (supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) != null) {
            displayBottomBar.visibility = View.INVISIBLE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        btnShuffle.setOnClickListener {
            val listFragment = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as SongListFragment
            songListFragment?.shuffleList()
        }

        tvMiniPlayer.setOnClickListener {
            currentSong?.let {
                onMiniPlayerSelected(it)
            }
        }


        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0

            if (hasBackStack) {
                displayBottomBar.visibility = View.INVISIBLE

                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                displayBottomBar.visibility = View.VISIBLE

                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }


    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment


    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

    fun onMiniPlayerSelected(song: Song) {
        if (supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) == null) {

            currentSong?.let {
                nowPlayingFragment = NowPlayingFragment.getInstance(it)
            }
            nowPlayingFragment?.let {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, it, NowPlayingFragment.TAG)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()
            }

        } else {
            nowPlayingFragment?.updateSongs(song)
        }
    }

    override fun onSongClicked(song: com.ericchee.songdataprovider.Song) {
        currentSong = song
        tvMiniPlayer.text = "${song.title} - ${song.artist}"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(CURRENT_SONG, currentSong)

    }
}
