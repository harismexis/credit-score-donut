package com.example.scoredonut.di.module

import android.content.Context
import com.example.scoredonut.application.InstrumentedMainApplication
import dagger.Module
import dagger.Provides

@Module
class InstrumentedApplicationModule {

    @Provides
    fun providesContext(application: InstrumentedMainApplication): Context {
        return application.applicationContext
    }

}