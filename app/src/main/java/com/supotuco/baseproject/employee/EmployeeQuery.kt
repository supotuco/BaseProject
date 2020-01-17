package com.supotuco.baseproject.employee

import io.reactivex.Observable
import io.reactivex.Single


interface EmployeeQuery {

    fun allEmployees(): Single<List<ValidatedEmployeeServerData>>
}