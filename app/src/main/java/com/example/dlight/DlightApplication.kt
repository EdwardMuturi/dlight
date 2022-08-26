package com.example.dlight

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class DlightApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }



    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@DlightApplication)
            modules()
        }
    }

}