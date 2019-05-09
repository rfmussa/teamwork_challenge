package com.example.myapplication.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.myapplication.R
import com.example.myapplication.data.Projects
import com.example.myapplication.di.appComponent
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject.create
import kotlinx.android.synthetic.main.fragment_project.*
import javax.inject.Inject

class DetailFragment : MviFragment<DetailView, DetailPresenter>(), DetailView {
	override fun loadProject(): Observable<Projects.Project> = initialSubject

	private val initialSubject = create<Projects.Project>()

	@Inject
	lateinit var presenter: DetailPresenter

	override fun createPresenter(): DetailPresenter = presenter

	override fun onCreate(savedInstanceState: Bundle?) {
		appComponent.detailFragment(this)
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
		val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {

		}
		sharedElementEnterTransition = transition
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_project, container, false)
	}

	override fun onResume() {
		super.onResume()
		initialSubject.onNext(arguments!!.getParcelable("project") as Projects.Project)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		ViewCompat.setTransitionName(logo, (arguments!!.getParcelable("project") as Projects.Project).logo)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> findNavController().navigateUp()
		}
		return super.onOptionsItemSelected(item)
	}

	override fun render(vs: DetailViewState) {
		when (vs) {
			is DetailViewState.Project -> {
				Picasso.get()
					.load(vs.project.logo).into(logo)
				toolbar.title = vs.project.name
				description.text = vs.project.description
				category.text = vs.project.category.name
				owner.text = vs.project.status
			}
		}
	}
}
