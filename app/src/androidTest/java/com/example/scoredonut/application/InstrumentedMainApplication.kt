package com.example.scoredonut.application

import com.example.scoredonut.di.component.DaggerInstrumentedMainComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class InstrumentedMainApplication : DaggerApplication()
{
//    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//        val component = DaggerInstrumentedMainComponent.builder().build()
//        component.inject(this)
//        return component
//    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val mainComponent = DaggerInstrumentedMainComponent.factory().create(this)
        mainComponent.inject(this)
        return mainComponent
    }
}