package com.example.scoredonut.di.component

import com.example.scoredonut.application.InstrumentedMainApplication
import com.example.scoredonut.di.module.ActivityBindingsModule
import com.example.scoredonut.di.module.InstrumentedApplicationModule
import com.example.scoredonut.viewmodel.factory.InstrumentedViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingsModule::class,
        InstrumentedViewModelModule::class,
        InstrumentedApplicationModule::class
    ]
)
interface InstrumentedMainComponent : AndroidInjector<InstrumentedMainApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: InstrumentedMainApplication): InstrumentedMainComponent
    }

}