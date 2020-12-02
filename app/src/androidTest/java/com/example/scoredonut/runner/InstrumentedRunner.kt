package com.example.scoredonut.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.scoredonut.application.InstrumentedMainApplication

open class InstrumentedRunner : AndroidJUnitRunner() {

//    override fun onCreate(arguments: Bundle?) {
//        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
//        super.onCreate(arguments)
//    }

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, InstrumentedMainApplication::class.java.name, context)
    }
}