package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.di.modules.ActivityBindingModule
import com.example.myapplication.di.modules.AppModule
import com.example.myapplication.di.modules.NetworkModule
import com.example.myapplication.ui.detail.DetailFragment
import com.example.myapplication.ui.feed.FeedFragment
import dagger.BindsInstance
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

    fun fragment(feedFragment: FeedFragment): FeedFragment

    fun detailFragment(detailFragment: DetailFragment): DetailFragment
}
