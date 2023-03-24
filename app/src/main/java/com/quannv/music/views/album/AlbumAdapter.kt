package com.quannv.music.views.album

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.quannv.music.bases.DataBoundListAdapter
import com.quannv.music.databinding.ItemAlbumBinding
import com.quannv.music.databinding.ItemSongBinding
import com.quannv.music.models.Album
import com.quannv.music.utilities.clickWithDebounce
import com.quannv.music.utilities.context
import com.quannv.music.models.Song

class AlbumAdapter(
    private val mContext: Context,
    private val onItemClick: ((item: Album) -> Unit)?
) : DataBoundListAdapter<Album, ItemAlbumBinding>(diffCallback = object :
    DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.album?.equals(newItem.album, false) == true
    }

}) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ItemAlbumBinding {
        return ItemAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun bind(binding: ItemAlbumBinding, item: Album, position: Int) {
        binding.apply {
            album = item
            val artworkUri = Uri.parse("content://media/external/audio/albumart")
            val uriArt = ContentUris.withAppendedId(artworkUri, item.id ?: 0L)
            Glide.with(context).load(uriArt).into(imgAlbum)
            root.clickWithDebounce {
                onItemClick?.invoke(item)
            }
        }
    }
}