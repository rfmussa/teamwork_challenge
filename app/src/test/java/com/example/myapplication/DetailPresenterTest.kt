package com.example.myapplication

import com.example.myapplication.data.Project
import com.example.myapplication.data.Task
import com.example.myapplication.ui.detail.DetailPresenter
import com.example.myapplication.usecases.ProjectsUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DetailPresenterTest {
	@MockK(relaxed = true)
	private lateinit var mockView: DetailPresenter.View

	@MockK(relaxed = true)
	private lateinit var useCase: ProjectsUseCase

	private lateinit var detailPresenter: DetailPresenter

	@Before
	@Throws(Exception::class)
	fun beforeEachTest() {
		MockKAnnotations.init(this, relaxUnitFun = true)
		RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
		detailPresenter = DetailPresenter(useCase)
	}

	@After
	@Throws(Exception::class)
	fun afterEachTest() {
		RxAndroidPlugins.reset()
	}


	@Test
	fun `It shows the project`() {
		every { useCase.getTasks(mockProject.id) } returns Observable.just(mockTasks)
		detailPresenter.project = mockProject
		detailPresenter.bind(mockView)


		verify {
			mockView.showProject(
				mockProject,
				mockTasks
			)
		}
	}

	@Test
	fun `It throws an exception`() {
		try {
			every { useCase.getTasks(mockProject.id) } returns Observable.just(mockTasks)
			detailPresenter.bind(mockView)
		} catch (throwable: Throwable) {
			assert((throwable.localizedMessage == "project is null. Set model before binding."))
		}
	}

	private val mockTasks = listOf(
		Task(name = "10"),
		Task(name = "11"),
		Task(name = "12")
	)

	private val mockProject = Project(id = "10")
}