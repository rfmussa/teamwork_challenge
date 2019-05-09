package com.example.myapplication.di

import com.example.myapplication.data.ProjectsUseCase
import com.example.myapplication.data.ProjectsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class UseCaseBindingModule {
	@Binds
	abstract fun bindProjectsUsecase(networkStateImpl: ProjectsUseCaseImpl): ProjectsUseCase
}
