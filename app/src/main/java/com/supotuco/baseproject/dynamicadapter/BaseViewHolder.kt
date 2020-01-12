package com.supotuco.baseproject.dynamicadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder<T: BaseHolderModel>(
        view: View
): RecyclerView.ViewHolder(view), BaseHolderModelVisitor {

    abstract fun  recycle()
}