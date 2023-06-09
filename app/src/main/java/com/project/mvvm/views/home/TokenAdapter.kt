package com.project.mvvm.views.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.project.mvvm.BR
import com.project.mvvm.R
import com.project.mvvm.bases.DataBoundListAdapter
import com.project.mvvm.databinding.ItemTokenBinding
import com.project.mvvm.db.entity.Token
import com.project.mvvm.utilities.clickWithDebounce
import com.project.mvvm.utilities.inflateViewBinding

class TokenAdapter(
    private val mContext: Context,
    private val onItemClick: ((item: Token) -> Unit)?
) : DataBoundListAdapter<Token, ItemTokenBinding>(diffCallback = object :
    DiffUtil.ItemCallback<Token>() {
    // check 2 item xem chúng có đại diện cho cùng một đối tượng không.
    // Nếu thỏa mãn điều kiện thì những item này giống nhau ngay cả khi có một số trường khác nhau
    override fun areItemsTheSame(oldItem: Token, newItem: Token): Boolean {
        return oldItem.chainId.equals(
            newItem.chainId,
            true
        ) && oldItem.address.equals(newItem.address, true)
    }

    // check 2 item xem chúng có đại diện cho cùng một nội dung không.
    // Nếu thỏa mãn điều kiện nó sẽ không vẽ lại và không có aniamtion xảy ra.
    // Nếu không thỏa mãn điều kiện thì item sẽ được vẽ lại trên màn hình.
    override fun areContentsTheSame(oldItem: Token, newItem: Token): Boolean {
        return areItemsTheSame(oldItem, newItem)
                && oldItem.swap == newItem.swap
                && oldItem.buy == newItem.buy
                && oldItem.logo == newItem.logo
                && oldItem.price == newItem.price
    }

}) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ItemTokenBinding {
/*        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_token,
            parent,
            false
        )*/
//        return mContext.inflateViewBinding(parent, false)
        return ItemTokenBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun bind(binding: ItemTokenBinding, item: Token, position: Int) {
        binding.setVariable(BR.token, item)
        binding.root.clickWithDebounce {
            onItemClick?.invoke(item)
        }
    }
}