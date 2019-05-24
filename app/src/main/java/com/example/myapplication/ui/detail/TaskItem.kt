package com.example.myapplication.ui.detail

import android.view.View
import com.example.myapplication.R
import com.example.myapplication.data.Task
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_project.*

class TaskItem constructor(private val task: Task) : Item() {

	override fun bind(viewHolder: ViewHolder, position: Int) {
		// Populate list
		viewHolder.title.text = task.content
		viewHolder.description.text = task.name
		viewHolder.logo.visibility = View.GONE
//		val containerAlpha = if (task.completed) {
//			0.3f
//		} else {
//			0.0f
//		}
//
//		viewHolder.containerView.alpha = containerAlpha
	}

	override fun getLayout() = R.layout.item_project
}