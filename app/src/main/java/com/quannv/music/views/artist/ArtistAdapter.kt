package com.quannv.music.views.artist

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.quannv.music.bases.DataBoundListAdapter
import com.quannv.music.databinding.ItemAlbumBinding
import com.quannv.music.databinding.ItemArtistBinding
import com.quannv.music.databinding.ItemSongBinding
import com.quannv.music.models.Album
import com.quannv.music.models.Artist
import com.quannv.music.utilities.clickWithDebounce
import com.quannv.music.utilities.context
import com.quannv.music.models.Song

class ArtistAdapter(
    private val mContext: Context,
    private val onItemClick: ((item: Artist) -> Unit)?
) : DataBoundListAdapter<Artist, ItemArtistBinding>(diffCallback = object :
    DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.artist?.equals(newItem.artist, false) == true
    }

}) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ItemArtistBinding {
        return ItemArtistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun bind(binding: ItemArtistBinding, item: Artist, position: Int) {
        binding.apply {
            artist = item
            val artworkUri = Uri.parse("content://media/external/audio/albumart")
            val uriArt = ContentUris.withAppendedId(artworkUri, item.id ?: 0L)
            Glide.with(context).load(uriArt).into(imgAvatar)
            root.clickWithDebounce {
                onItemClick?.invoke(item)
            }
        }
    }
}