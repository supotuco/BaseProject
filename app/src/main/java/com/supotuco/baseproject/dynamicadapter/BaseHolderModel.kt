package com.supotuco.baseproject.dynamicadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

data class LayoutResource(@LayoutRes val id: Int)

interface BaseHolderModel {

    val layout: LayoutResource

    val type: Int
        get() = layout.id

    fun viewHolderFactory(): (ViewGroup) -> BaseViewHolder<*>

    fun accept(visitor: BaseHolderModelVisitor)

    fun areItemsSame(that: BaseHolderModel) : Boolean

    fun areContentsSame(that: BaseHolderModel) : Boolean

    fun ViewGroup.inflate(layout: LayoutResource): View {
        return LayoutInflater.from(context)
                .inflate(layout.id, this, false)
    }
}

interface BaseHolderModelVisitor