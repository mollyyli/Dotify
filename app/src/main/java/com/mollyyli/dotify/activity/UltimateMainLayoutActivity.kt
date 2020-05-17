package com.mollyyli.dotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.google.gson.Gson
import com.mollyyli.dotify.ApiManager
import com.mollyyli.dotify.MusicApp
import com.mollyyli.dotify.MusicManager
import com.mollyyli.dotify.R
import com.mollyyli.dotify.fragment.NowPlayingFragment
import com.mollyyli.dotify.fragment.OnSongClickListener
import com.mollyyli.dotify.fragment.SongListFragment
import com.mollyyli.dotify.model.AllSongs
import kotlinx.android.synthetic.main.activity_ultimate_main_layout.*
import kotlinx.android.synthetic.main.fragment_song_list.*
import java.util.*

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

//    private fun fetchDataWithGson() {
//
//        val gson = Gson()
//
//        val song: Song = gson.fromJson(songOverviewJSONString, AllSongs::class.java)
//
////        email.from
////        email.content
////        email.id
//
////
////        email.content?.let {
////            Log.i(TAG, "Found an content of: $it")
////        } ?: Toast.makeText(this, "Sorry invalid data", Toast.LENGTH_SHORT).show()
//
//    }
//    private val songOverviewJSONString = """
//        {
//          "title": "Dotify",
//          "numOfSongs": 47,
//          "songs": [
//            {
//              "id": "1588825540885InTheEnd_LinkinPark",
//              "title": "In The End",
//              "artist": "Linkin Park",
//              "durationMillis": 193790,
//              "smallImageURL": "https://picsum.photos/seed/InTheEnd/50",
//              "largeImageURL": "https://picsum.photos/seed/InTheEnd/256"
//            },
//            {
//              "id": "1588825540953MaskDefinitelyOn_Future",
//              "title": "Mask Definitely On",
//              "artist": "Future",
//              "durationMillis": 92949,
//              "smallImageURL": "https://picsum.photos/seed/MaskDefinitelyOn/50",
//              "largeImageURL": "https://picsum.photos/seed/MaskDefinitelyOn/256"
//            },
//            ]
//        }
//    """.trimIndent()



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
