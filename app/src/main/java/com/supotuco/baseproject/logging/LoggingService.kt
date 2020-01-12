package com.supotuco.baseproject.logging

import android.util.Log


interface LoggingService {

    fun debug(message: String)

    fun error(error: Throwable)
}

class ConsoleLoggingService : LoggingService {
    override fun debug(message: String) {
        Log.d(null, message)
    }

    override fun error(error: Throwable) {
        Log.e(null, error.message, error)
    }
}