package com.example.scoredonut.di.module

import android.content.Context
import com.example.scoredonut.application.MainApplication
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule {

//    //@Provides
//    fun provideApplication(): Application {
//        return application
//    }

    @Provides
    fun providesContext(application: MainApplication): Context {
        return application.applicationContext
    }

}