package com.quannv.music.views.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.quannv.music.BR
import com.quannv.music.bases.DataBoundListAdapter
import com.quannv.music.databinding.ItemSongBinding
import com.quannv.music.databinding.ItemTokenBinding
import com.quannv.music.db.entity.Token
import com.quannv.music.utilities.clickWithDebounce
import com.quannv.music.views.home.models.Song

class SongAdapter(
    private val mContext: Context,
    private val onItemClick: ((item: Song) -> Unit)?
) : DataBoundListAdapter<Song, ItemSongBinding>(diffCallback = object :
    DiffUtil.ItemCallback<Song>() {
    // check 2 item xem chúng có đại diện cho cùng một đối tượng không.
    // Nếu thỏa mãn điều kiện thì những item này giống nhau ngay cả khi có một số trường khác nhau
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.equals(newItem)
    }

    // check 2 item xem chúng có đại diện cho cùng một nội dung không.
    // Nếu thỏa mãn điều kiện nó sẽ không vẽ lại và không có aniamtion xảy ra.
    // Nếu không thỏa mãn điều kiện thì item sẽ được vẽ lại trên màn hình.
    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.displayName?.equals(newItem.displayName, false) == true
    }

}) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ItemSongBinding {
/*        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_token,
            parent,
            false
        )*/
//        return mContext.inflateViewBinding(parent, false)
        return ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun bind(binding: ItemSongBinding, item: Song, position: Int) {
        binding.apply {
            song = item
            root.clickWithDebounce {
                onItemClick?.invoke(item)
            }
        }
    }
}