package com.example.myapplication.api

import com.example.myapplication.data.Projects
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    /**
     * Get list of projects for user
     */
    @GET("projects.json")
    fun getProjects(): Observable<Projects>
}