package com.example.scoredonut.di.module

import androidx.annotation.NonNull
import com.example.scoredonut.util.rx.schedulers.BaseSchedulerProvider
import com.example.scoredonut.util.rx.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class SchedulersModule {

    @NonNull
    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider()
    }

}