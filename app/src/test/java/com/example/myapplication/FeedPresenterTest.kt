package com.example.myapplication

import com.example.myapplication.data.Project
import com.example.myapplication.usecases.ProjectsUseCase
import com.example.myapplication.ui.feed.FeedPresenter
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.mockk.verifyAll
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FeedPresenterTest {
	@MockK(relaxed = true)
	private lateinit var mockView: FeedPresenter.View

	@MockK(relaxed = true)
	private lateinit var useCase: ProjectsUseCase

	private lateinit var feedPresenter: FeedPresenter
	private val clickSubject: BehaviorSubject<Project> = BehaviorSubject.create()

	@Before
	@Throws(Exception::class)
	fun beforeEachTest() {
		MockKAnnotations.init(this, relaxUnitFun = true)
		RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

		feedPresenter = FeedPresenter(useCase)
	}

	@After
	@Throws(Exception::class)
	fun afterEachTest() {
		RxAndroidPlugins.reset()
	}


	@Test
	fun `It renders the list`() {
		every { useCase.getProjects() } returns Observable.just(mockProjectList)
		every { mockView.projectClick() } returns clickSubject

		feedPresenter.bind(mockView)


		verify{
			mockView.showProjects(mockProjectList)
		}
	}

	@Test
	fun `It renders the error`() {
		every { useCase.getProjects() } returns Observable.just(emptyList())
		feedPresenter.bind(mockView)

		/* Then the first state is Loading */
		verify {
			mockView.showProjects(emptyList()) wasNot Called
		}

		verify(exactly = 1, verifyBlock = { mockView.showEmpty() })
	}

	@Test
	fun `It handles clicks properly`() {
		feedPresenter.bind(mockView)

		clickSubject.onNext(mockProject)

		verifyAll {
			mockView.projectClick()
		}
	}

	@Test
	fun `It checks that show hide progress dialog are called once`() {
		every { useCase.getProjects() } returns Observable.just(mockProjectList)

		feedPresenter.bind(mockView)

		verify(exactly = 1, verifyBlock = { mockView.showProgress() })
	}

	@Test
	fun `It checks that project list is reused`() {
		feedPresenter.projectList = mockProjectList
		feedPresenter.bind(mockView)

		verifyAll {
			useCase.getProjects() wasNot Called
		}

		verify(exactly = 1, verifyBlock = { mockView.showProjects(mockProjectList) })
	}

	private val mockProjectList = listOf(
		Project(id = "10"),
		Project(id = "11"),
		Project(id = "12")
	)

	private val mockProject = Project(id = "10")
}