package com.supotuco.baseproject.employeeview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supotuco.baseproject.R
import com.supotuco.baseproject.dynamicadapter.BaseHolderModel
import com.supotuco.baseproject.dynamicadapter.BaseHolderModelVisitor
import com.supotuco.baseproject.dynamicadapter.BaseViewHolder
import com.supotuco.baseproject.dynamicadapter.LayoutResource
import com.supotuco.baseproject.employee.EmployeeServerData

class EmployeeItemHM(
        private val data: EmployeeServerData,
        private val onItemClicked: (EmployeeServerData) -> Unit
) : BaseHolderModel {

    override val layout: LayoutResource
        get() = LayoutResource(R.layout.employee_item_view)

    val nameViewText: String
        get() = data.fullName

    override fun viewHolderFactory(): (ViewGroup) -> BaseViewHolder<*> {
        return { viewGroup -> EmployeeItemVH(viewGroup.inflate(layout)) }
    }

    override fun accept(visitor: BaseHolderModelVisitor) {
        (visitor as? Visitor)?.visit(this)
    }

    override fun areItemsSame(that: BaseHolderModel): Boolean {
        return that is EmployeeItemHM && this.data.uuid == that.data.uuid
    }

    override fun areContentsSame(that: BaseHolderModel): Boolean {
        return that is EmployeeItemHM && this.data == that.data
    }

    fun itemClicked() {
        onItemClicked(data)
    }

    interface Visitor : BaseHolderModelVisitor {
        fun visit(employeeItemHM: EmployeeItemHM)
    }
}

class EmployeeItemVH(view: View) : BaseViewHolder<EmployeeItemHM>(view), EmployeeItemHM.Visitor {

    private val profilePhotoView: ImageView = view.findViewById(R.id.profile_image_view)
    private val nameView: TextView = view.findViewById(R.id.name_view)


    override fun visit(employeeItemHM: EmployeeItemHM) {
        itemView.setOnClickListener { employeeItemHM.itemClicked() }
        nameView.text = employeeItemHM.nameViewText

    }

    override fun recycle() {
        itemView.setOnClickListener(null)
    }
}