package com.yachikajoshi.movielist.domain.repo

import com.yachikajoshi.movielist.data.model.Movies

/*
* We are going to use this repository in
* `data.repo` package &
* in this repo we are getting the data directly from the API
* */

interface Top10MovieRepository {

    suspend fun getTop10Movies(): Movies
}