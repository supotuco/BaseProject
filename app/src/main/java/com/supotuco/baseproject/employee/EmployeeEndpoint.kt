package com.supotuco.baseproject.employee

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface EmployeeEndpoint {

    @GET("employees.json")
    fun employeesResponse(): Single<Response>

    data class Response(
            @SerializedName("employees")
            val employees: List<EmployeeServerData>
    )
}