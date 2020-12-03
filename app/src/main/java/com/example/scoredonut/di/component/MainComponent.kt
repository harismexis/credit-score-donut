package com.example.scoredonut.di.component

import com.example.scoredonut.application.MainApplication
import com.example.scoredonut.di.module.ActivityBindingsModule
import com.example.scoredonut.di.module.ApplicationModule
import com.example.scoredonut.viewmodel.factory.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingsModule::class
         , ViewModelModule::class
        , ApplicationModule::class
    ]
)
interface MainComponent : AndroidInjector<MainApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: MainApplication) : MainComponent
    }

}