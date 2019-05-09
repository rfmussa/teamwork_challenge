package com.example.myapplication.data


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class Projects(
    @Json(name = "projects")
    val projects: List<Project>,
    @Json(name = "STATUS")
    val sTATUS: String
) {
    @Parcelize
    data class Project(
        @Json(name = "announcement")
        val announcement: String = "",
        @Json(name = "category")
        val category: Category ,
        @Json(name = "company")
        val company: Company,
        @Json(name = "created-on")
        val createdOn: String = "",
        @Json(name = "description")
        val description: String = "",
        @Json(name = "endDate")
        val endDate: String = "",
        @Json(name = "id")
        val id: String,
        @Json(name = "isProjectAdmin")
        val isProjectAdmin: Boolean,
        @Json(name = "last-changed-on")
        val lastChangedOn: String,
        @Json(name = "logo")
        val logo: String,
        @Json(name = "name")
        val name: String,
        val starred: Boolean,
        @Json(name = "startDate")
        val startDate: String,
        @Json(name = "status")
        val status: String,
        @Json(name = "subStatus")
        val subStatus: String = ""
    ) : Parcelable {
        @Parcelize
        data class Company(
            @Json(name = "id")
            val id: String,
            @Json(name = "is-owner")
            val isOwner: String,
            @Json(name = "name")
            val name: String
        ) : Parcelable

        @Parcelize
        data class Category(
            @Json(name = "color")
            val color: String,
            @Json(name = "id")
            val id: String,
            @Json(name = "name")
            val name: String
        ) : Parcelable
    }
}