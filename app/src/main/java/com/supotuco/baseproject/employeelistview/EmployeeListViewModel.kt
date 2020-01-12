package com.supotuco.baseproject.employeelistview

import androidx.lifecycle.ViewModel
import com.supotuco.baseproject.command.CommandHandler
import com.supotuco.baseproject.employee.EmployeeQuery
import com.supotuco.baseproject.employee.EmployeeServerData
import io.reactivex.Observable


class EmployeeListViewModel(
        private val employeeQuery: EmployeeQuery,
        private val selectHandler: CommandHandler<SelectEmployeeCommand>
) : ViewModel() {

    interface ViewState {
        val isLoadingSpinnerVisible: Boolean
        val listData: List<EmployeeServerData>
    }

    internal data class FlatViewState(
            override val isLoadingSpinnerVisible: Boolean,
            override val listData: List<EmployeeServerData>
    ) : ViewState


    fun viewState(): Observable<ViewState> {
        return employeeQuery.allEmployees()
                .map<ViewState> { listData ->
                    FlatViewState(
                            isLoadingSpinnerVisible = false,
                            listData = listData
                    )
                }
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