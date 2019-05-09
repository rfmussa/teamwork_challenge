package com.example.myapplication.ui

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseView<in VS> : MvpView {
    fun render(vs: VS)
}