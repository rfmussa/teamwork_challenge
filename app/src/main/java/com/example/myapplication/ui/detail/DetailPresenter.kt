package com.example.myapplication.ui.detail

import com.example.myapplication.data.Projects
import com.example.myapplication.ui.BaseView
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailPresenter @Inject constructor() :
	MviBasePresenter<DetailView, DetailViewState>() {
	override fun bindIntents() {
		val initialIntent: Observable<DetailViewState> = intent(DetailView::loadProject)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.switchMap { Observable.just(DetailViewState.Project(it)) }

		subscribeViewState(initialIntent, DetailView::render)
	}
}

interface DetailView : BaseView<DetailViewState> {
	fun loadProject(): Observable<Projects.Project>
}

sealed class DetailViewState {
	data class Project(val project: Projects.Project) : DetailViewState()
}
