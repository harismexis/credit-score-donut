package com.example.scoredonut.di.component

import android.content.Context
import com.example.scoredonut.application.InstrumentedMainApplication
import dagger.Module
import dagger.Provides

@Module
class InstrumentedApplicationModule {

//    //@Provides
//    fun provideApplication(): Application {
//        return application
//    }

    @Provides
    fun providesContext(application: InstrumentedMainApplication): Context {
        return application.applicationContext
    }

}