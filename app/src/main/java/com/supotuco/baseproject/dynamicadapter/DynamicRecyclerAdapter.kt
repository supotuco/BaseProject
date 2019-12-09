package com.supotuco.baseproject.dynamicadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.lang.IllegalStateException


class DynamicRecyclerAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var typeToFactory: Map<Int, ((ViewGroup) -> BaseViewHolder<*>)> = emptyMap()

    private var holderModels = listOf<BaseHolderModel>()
        set(value) {
            val map = mutableMapOf<Int, (ViewGroup) -> BaseViewHolder<*>>()

            value.forEach { holderModel -> map[holderModel.type] = holderModel.viewHolderFactory() }
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return typeToFactory[viewType]?.invoke(parent)
                ?: throw IllegalStateException("No matching VH")
    }

    override fun getItemCount(): Int {
        return holderModels.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bind(holderModels[position])
    }

    override fun onViewRecycled(holder: BaseViewHolder<*>) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    fun replace(holderModels: List<BaseHolderModel>) {
        this.holderModels = holderModels
        notifyDataSetChanged()
    }
}