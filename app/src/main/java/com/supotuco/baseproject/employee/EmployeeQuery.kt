package com.supotuco.baseproject.employee

import io.reactivex.Observable


interface EmployeeQuery {

    fun allEmployees(): Observable<List<EmployeeServerData>>
}