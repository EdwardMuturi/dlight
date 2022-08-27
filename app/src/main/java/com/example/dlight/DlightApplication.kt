package com.example.dlight

import android.app.Application
import com.example.dlight.di.dlightModules
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level
import timber.log.Timber

class DlightApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        setUpLogger()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@DlightApplication)
            modules(dlightModules)
        }
    }

    private fun setUpLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                @Nullable
                override fun createStackElementTag(@NotNull element: StackTraceElement): String? {
                    return super.createStackElementTag(element) + ":" + element.lineNumber
                }
            })
        }
    }

}