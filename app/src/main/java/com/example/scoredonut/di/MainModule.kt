package com.example.scoredonut.di

import androidx.annotation.NonNull
import com.example.scoredonut.util.BaseSchedulerProvider
import com.example.scoredonut.util.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @NonNull
    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider()
    }
}