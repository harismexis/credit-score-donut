package com.example.scoredonut.util.rx.schedulers

import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrampolineSchedulerProvider @Inject constructor(): BaseSchedulerProvider() {
    override fun computation() = Schedulers.trampoline()
    override fun ui() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
}