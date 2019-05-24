package com.example.myapplication.usecases

import com.example.myapplication.api.ApiService
import com.example.myapplication.data.Project
import com.example.myapplication.data.Task
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface ProjectsUseCase {
	fun getProjects(): Observable<List<Project>>

	fun getTasks(id: String): Observable<List<Task>>
}

internal class ProjectsUseCaseImpl @Inject constructor(private val api: ApiService) :
	ProjectsUseCase {
	override fun getProjects(): Observable<List<Project>> =
		api.getProjects()
			.map { data ->
				data.projects.let { set ->
					return@map set
				}
			}
			.onErrorReturn {
				emptyList()
			}
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())

	override fun getTasks(id: String): Observable<List<Task>> =
		api.getTasks(id)
			.map { data ->
				data.taskList.let {
					return@map it
				}
			}
			.onErrorReturn { emptyList() }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
}
