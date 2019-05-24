package com.example.myapplication.data


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class Projects(
	@Json(name = "projects")
	val projects: List<Project>,
	@Json(name = "STATUS")
	val status: String
)

@Parcelize
data class Project(
	@Json(name = "announcement")
	val announcement: String = "",
	@Json(name = "created-on")
	val createdOn: String = "",
	@Json(name = "description")
	val description: String = "",
	@Json(name = "endDate")
	val endDate: String = "",
	@Json(name = "id")
	val id: String,
	@Json(name = "isProjectAdmin")
	val isProjectAdmin: Boolean = false,
	@Json(name = "last-changed-on")
	val lastChangedOn: String = "",
	@Json(name = "logo")
	val logo: String = "",
	@Json(name = "name")
	val name: String = "",
	val starred: Boolean = false,
	@Json(name = "startDate")
	val startDate: String = "",
	@Json(name = "status")
	val status: String = "",
	@Json(name = "subStatus")
	val subStatus: String = ""
) : Parcelable