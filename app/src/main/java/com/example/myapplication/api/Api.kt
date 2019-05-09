package com.example.myapplication.api

import com.example.myapplication.data.Projects
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
	/**
	 * Get list of projects for user
	 */
	@GET("projects.json")
	fun getProjects(): Observable<Projects>
}