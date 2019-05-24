package com.example.myapplication.data


import com.squareup.moshi.Json

data class Task(
	val completed: Boolean = false,
	val content: String = "",
	@Json(name = "created-on")
	val createdOn: String = "",
	@Json(name = "creator-avatar-url")
	val creatorAvatarUrl: String = "",
	@Json(name = "creator-firstname")
	val creatorFirstName: String = "",
	@Json(name = "creator-lastname")
	val creatorLastName: String = "",
	val description: String = "",
	@Json(name = "due-date")
	val dueDate: String = "",
	@Json(name = "responsible-party-firstname")
	val responsiblePartyFirstname: String = "",
	@Json(name = "tasklist-private")
	val isPrivate: Boolean = false,
	@Json(name = "todo-list-name")
	val name: String = ""
)

data class Tasks(
	@Json(name = "STATUS")
	val sTATUS: String = "",
	@Json(name = "todo-items")
	val taskList: List<Task> = listOf()
)
