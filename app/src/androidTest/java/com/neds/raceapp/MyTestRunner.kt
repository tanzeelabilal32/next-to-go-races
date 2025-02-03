package com.neds.raceapp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class MyTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, "com.neds.raceapp.RaceApplication", context)
    }
}
