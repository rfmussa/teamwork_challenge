package com.example.myapplication.di.modules

import com.example.myapplication.MainActivity
import dagger.Module

@Module
abstract class ActivityModule {
    abstract fun contributeMainActivity(): MainActivity
}