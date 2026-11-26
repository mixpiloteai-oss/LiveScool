package com.livemachine.academy

import android.app.Application
import com.livemachine.academy.di.AppContainer

class LiveMachineApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
