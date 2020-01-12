package com.supotuco.baseproject

import android.app.Application
import com.supotuco.baseproject.compositionroot.CompositionRoot


class EmployeeDirectoryApplication : Application() {

    private lateinit var compositionRoot: CompositionRoot

    override fun onCreate() {
        super.onCreate()
        compositionRoot = CompositionRoot()
    }

    fun provideCompositionRoot(): CompositionRoot {
        return compositionRoot
    }
}