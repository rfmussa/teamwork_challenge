package com.example.myapplication.ui.feed

import com.example.myapplication.R
import com.example.myapplication.data.Projects
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_project.*

class ProjectItem constructor(val project: Projects.Project) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        // Populate list
        viewHolder.title.text = project.name
        viewHolder.description.text = project.name
        // Set image
        Picasso.get()
            .load(project.logo)
            .into(viewHolder.logo)

        // Set item specific transition names
        viewHolder.logo.transitionName = project.logo
        viewHolder.title.transitionName = project.name
    }

    override fun getLayout() = R.layout.item_project

}