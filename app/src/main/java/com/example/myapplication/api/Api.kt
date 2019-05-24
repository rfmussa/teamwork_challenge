package com.example.myapplication.api

import com.example.myapplication.data.Projects
import com.example.myapplication.data.Task
import com.example.myapplication.data.Tasks
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
	/**
	 * Get list of projects for user
	 */
	@GET("projects.json")
	fun getProjects(): Observable<Projects>

	/**
	 * Get list of tasks for project
	 */
	@GET("projects/{Id}/tasks.json")
	fun getTasks(@Path("Id") projectId: String) : Observable<Tasks>
}