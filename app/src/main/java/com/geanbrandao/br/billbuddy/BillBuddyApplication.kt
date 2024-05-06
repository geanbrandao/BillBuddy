package com.geanbrandao.br.billbuddy

import android.app.Application
import com.geanbrandao.br.billbuddy.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

class BillBuddyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BillBuddyApplication)
            modules(AppModule().module)
        }
    }

}