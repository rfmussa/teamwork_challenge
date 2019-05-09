package com.example.myapplication.data

import com.example.myapplication.api.ApiService
import com.example.myapplication.ui.feed.FeedViewState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface ProjectsUseCase {
	fun getProjects(): Observable<FeedViewState>
}

internal class ProjectsUseCaseImpl @Inject constructor(private val api: ApiService) : ProjectsUseCase {
	override fun getProjects(): Observable<FeedViewState> =
		api.getProjects()
			.map { data ->
				data.projects.let { set ->
					if (set.isEmpty()) {
						return@map FeedViewState.Error("empty")
					} else {
						return@map FeedViewState.ProjectList(set)
					}
				}
			}
			.startWith(FeedViewState.Loading)
			.onErrorReturn { throwable -> FeedViewState.Error(throwable.localizedMessage) }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
}
