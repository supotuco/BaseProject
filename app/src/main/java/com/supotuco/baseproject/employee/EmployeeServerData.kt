package com.supotuco.baseproject.employee

import com.google.gson.annotations.SerializedName


data class ValidatedEmployeeServerData(
        @SerializedName("uuid")
        val uuid: String,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("email_address")
        val emailAddress: String,
        @SerializedName("team")
        val team: String,
        @SerializedName("employee_type")
        val type: EmployeeServerData.Type?,
        @SerializedName("phone_number")
        val phoneNumber: String? = null,
        @SerializedName("biography")
        val biography: String? = null,
        @SerializedName("photo_url_small")
        val photoUrlSmall: String? = null,
        @SerializedName("photo_url_large")
        val photoUrlLarge: String? = null
)

/**
 * Might be good to convert to an interface but in the interest of time
 * using the raw data
 */
data class EmployeeServerData(
        @SerializedName("uuid")
        val uuid: String?,
        @SerializedName("full_name")
        val fullName: String?,
        @SerializedName("email_address")
        val emailAddress: String?,
        @SerializedName("team")
        val team: String?,
        @SerializedName("employee_type")
        val type: Type?,
        @SerializedName("phone_number")
        val phoneNumber: String? = null,
        @SerializedName("biography")
        val biography: String? = null,
        @SerializedName("photo_url_small")
        val photoUrlSmall: String? = null,
        @SerializedName("photo_url_large")
        val photoUrlLarge: String? = null
) {

    enum class Type {
        FULL_TIME,
        PART_TIME,
        CONTRACT
    }
}