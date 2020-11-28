package com.example.scoredonut.di.module

import com.example.scoredonut.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingsModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}
