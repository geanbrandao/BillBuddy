package com.geanbrandao.br.billbuddy.di

import android.content.Context
import androidx.room.Room
import com.geanbrandao.br.billbuddy.data.local.database.DB_NAME
import com.geanbrandao.br.billbuddy.data.local.database.Database
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.geanbrandao.br.billbuddy")
class AppModule {

    @Single
    fun provideDatabase(appContext: Context): Database {
        return Room.databaseBuilder(
            context = appContext,
            klass = Database::class.java,
            name = DB_NAME,
        ).build()
    }

    @Single
    fun provideAppDao(database: Database) = database.appDao
}
