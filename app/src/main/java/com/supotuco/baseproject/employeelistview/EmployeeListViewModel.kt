package com.supotuco.baseproject.employeelistview

import androidx.lifecycle.ViewModel
import com.supotuco.baseproject.command.CommandHandler
import com.supotuco.baseproject.employee.EmployeeQuery
import com.supotuco.baseproject.employee.EmployeeServerData
import com.supotuco.baseproject.employee.ValidatedEmployeeServerData
import io.reactivex.Observable


class EmployeeListViewModel(
        private val employeeQuery: EmployeeQuery,
        private val selectHandler: CommandHandler<SelectEmployeeCommand>
) : ViewModel() {

    interface ViewState {
        val isLoadingSpinnerVisible: Boolean
        val listData: List<ValidatedEmployeeServerData>
    }

    internal data class FlatViewState(
            override val isLoadingSpinnerVisible: Boolean,
            override val listData: List<ValidatedEmployeeServerData>
    ) : ViewState


    fun viewState(): Observable<ViewState> {
        return employeeQuery.allEmployees()
                .map<ViewState> { listData ->
                    FlatViewState(
                            isLoadingSpinnerVisible = false,
                            listData = listData
                    )
                }
                .toObservable()
                .startWith(
                        FlatViewState(
                                isLoadingSpinnerVisible = true,
                                listData = emptyList()
                        )
                )
    }

    fun selectEmployee(uuid: String) {
        selectHandler.execute(SelectEmployeeCommand(employeeId = uuid))
    }


    data class SelectEmployeeCommand(val employeeId: String)
}