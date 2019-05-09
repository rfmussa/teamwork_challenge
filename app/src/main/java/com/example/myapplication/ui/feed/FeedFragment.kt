package com.example.myapplication.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.di.appComponent
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.item_project.view.*
import javax.inject.Inject

class FeedFragment : MviFragment<FeedView, FeedPresenter>(), FeedView {

	private val groupAdapter: GroupAdapter<ViewHolder> = GroupAdapter()
	private var loadSubject: BehaviorSubject<Unit> = BehaviorSubject.create<Unit>()

	@Inject
	lateinit var presenter: FeedPresenter

	override fun createPresenter(): FeedPresenter = presenter

	override fun onCreate(savedInstanceState: Bundle?) {
		appComponent.fragment(this)
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		setHasOptionsMenu(true)
		return inflater.inflate(R.layout.fragment_list, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupRecyclerView()
		postponeEnterTransition()
		view.doOnPreDraw { startPostponedEnterTransition() }

		loadSubject.onNext(Unit)

	}

	override fun loadProjects(): BehaviorSubject<Unit> = loadSubject

	override fun render(vs: FeedViewState) {
		when (vs) {
			is FeedViewState.Loading -> {
				progressView.show()
				recyclerView.visibility = View.INVISIBLE
			}
			is FeedViewState.ProjectList -> {
				progressView.hide()
				recyclerView.visibility = View.VISIBLE
				val itemList = vs.projectList
					.map { ProjectItem(it) }
					.toList()

				groupAdapter.update(itemList)
			}
			is FeedViewState.Error -> {
				progressView.hide()
				recyclerView.visibility = View.INVISIBLE
				errorView.text = vs.msg
				errorView.visibility = View.VISIBLE
			}
		}
	}

	private fun setupRecyclerView() {
		recyclerView.apply {
			layoutManager = LinearLayoutManager(this.context)
			adapter = groupAdapter
			addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
		}

		//TODO ideally this would be Passed as an Intent to the Presenter
		groupAdapter.setOnItemClickListener { item, view ->
			// Pass item to Bundle
			val bundle = bundleOf("project" to (item as ProjectItem).project)

			// Add shared element transitions
			val extras = FragmentNavigator.Extras.Builder()
				.addSharedElement(view.logo, ViewCompat.getTransitionName(view.logo)!!)
				.addSharedElement(view.title, "title")
				.build()

			// Push action to navigation controller
			view.findNavController().navigate(R.id.project_click, bundle, null, extras)
		}
	}
}