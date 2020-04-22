package com.mollyyli.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import org.w3c.dom.Text

class SongListAdaptor(ListOfSongs: List<Song>): RecyclerView.Adapter<SongListAdaptor.SongViewHolder>(){

    private var listOfSongs: List<Song> = ListOfSongs.toList()
    var onSongClickListener: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int): Unit {
        val song = listOfSongs[position]
        holder.bind(song, position)
    }
    fun change(newSongs: List<Song>) {
        val callback = SongDiffCallBack(listOfSongs, newSongs)
        val diffResult = DiffUtil.calculateDiff(callback)

        diffResult.dispatchUpdatesTo(this)
        listOfSongs = newSongs
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvSongName)
        private val ivImage = itemView.findViewById<ImageView>(R.id.ivSongImage)
        private val tvArtist = itemView.findViewById<TextView>(R.id.tvArtist)

        fun bind(song: Song, position: Int) {
            tvTitle.text = song.title
            ivImage.setImageResource(song.smallImageID)
            tvArtist.text = song.artist

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)

            }
        }
    }

}