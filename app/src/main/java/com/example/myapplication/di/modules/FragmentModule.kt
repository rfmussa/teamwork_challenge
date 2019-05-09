package com.example.myapplication.di.modules

import android.app.Application
import com.example.myapplication.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FragmentModule {
	@Provides
	@Singleton
	fun provideApplication(application: Application) = application as App
}