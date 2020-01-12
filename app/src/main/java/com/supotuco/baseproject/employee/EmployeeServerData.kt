package com.supotuco.baseproject.employee


/**
 * Might be good to convert to an interface but in the interest of time
 * using the raw data
 */
data class EmployeeServerData(
        val uuid: String,
        val fullName: String,
        val emailAddress: String,
        val team: String,
        val type: Type,
        val phoneNumber: String? = null,
        val biography: String? = null,
        val photoUrlSmall: String? = null,
        val photoUrlLarge: String? = null
) {

    enum class Type {
        FULL_TIME,
        PART_TIME,
        CONTRACT
    }
}