package com.supotuco.baseproject.employeelistview

import com.supotuco.baseproject.command.CommandHandler
import com.supotuco.baseproject.employee.EmployeeQuery
import com.supotuco.baseproject.employee.EmployeeServerData
import com.supotuco.baseproject.employeelistview.EmployeeListViewModel.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.junit.Assert.*
import org.junit.Test

class EmployeeListViewModelTest {

    @Test
    fun selecting_an_employee_triggers_command() {
        // Given
        val query = FakeEmployeeQuery()
        val commandHistory = CommandHistory<SelectEmployeeCommand>()
        val viewModel = EmployeeListViewModel(
                query,
                commandHistory
        )

        val employeeId = "10"

        // When

        viewModel.selectEmployee(uuid = employeeId)

        // Then
        commandHistory.assertCommandExecuted(
                SelectEmployeeCommand(employeeId)
        )
    }

    @Test
    fun view_state_shows_loading_initially() {
        // Given
        val query = FakeEmployeeQuery()
        val commandHistory = CommandHistory<SelectEmployeeCommand>()
        val viewModel = EmployeeListViewModel(
                query,
                commandHistory
        )

        // When

        val actual = viewModel.viewState().test()

        // Then

        actual.assertValue(
                FlatViewState(
                        isLoadingSpinnerVisible = true,
                        listData = emptyList()
                )
        )
    }

    @Test
    fun view_state_shows_list_of_values_after_loading() {
        // Given
        val query = FakeEmployeeQuery()
        val commandHistory = CommandHistory<SelectEmployeeCommand>()
        val viewModel = EmployeeListViewModel(
                query,
                commandHistory
        )
        val employeeData = listOf(createTestEmployee())

        // When

        val actual = viewModel.viewState().test()
        query.employeeStream.onNext(employeeData)

        // Then

        actual.assertValues(
                FlatViewState(
                        isLoadingSpinnerVisible = true,
                        listData = emptyList()
                ),
                FlatViewState(
                        isLoadingSpinnerVisible = false,
                        listData = employeeData
                )
        )
    }

    private class FakeEmployeeQuery : EmployeeQuery {
        val employeeStream: BehaviorSubject<List<EmployeeServerData>> = BehaviorSubject.create()

        override fun allEmployees(): Observable<List<EmployeeServerData>> {
            return employeeStream
        }
    }

    private class CommandHistory<T> : CommandHandler<T> {

        private val history: MutableList<T> = mutableListOf()

        override fun execute(command: T) {
            history.add(command)
        }

        fun assertCommandExecuted(command: T) {
            assertTrue(
                    "$command was not executed",
                    history.contains(command)
            )
        }
    }

    private fun createTestEmployee(
            uuid: String = "",
            fullName: String = "",
            phoneNumber: String? = null,
            emailAddress: String = "",
            biography: String? = null,
            photoUrlSmall: String? = null,
            photoUrlLarge: String? = null,
            team: String = "",
            type: EmployeeServerData.Type = EmployeeServerData.Type.FULL_TIME
    ): EmployeeServerData {
        return EmployeeServerData(
                uuid = uuid,
                fullName =fullName,
                phoneNumber = phoneNumber,
                emailAddress = emailAddress,
                biography = biography,
                photoUrlSmall = photoUrlSmall,
                photoUrlLarge = photoUrlLarge,
                team = team,
                type = type
        )
    }
}