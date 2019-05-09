package com.example.myapplication.ui.feed

import android.annotation.SuppressLint
import com.example.myapplication.data.Projects
import com.example.myapplication.data.ProjectsUseCase
import com.example.myapplication.ui.BaseView
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


//TODO A production app would also use a reducer
class FeedPresenter @Inject constructor(
	private val useCase: ProjectsUseCase
) : MviBasePresenter<FeedView, FeedViewState>() {
	@SuppressLint("CheckResult")
	override fun bindIntents() {
		val projects: Observable<FeedViewState> = intent(FeedView::loadProjects)
			.subscribeOn(Schedulers.io())
			.switchMap { useCase.getProjects() }
			.observeOn(AndroidSchedulers.mainThread())

		subscribeViewState(projects, FeedView::render)
	}
}

interface FeedView : BaseView<FeedViewState> {
	fun loadProjects(): BehaviorSubject<Unit>
}


sealed class FeedViewState {
	object Loading : FeedViewState()
	data class ProjectList(val projectList: List<Projects.Project>) : FeedViewState()
	data class Error(val msg: String) : FeedViewState()
}