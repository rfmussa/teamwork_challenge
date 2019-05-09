package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.AppComponent
import com.example.myapplication.di.AppComponentProvider
import com.example.myapplication.di.DaggerAppComponent
import com.example.myapplication.di.modules.NetworkModule

class App : Application(), AppComponentProvider {
    override val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .networkModule(NetworkModule(" /")).build()
    }

    override fun onCreate() {
        super.onCreate()
        component.build()
    }
}