package com.supotuco.baseproject.employeelistview

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.supotuco.baseproject.EmployeeDirectoryApplication
import com.supotuco.baseproject.R
import com.supotuco.baseproject.dynamicadapter.BaseHolderModel
import com.supotuco.baseproject.dynamicadapter.DynamicRecyclerAdapter
import com.supotuco.baseproject.employee.EmployeeQuery
import com.supotuco.baseproject.employee.EmployeeServerData
import com.supotuco.baseproject.employee.ValidatedEmployeeServerData
import com.supotuco.baseproject.employeeview.EmployeeItemHM
import com.supotuco.baseproject.logging.LoggingService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.Exception


class EmployeeListView: Fragment() {

    private val adapter = DynamicRecyclerAdapter()
    private val compositeDisposable = CompositeDisposable()
    lateinit var viewModel: EmployeeListViewModel
    lateinit var loggingService: LoggingService

    private lateinit var swipeToRefreshView: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_employee_list_view, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.root_recycler_view)
        recyclerView.adapter = adapter

        swipeToRefreshView = rootView.findViewById(R.id.swipe_to_refresh_layout)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context!!.applicationContext as EmployeeDirectoryApplication)
                .provideCompositionRoot()
                .inject(this)
    }

    override fun onResume() {
        super.onResume()

        hookRefreshView()
        val subscription = hookViewState()

        compositeDisposable.add(subscription)
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }

    private fun hookRefreshView() {
        swipeToRefreshView.isEnabled = false
    }

    private fun hookViewState(): Disposable {
        return viewModel.viewState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::render, this::handleError)
    }

    private fun render(viewState: EmployeeListViewModel.ViewState) {
        swipeToRefreshView.isRefreshing = viewState.isLoadingSpinnerVisible

        adapter.replace(viewState.listData.map(this::createEmployeeItem))
    }

    private fun handleError(e: Throwable) {
        loggingService.error(e)

        val context = context ?: return
        AlertDialog.Builder(context)
                .setTitle("Something went wrong")
                .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
    }

    private fun createEmployeeItem(data: ValidatedEmployeeServerData): BaseHolderModel {
        return EmployeeItemHM(data) { viewModel.selectEmployee(data.uuid) }
    }

    companion object {
        val FRAGMENT_TAG = "${EmployeeListView::class.java}_tag"
    }
}