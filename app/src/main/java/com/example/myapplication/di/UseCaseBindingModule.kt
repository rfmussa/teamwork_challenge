package com.example.myapplication.di

import com.example.myapplication.usecases.ProjectsUseCase
import com.example.myapplication.usecases.ProjectsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class UseCaseBindingModule {
	@Binds
	abstract fun bindProjectsUsecase(networkStateImpl: ProjectsUseCaseImpl): ProjectsUseCase
}
