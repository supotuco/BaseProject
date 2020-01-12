package com.supotuco.baseproject.compositionroot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.supotuco.baseproject.command.CommandHandler
import com.supotuco.baseproject.employee.EmployeeQuery
import com.supotuco.baseproject.employee.EmployeeServerData
import com.supotuco.baseproject.employeelistview.EmployeeListView
import com.supotuco.baseproject.employeelistview.EmployeeListViewModel
import com.supotuco.baseproject.logging.ConsoleLoggingService
import io.reactivex.Observable


class CompositionRoot {

    init {
    }


    fun inject(listView: EmployeeListView) {
        listView.viewModel = EmployeeListViewModel(
                object : EmployeeQuery {
                    override fun allEmployees(): Observable<List<EmployeeServerData>> {
                        val fakeData = listOf(
                                EmployeeServerData(
                                        uuid = "fakeUuid",
                                        fullName = "fake full name",
                                        emailAddress = "fake email",
                                        type = EmployeeServerData.Type.FULL_TIME,
                                        team = "team"
                                )
                        )
                        return Observable.just(fakeData)
                    }
                },
                object : CommandHandler<EmployeeListViewModel.SelectEmployeeCommand> {
                    override fun execute(command: EmployeeListViewModel.SelectEmployeeCommand) {

                    }

                }
        )
        listView.loggingService = ConsoleLoggingService()
    }
}