package com.supotuco.baseproject.employee

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Test

class EmployeeServerQueryTest {

    @Test
    fun employees_are_pulled_from_response() {
        // Given
        val employees = listOf(
                EmployeeServerData(
                        uuid = "",
                        fullName = "",
                        emailAddress = "",
                        team = "",
                        type = EmployeeServerData.Type.FULL_TIME
                )
        )
        val expectedResponse = EmployeeEndpoint.Response(
                employees = employees
        )
        val fakeEndpoint = FakeEndpoint()
        fakeEndpoint.response = Single.just(expectedResponse)
        val query = EmployeeServerQuery(
                fakeEndpoint,
                Schedulers.trampoline(),
                Schedulers.trampoline()
        )

        // When

        val actual = query.allEmployees().test()

        // Then

        actual.assertValue(
                listOf(
                        ValidatedEmployeeServerData(
                                uuid = "",
                                fullName = "",
                                emailAddress = "",
                                team = "",
                                type = EmployeeServerData.Type.FULL_TIME
                        )
                )
        )
    }

    @Test
    fun employee_data_is_validated() {
        // Given
        val employees = listOf(
                EmployeeServerData(
                        uuid = null,
                        fullName = null,
                        emailAddress = null,
                        team = null,
                        type = EmployeeServerData.Type.FULL_TIME
                )
        )
        val expectedResponse = EmployeeEndpoint.Response(
                employees = employees
        )
        val fakeEndpoint = FakeEndpoint()
        fakeEndpoint.response = Single.just(expectedResponse)
        val query = EmployeeServerQuery(
                fakeEndpoint,
                Schedulers.trampoline(),
                Schedulers.trampoline()
        )

        // When

        val actual = query.allEmployees().test()

        // Then

        actual.assertValue(emptyList())
    }

    private class FakeEndpoint : EmployeeEndpoint {

        var response: Single<EmployeeEndpoint.Response> = Single.never()

        override fun employeesResponse(): Single<EmployeeEndpoint.Response> {
            return response
        }
    }
}