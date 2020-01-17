package com.supotuco.baseproject.employee

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject

class EmployeeServerQuery(
        private val endpoint: EmployeeEndpoint,
        private val ioScheduler: Scheduler,
        private val computationScheduler: Scheduler
        ) : EmployeeQuery {

    override fun allEmployees(): Single<List<ValidatedEmployeeServerData>> {
        return endpoint
                .employeesResponse()
                .subscribeOn(ioScheduler)
                .observeOn(computationScheduler)
                .map { it.employees }
                .map { it.mapNotNull { employee -> validatedEmployee(employee) } }
    }

    private fun validatedEmployee(employeeServerData: EmployeeServerData): ValidatedEmployeeServerData? {

        return with(employeeServerData) {
            if(uuid == null || fullName == null || emailAddress == null || team == null) {
                return@with null
            }
            return@with ValidatedEmployeeServerData(
                    uuid = uuid,
                    fullName = fullName,
                    emailAddress = emailAddress,
                    team = team,
                    type = type,
                    photoUrlLarge = photoUrlLarge,
                    photoUrlSmall = photoUrlSmall,
                    phoneNumber = phoneNumber
            )
        }
    }
}

class SuccessCacheQuery(
        val query: EmployeeQuery
) : EmployeeQuery {

    private val successSingle = SingleSubject.create<List<ValidatedEmployeeServerData>>()
    private val replaySingle = successSingle.cache()

    override fun allEmployees(): Single<List<ValidatedEmployeeServerData>> {
        return Single.amb(listOf(replaySingle, query.allEmployees()))
                .doOnSuccess { successSingle.onSuccess(it) }
    }
}