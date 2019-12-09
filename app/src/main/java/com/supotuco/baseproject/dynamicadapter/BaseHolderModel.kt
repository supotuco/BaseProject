package com.supotuco.baseproject.dynamicadapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes

data class LayoutResource(@LayoutRes val id: Int)

interface BaseHolderModel {

    val layout: LayoutResource

    val type: Int
        get() = layout.id

    fun viewHolderFactory(): (ViewGroup) -> BaseViewHolder<*>
}