package com.supotuco.baseproject.compositionroot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.supotuco.baseproject.command.CommandHandler
import com.supotuco.baseproject.employee.*
import com.supotuco.baseproject.employeelistview.EmployeeListView
import com.supotuco.baseproject.employeelistview.EmployeeListViewModel
import com.supotuco.baseproject.logging.ConsoleLoggingService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class CompositionRoot {

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://s3.amazonaws.com/sq-mobile-interview/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val employeeQuery: EmployeeQuery

    init {

        val employeeEndpoint = retrofit.create(EmployeeEndpoint::class.java)

        employeeQuery = SuccessCacheQuery(EmployeeServerQuery(
                endpoint = employeeEndpoint,
                ioScheduler = Schedulers.io(),
                computationScheduler = Schedulers.computation()
        ))

    }

    private fun employeeServerQuery(): EmployeeQuery {
        return employeeQuery
    }

    private fun employeeFakeQuery(): EmployeeQuery {
        return object : EmployeeQuery {
            override fun allEmployees(): Single<List<ValidatedEmployeeServerData>> {
                val fakeData = listOf(
                        ValidatedEmployeeServerData(
                                uuid = "fakeUuid",
                                fullName = "fake full name",
                                emailAddress = "fake email",
                                type = EmployeeServerData.Type.FULL_TIME,
                                team = "team"
                        )
                )
                return Single.just(fakeData)
            }
        }
    }


    fun inject(listView: EmployeeListView) {
        listView.viewModel = EmployeeListViewModel(
                employeeServerQuery(),
                object : CommandHandler<EmployeeListViewModel.SelectEmployeeCommand> {
                    override fun execute(command: EmployeeListViewModel.SelectEmployeeCommand) {

                    }

                }
        )
        listView.loggingService = ConsoleLoggingService()
    }
}