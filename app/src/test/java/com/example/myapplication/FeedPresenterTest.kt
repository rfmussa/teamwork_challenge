package com.example.myapplication

import com.example.myapplication.api.ApiService
import com.example.myapplication.data.ProjectsUseCase
import com.example.myapplication.ui.feed.FeedPresenter
import com.example.myapplication.ui.feed.FeedView
import com.example.myapplication.ui.feed.FeedViewState
import com.squareup.moshi.Moshi
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

//TODO tests are not passing. there is some logic missing. Just here for demonstration

//TODO with a reducer we would only test the input Action and the output State
@RunWith(JUnit4::class)
class FeedPresenterTest {
	private val moshi = Moshi.Builder().build()

	private lateinit var retrofit: Retrofit
	private lateinit var mockWebServer: MockWebServer
	private lateinit var webService: ApiService

	@MockK(relaxed = true)
	private lateinit var mockView: FeedView

	@MockK(relaxed = true)
	private lateinit var useCase: ProjectsUseCase

	private lateinit var feedPresenter: FeedPresenter
	private val initialIntent: BehaviorSubject<Unit> = BehaviorSubject.create()

	@Before
	@Throws(Exception::class)
	fun beforeEachTest() {
		MockKAnnotations.init(this, relaxUnitFun = true)
		mockWebServer = MockWebServer()
		mockWebServer.start()

		RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
		RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

		//TODO ideally this would be handled with a different NetworkModule for tests
		retrofit = Retrofit.Builder()
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.baseUrl(mockWebServer.url("/").toString())
			.client(OkHttpClient())
			.build()

		webService = retrofit.create<ApiService>(ApiService::class.java)
	}

	@After
	@Throws(Exception::class)
	fun afterEachTest() {
		mockWebServer.shutdown()
		RxAndroidPlugins.reset()
	}


	@Test
	fun `It renders the list on initial intent`() {
		every { mockView.loadProjects() } returns initialIntent
		// every { useCase.getProjects() } returns Observable.just(FeedViewState.ProjectList(mockProjectResponse))
		feedPresenter = FeedPresenter(useCase)
		feedPresenter.attachView(mockView)

		/* When  load intent is called */
		initialIntent.onNext(Unit)

		// this.mockHttpResponse("mock_projects.json", HttpURLConnection.HTTP_OK)

		/* Then the first state is Loading */
		verify(exactly = 1, verifyBlock = { mockView.render(FeedViewState.Loading) })

		/* Then the list is displayed */
		// verify(exactly = 1, verifyBlock = { mockView.render(FeedViewState.ProjectList(mockProjectResponse)) })
	}

//    private val mockProjectResponse = listOf(
//        Projects.Project(name = "Project Zero", id = "322852"),
//        Projects.Project(name = "Project One", id = "124566")
//    )
}