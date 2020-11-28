package com.example.scoredonut.di.module


import android.app.Application
import android.content.Context

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    fun providesContext(): Context {
        return application.applicationContext
    }

}