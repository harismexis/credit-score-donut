package com.example.scoredonut.di.component

import com.example.scoredonut.application.InstrumentedMainApplication
import com.example.scoredonut.di.module.ActivityBindingsModule
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
        , InstrumentedApplicationModule::class
        //, ApplicationModule::class
    ]
)
interface InstrumentedMainComponent : AndroidInjector<InstrumentedMainApplication> {

//    @Component.Builder
//    interface Builder {
//
//        /**
//         * [BindsInstance] annotation is used for, if you want to bind particular object or instance
//         * of an object through the time of component construction
//         */
////        @BindsInstance
////        fun application(application: InstrumentedMainApplication) : Builder
//
//        fun build() : InstrumentedMainComponent
//    }

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: InstrumentedMainApplication) : InstrumentedMainComponent
    }


}