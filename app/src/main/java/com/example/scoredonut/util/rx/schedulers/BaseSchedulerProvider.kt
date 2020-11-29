package com.example.scoredonut.util.rx.schedulers

import io.reactivex.Scheduler
import javax.inject.Singleton

@Singleton
abstract class BaseSchedulerProvider {
    abstract fun io(): Scheduler
    abstract fun computation(): Scheduler
    abstract fun ui(): Scheduler
}