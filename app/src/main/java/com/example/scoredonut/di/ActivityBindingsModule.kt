package com.example.scoredonut.di

import com.example.scoredonut.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingsModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}
