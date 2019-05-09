package com.example.myapplication.di.modules

import com.example.myapplication.MainActivity
import dagger.Module

@Module
abstract class ActivityBindingModule {
	abstract fun providesMainActivity(): MainActivity
}