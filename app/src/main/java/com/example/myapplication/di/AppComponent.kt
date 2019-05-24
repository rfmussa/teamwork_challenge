package com.example.myapplication.di

import com.example.myapplication.di.modules.AppModule
import com.example.myapplication.di.modules.NetworkModule
import com.example.myapplication.ui.detail.DetailFragment
import com.example.myapplication.ui.feed.FeedFragment
import dagger.Component

@Component(
	modules = [
		AppModule::class,
		NetworkModule::class,
		UseCaseBindingModule::class
	]
)
interface AppComponent {
	fun build(): AppComponent

	fun feedFragment(feedFragment: FeedFragment): FeedFragment

	fun detailFragment(feedFragment: DetailFragment): DetailFragment
}
