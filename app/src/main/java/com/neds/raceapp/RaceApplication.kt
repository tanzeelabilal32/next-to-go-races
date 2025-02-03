package com.neds.raceapp

import android.app.Application
import com.neds.raceapp.di.appModule
import org.koin.core.context.GlobalContext.startKoin

class RaceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(appModule) }
    }
}