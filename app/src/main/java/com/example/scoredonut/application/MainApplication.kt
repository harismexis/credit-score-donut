package com.example.scoredonut.application

import com.example.scoredonut.di.component.DaggerMainComponent
import com.example.scoredonut.di.component.MainComponent
import com.example.scoredonut.di.module.ApplicationModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

class MainApplication : DaggerApplication(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

     private lateinit var mainComponent: MainComponent

    override fun onCreate() {
        super.onCreate()
        initRxErrorHandler()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        mainComponent = DaggerMainComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        mainComponent.inject(this)
        return mainComponent
    }

//    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//        return DaggerMainComponent.builder().application(this).build()
//    }

    private fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler { ex: Throwable ->

        }
    }

}