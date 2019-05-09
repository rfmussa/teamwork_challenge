package com.example.myapplication.di

import androidx.fragment.app.Fragment

interface AppComponentProvider {
	val component: AppComponent
}

val Fragment.appComponent get() = (activity!!.application as AppComponentProvider).component