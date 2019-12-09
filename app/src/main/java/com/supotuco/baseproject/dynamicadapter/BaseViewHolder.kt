package com.supotuco.baseproject.dynamicadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder<T: BaseHolderModel>(view: View): RecyclerView.ViewHolder(view) {

    abstract fun bind(holderModel: BaseHolderModel)

    abstract fun  recycle()
}