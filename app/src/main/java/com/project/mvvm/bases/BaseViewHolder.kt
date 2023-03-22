package com.project.mvvm.bases

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseViewHolder<VB : ViewBinding, IT>(var binding: VB) : RecyclerView.ViewHolder(binding.root) {

    open fun setupView() {

    }

    open fun bindData(itemInfo: IT, position: Int) {

    }

}
