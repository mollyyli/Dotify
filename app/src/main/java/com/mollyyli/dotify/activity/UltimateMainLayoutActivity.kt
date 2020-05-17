package com.mollyyli.dotify.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.mollyyli.dotify.ApiManager
import com.mollyyli.dotify.MusicApp
import com.mollyyli.dotify.MusicManager
import com.mollyyli.dotify.R
import com.mollyyli.dotify.fragment.NowPlayingFragment
import com.mollyyli.dotify.fragment.OnSongClickListener
import com.mollyyli.dotify.fragment.SongListFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ultimate_main_layout.*
import sun.jvm.hotspot.utilities.IntArray
import sun.security.krb5.internal.KDCOptions.with


class UltimateMainLayoutActivity : AppCompatActivity(), OnSongClickListener {

    private var listOfSongs: List<Song>? = null

    private var songListFragment: SongListFragment? =null

    private var nowPlayingFragment: NowPlayingFragment? =null

    private var currentSong: Song? = null

    lateinit var apiManager: ApiManager

    lateinit var musicManager: MusicManager

    companion object {
        private const val CURRENT_SONG = "CURRENT_SONG"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_main_layout)

        apiManager = (application as MusicApp).apiManager
        apiManager.getSongs({allSongs ->
                listOfSongs = allSongs.songs
                Log.i("main", listOfSongs.toString())
//                for (url in listofSongs.smallImageURL) {
//                    Picasso.with(applicationContext).load(url).fetch()
//                }
            },
            {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            })
        if (supportFragmentManager.findFragmentByTag(SongListFragment.TAG) == null) {
            listOfSongs?.let {
                songListFragment = SongListFragment.getInstance(it)
            }

            songListFragment?.let {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, it, SongListFragment.TAG)
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
